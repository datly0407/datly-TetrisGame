/**
 * TCSS 305 - Autumn 2016
 * Assignment 6b - tetris
 */

package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.MovableTetrisPiece;

/**
 * Class represent the next piece preview.
 * @author Dat Ly datly@uw.edu
 * @version 12/09/2016
 *
 */
public class TetrisNextPiece extends JPanel implements Observer, PropertyChangeListener {    

    /** Auto generated serialVersion. */
    private static final long serialVersionUID = -646900114371577590L;
    /** Default color for this panel. */
    private static final Color DEFAULT_COLOR = Color.BLACK;
    /** Scale use by lots of components. */
    private static final int SCALE = 3;
    /** Scale use to construct invisible component. */
    private static final int STRUT_SCALE = 10;
    /** Fastest speed that user can play. */
    private static final int FASTEST_SPEED = 100;
    /** Reference to the MovaleTetrisPiece class. */
    private MovableTetrisPiece myNextPiece;
    /**
     * Top panel display next piece.
     */
    private TopPanel myTopPanel;
    /**
     * Center panel contains information about the Game.
     */
    private final JPanel myCenterPanel;
    /**
     * Do nothing for this part.
     */
    private TetrisScorePanel myBottomPanel;
    /**
     * Dimension of the Panel.
     */
    private Dimension myDimension; 
    /**
     * Dimension use by other components.
     */
    private Dimension mySmallerDimension;
    /**
     * Number of line clear in current game. 
     */
    private int myNumLineCleared;
  
    /**
     * Constructor initialized instance fields.
     */
    public TetrisNextPiece() {
        
        super();
        myDimension = new Dimension(TetrisGUI.DEFAULT_WIDTH - TetrisGUI.MEDIUM_WIDTH, 
                                TetrisGUI.MEDIUM_HEIGHT);
        
        myCenterPanel = new JPanel();
        mySmallerDimension = new Dimension((int) myDimension.getWidth(), 
                                           (int) myDimension.getHeight() / SCALE);
        myNumLineCleared = 0;
        setUpPanel();
    }
    
    /**
     * Command method that will do the set up components of this panel.
     */
    private void setUpPanel() {
              
        myBottomPanel = new TetrisScorePanel();
        setBackground(TetrisBoard.DEFAULT_COLOR);
        setPreferredSize(myDimension);
        setUpInfoPanel();
        
        myTopPanel = new TopPanel();
        final BorderLayout borderLayout = new BorderLayout();

        myBottomPanel.setPreferredSize(mySmallerDimension);
        myBottomPanel.setBackground(DEFAULT_COLOR);
        
        setLayout(borderLayout);
        add(myTopPanel, BorderLayout.NORTH);
        add(myCenterPanel, BorderLayout.CENTER);
        add(myBottomPanel, BorderLayout.SOUTH);           
    }
    
    /**
     * Set up the middle panel which is contain info about the Game.
     */
    private void setUpInfoPanel() {
        
        final List<JLabel> label = new ArrayList<JLabel>();
        label.add(new JLabel("Move Piece Left: A "));
        label.add(new JLabel("Move Piece Right: D "));
        label.add(new JLabel("Move Piece Down: S "));
        label.add(new JLabel("Rotate Piece: R "));
        label.add(new JLabel("Drop Piece Down: Space "));
        label.add(new JLabel("Pause Game: P"));
            
        final TitledBorder infoBorder = new TitledBorder("Controls Key:");
        infoBorder.setTitleColor(Color.WHITE);
        infoBorder.setTitleJustification(TitledBorder.CENTER);
        infoBorder.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        myCenterPanel.setBorder(infoBorder);
        myCenterPanel.setBackground(TetrisBoard.DEFAULT_COLOR);
        myCenterPanel.setPreferredSize(mySmallerDimension);
        myCenterPanel.setLayout(new BoxLayout(myCenterPanel, BoxLayout.Y_AXIS));
        
        final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
        for (final JLabel lb : label) { 
            lb.setForeground(Color.WHITE);
            lb.setFont(font);
            lb.setAlignmentX(Component.CENTER_ALIGNMENT);
            myCenterPanel.add(lb);
        }
    }
    
    /**
     * Get current dimension of the JPanel.
     * @return dimension.
     */
    public Dimension getDimension() {
        
        return (Dimension) myDimension.clone();
    }
    
    @Override 
    public void update(final Observable theObservable, final Object theData) {
        
        if (theData instanceof MovableTetrisPiece) {
            myNextPiece = (MovableTetrisPiece) theData;
        }  
        if (theData instanceof Integer[]) {
            this.firePropertyChange("Cheer", null, null);
            myNumLineCleared += ((Integer[]) theData).length;
            final int lineClearedThisTime = ((Integer[]) theData).length;
            myBottomPanel.getGameProgress(myNumLineCleared, lineClearedThisTime);
            if (myBottomPanel.updateGameSpeed() >= FASTEST_SPEED) {
                this.firePropertyChange("SpeedUp", null, myBottomPanel.updateGameSpeed());
            }
        }
        repaint();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if ("PreviewBoard".equals(theEvent.getPropertyName())) {
            myDimension = (Dimension) theEvent.getNewValue();
            mySmallerDimension = new Dimension((int) myDimension.getWidth(), 
                                               (int) myDimension.getHeight() / SCALE);
            setPreferredSize(myDimension);
            myCenterPanel.setPreferredSize(mySmallerDimension);
            myBottomPanel.setPreferredSize(mySmallerDimension);
            myTopPanel.setPreferredSize(mySmallerDimension);
        } 
        if ("NewGame".equals(theEvent.getPropertyName())) {
            myNumLineCleared = 0;
            myBottomPanel.setInitialInfo();
        }
    }
       
    /**
     * Inner class that will display the next piece preview.
     * @author Dat Ly datly@uw.edu
     * @version 12/02/2016
     *
     */
    private class TopPanel extends JPanel {
        
        /** Auto generate serialVersion. */
        private static final long serialVersionUID = -4750719428444057237L;

        /**
         * Constructor that will call set up method. 
         */
        TopPanel() {
                    
            super();
            setUpPreviewPanel();  
        }
        
        /**
         * Command method that will do the set up for 
         * next piece preview panel.
         */
        private void setUpPreviewPanel() {
            
            final TitledBorder previewBorder = new TitledBorder("Next Piece: ");
            previewBorder.setTitleColor(Color.WHITE);
            previewBorder.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            previewBorder.setTitleJustification(TitledBorder.CENTER);
           
            setPreferredSize(mySmallerDimension);
            setBackground(TetrisBoard.DEFAULT_COLOR);
            setBorder(previewBorder);
        }
        
        @Override
        public void paintComponent(final Graphics theGraphics) {
        
            super.paintComponent(theGraphics);
            final Graphics2D g2d = (Graphics2D) theGraphics;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                 RenderingHints.VALUE_ANTIALIAS_ON);
            final int portion = (int) myDimension.getWidth() / STRUT_SCALE;
            final int topPanelSpace = portion * 8;
            final int scale = topPanelSpace / 7;
            if (myNextPiece != null) {
                drawNextPiece(g2d, scale);
            }
        }
    
       /**
        * Display preview for next coming piece.
        * @param theGraphics use Graphics2D to do the drawing.
        * @param theScale scale appropriate to display each piece.
        */
        private void drawNextPiece(final Graphics2D theGraphics, final int theScale) {
        
            final Graphics2D g2d = theGraphics;
            g2d.setStroke(new BasicStroke(1));
            final char[] pieceType = {'I', 'J', 'L', 'O', 'S', 'T', 'Z'};
            final Color[] pieceColor = {Color.CYAN, Color.WHITE, Color.ORANGE, Color.YELLOW,
                Color.GREEN, Color.MAGENTA, Color.RED};
            final String[] lines = myNextPiece.toString().split("\n");
            for (int i = 0; i < lines.length; i++) {  
                for (int j = 0; j < lines[i].length(); j++) { 
                    final int topLeftX = (j * theScale) + theScale * SCALE;
                    final int topLeftY = (i * theScale) + theScale;
                    final Rectangle2D.Double piecePreview = 
                                new Rectangle2D.Double(topLeftX, topLeftY, theScale, theScale);
                    for (int k = 0; k < pieceType.length; k++) { 
                        if (pieceType[k] == (lines[i].charAt(j))) {
                            g2d.setPaint(pieceColor[k].darker().darker());
                            g2d.fill(piecePreview);
                        }
                    }
                }
            }         
        }
    }
}