/**
 * TCSS 305 - Autumn 2016
 * Assignment 6b - tetris
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import listener.KeyListener;

import model.Board;

/**
 * Class represent the board of tetris game.
 * @author Dat Ly datly@uw.edu
 * @version 12/09/2016
 *
 */
public class TetrisBoard extends JPanel implements ActionListener, 
                                           Observer, PropertyChangeListener, FocusListener {
    /** Timer constant 1 sec (milisecond). */
    public static final int DELAY = 1000;
    /** Default color of the Board. */
    public static final Color DEFAULT_COLOR = Color.BLACK;
    /** Auto generated serialVerision. */
    private static final long serialVersionUID = 6043654053594679514L;
    /** Initial scale for GameOver String when the size is Medium. */
    private static final int INITIAL_GAMEOVER_SCALE = 30;
    /** Scale that use by lots of components. */
    private static final int SCALE = 10;
    /** Scale needed when drawing piece. (x-coordinate) */
    private static final int X_OFFSET = 1;
    /** Scale needed when drawing piece. (y-coordinate) */  
    private static final int Y_OFFSET = 5;
    /** Extra scale needed when drawing piece. */
    private static final int ROUNDING = 1;
    /** Each line of the Board as String representation. */
    private String[] myLines;
    /** KeyListener to move pieces. */
    private final KeyListener myKeyListener;
    /** Reference to the Board. */ 
    private final Board myBoard; 
    /** Timer use for animation of Tetris game. */   
    private final Timer myTimer;
    /** Scale use to display GameOver String. */
    private int myGameOverScale;
    /** Check whether the game is over. */
    private boolean myGameOverFlag;
    /** Check whether the game is pause. */
    private boolean myPauseFlag;
    /** Check whether the user want the grid to visible or not. */
    private boolean myGridFlag;
    /**
     * GameBoard's width.
     */
    private Dimension myDimension; 
    
    /**
     * Constructor initialized instance fields.
     * @param theBoard represent Board class.
     */
    public TetrisBoard(final Board theBoard) {
        
        super();
        myBoard = theBoard;
        myTimer = new Timer(DELAY, this);
        myKeyListener = new KeyListener(myBoard);
        myGridFlag = false;
        myGameOverFlag = false;
        myPauseFlag = false;
        myDimension = new Dimension(TetrisGUI.MEDIUM_WIDTH, TetrisGUI.MEDIUM_HEIGHT);
        myGameOverScale = INITIAL_GAMEOVER_SCALE;
        setUpBoard();
    }

    /**
     * Get current Dimension of the Game Board.
     * @return current Dimension.
     */
    public Dimension getDimension() {
        
        return (Dimension) myDimension.clone();
    }   

    /**
     * Command method do the set up for the Game Board.
     */
    private void setUpBoard() {
        
        myTimer.start();
        
        setPreferredSize(myDimension);
        addFocusListener(this);
        addKeyListener(myKeyListener);  
        addKeyListener(new PauseKeyListener());
        setFocusable(true);
        setBackground(DEFAULT_COLOR);
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }
    
    /**
     * Command method use to draw the grid of the Game Board.
     * @param theGraphics using Graphics2D to draw.
     * @param theScale scale of the Board.
     */
    private void paintGrid(final Graphics2D theGraphics, final int theScale) {
        
        final Graphics2D g2d = theGraphics;
        g2d.setStroke(new BasicStroke(1));
        g2d.setPaint(Color.GRAY);
        
        // y - coordinate
        if (myGridFlag) {
            for (int i = 0; i < myBoard.getHeight(); i++) {
                // x - coordinate
                for (int j = 0; j < myBoard.getWidth(); j++) {
                    g2d.drawRect(j * theScale, i * theScale, theScale, theScale);
                }
            } 
        } else {
            g2d.drawRect(0, 0, myBoard.getWidth() * theScale, myBoard.getHeight() * theScale);
        }
    }

    /**
     * Command method use to draw pieces inside the Game Board.
     * @param theGraphics using Graphics2D to do drawing.
     * @param theScale scale of each piece.
     */
    private void paintPiece(final Graphics2D theGraphics, final int theScale) {
        
        final Graphics2D g2d = theGraphics;
        g2d.setStroke(new BasicStroke(1)); 
        final char[] pieceType = {'I', 'J', 'L', 'O', 'S', 'T', 'Z'};
        final Color[] pieceColor = {Color.CYAN, Color.WHITE, Color.ORANGE, Color.YELLOW,
            Color.GREEN, Color.MAGENTA, Color.RED};
        for (int i = 0; i < myLines.length; i++) {
            for (int j = 0; j < myLines[i].length(); j++) {
                final int topLeftX = ((j - X_OFFSET) * theScale) + ROUNDING;
                final int topLeftY = ((i - Y_OFFSET) * theScale) + ROUNDING;
                final Rectangle2D.Double piece = new Rectangle2D.Double(topLeftX, topLeftY, 
                                                     theScale - ROUNDING, theScale - ROUNDING);
                for (int k = 0; k < pieceType.length; k++) {
                    if (pieceType[k] == (myLines[i].charAt(j))) {
                        g2d.setPaint(pieceColor[k].darker().darker());
                        g2d.fill(piece);
                        break;
                    }
                }
            }
        }
        myTimer.start();
    }  
    
    /**
     * Command method will display text when piece reach the top.
     * (Game is over)
     * @param theGraphics use Graphics2D to do the drawing.
     */
    private void paintGameOver(final Graphics2D theGraphics) {
        
        displayString(theGraphics, "GAME - OVER");
        myTimer.stop();
    }
    
    /**
     * Command method that will pause the game and blind the view of the current game.
     * @param theGraphics use to do the drawing.
     * @param theScale scale use to draw.
     */
    private void paintPause(final Graphics2D theGraphics, final int theScale) {
        
        final Graphics2D g2d = theGraphics;
        // display the text visually centered in the JPanel   
        g2d.setPaint(Color.GRAY);
        g2d.fillRect(0, 0, myBoard.getWidth() * theScale, myBoard.getHeight() * theScale);
        displayString(g2d, "PAUSE -  GAME");
        myTimer.stop();
    }
    
    /**
     * Use to display text into the game. 
     * @param theGraphics use to do the drawing.
     * @param theString can be use to display particular String.
     */
    private void displayString(final Graphics2D theGraphics, final String theString) {
          
        final Font font = new Font(Font.SERIF, Font.BOLD + Font.ITALIC, myGameOverScale);
        theGraphics.setFont(font);
        final FontRenderContext renderContext = theGraphics.getFontRenderContext();
        final GlyphVector glyphVector = font.createGlyphVector(renderContext, theString);
        final Rectangle2D visualBounds = glyphVector.getVisualBounds().getBounds();

        final int x =
            (int) ((getWidth() - visualBounds.getWidth()) / 2 - visualBounds.getX());
        final int y =
            (int) ((getHeight() - visualBounds.getHeight()) / 2 - visualBounds.getY());
        theGraphics.setPaint(Color.WHITE);
        theGraphics.drawString(theString, x, y);
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
       // Final debug
       //System.out.println(myBoard.toString());
       // Need to re-calculate to display the board in the middle of the panel.  
        int scale = (int) myDimension.getWidth() / myBoard.getWidth();
        final int scale2 = (int) myDimension.getHeight() / myBoard.getHeight(); 
        if (scale > scale2) {
            scale = scale2;
        }
  
        paintPiece(g2d, scale);  
        paintGrid(g2d, scale);
        if (myGameOverFlag) {
            paintGameOver(g2d);
        } 
        if (myPauseFlag && !myGameOverFlag) {
            paintPause(g2d, scale);
        }       
    }
    
    @Override 
    public void update(final Observable theObserver, final Object theData) {
        
        if (theData instanceof String) {
            myLines = myBoard.toString().split("\n"); 
            repaint();
        }
        if (theData instanceof Boolean) {
            myGameOverFlag = (Boolean) theData;
        } 
    }
    
    @Override
    public void actionPerformed(final ActionEvent theEvent) {     
        
        myBoard.down();    
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if ("Board".equals(theEvent.getPropertyName())) {
            myDimension = (Dimension) theEvent.getNewValue();
            myGameOverScale = (int) myDimension.getWidth() / SCALE;
            setPreferredSize(myDimension);
        } else if ("PauseGame".equals(theEvent.getPropertyName())) {
            myPauseFlag = (Boolean) theEvent.getNewValue();
            myKeyListener.setAction(myPauseFlag);
        } else if ("NewGame".equals(theEvent.getPropertyName())) {
            myGameOverFlag = (Boolean) theEvent.getNewValue();
            myTimer.setDelay(DELAY);
            myBoard.newGame();
            myKeyListener.setAction(myGameOverFlag);
        } else if ("EndGame".equals(theEvent.getPropertyName())) {
            myGameOverFlag = (Boolean) theEvent.getNewValue();
            myKeyListener.setAction(myGameOverFlag);
        } else if ("SpeedUp".equals(theEvent.getPropertyName())) {
            myTimer.setDelay((int) theEvent.getNewValue()); 
        } else if ("EnableGrid".equals(theEvent.getPropertyName())) {
            myGridFlag = (Boolean) theEvent.getNewValue();
        }
        repaint();
    }

    @Override
    public void focusGained(final FocusEvent theEvent) {
        
        myPauseFlag = false;
        repaint();
    }

    @Override
    public void focusLost(final FocusEvent theEvent) {
        
        myPauseFlag = true;
        myKeyListener.setAction(!myPauseFlag);
        repaint();
    }  
    
    /**
     * Class will listen to the pause key.
     * @author Dat Ly datly@uw.edu
     * @version 12/09/2016
     */
    private class PauseKeyListener extends KeyAdapter {
        
        @Override 
        public void keyPressed(final KeyEvent theEvent) {
            
            if (theEvent.getKeyCode() == KeyEvent.VK_P) {
                myPauseFlag = !myPauseFlag; 
                myKeyListener.setAction(myPauseFlag);
                repaint();
            }
        }        
    }
}