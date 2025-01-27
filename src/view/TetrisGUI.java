/**
 * TCSS 305 - Autumn 2016
 * Assignment 6b - tetris
 */

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Board;

/**
 * Class represent the JFrame which is contain all 
 * components of the program.
 * @author Dat Ly datly@uw.edu
 * @version 12/09/2016
 *
 */
public class TetrisGUI extends JFrame implements PropertyChangeListener {
    /** Initial size of the JFrame. */
    public static final int DEFAULT_WIDTH = 610;
    /** Initial size of the JFrame. */
    public static final int DEFAULT_HEIGHT = 655;
    /** Small size for Game Board. (Width) */
    public static final int SMALL_WIDTH = 205;
    /** Small size for Game Board. (Height) */
    public static final int SMALL_HEIGHT = 405;
    /** Medium size for Game Board. (Width) */
    public static final int MEDIUM_WIDTH = 305;
    /** Medium size for Game Board. (Height) */
    public static final int MEDIUM_HEIGHT = 605;
    /** Large size for Game Board. (Width) */
    public static final int LARGE_WIDTH = 355;
    /** Large size for Game Board. (Height) */
    public static final int LARGE_HEIGHT = 705;
    /** Auto generate serialVersion. */
    private static final long serialVersionUID = 3421843505830498381L;
    /**
     * Reference Tetris Board class.
     */
    private final TetrisBoard myBoardDisplay;
    /**
     * Reference next piece preview class.
     */
    private final TetrisNextPiece myNextPieceDisplay;
    /**
     * Reference to the menu bar class.
     */
    private final TetrisMenuBar myMenuBar;
    /**
     * Reference to Board class.
     */
    private final Board myBoard;
    /**
     * Play background music.
     */
    private Clip myClip;
    /**
     * Mute flag for background music.
     */
    private boolean mySoundFlag;
    
    /**
     * Constructor that will initialize instance fields.
     */
    public TetrisGUI() {
        
        super("TCSS 305 ---- TETRIS ----");   
        myBoard = new Board();   
        myBoardDisplay = new TetrisBoard(myBoard);
        myNextPieceDisplay = new TetrisNextPiece();
        myMenuBar = new TetrisMenuBar(this);   
        mySoundFlag = false;
    }
    
    /**
     * Method call from method that will kick-off the program.
     */
    public void start() {

        final URL url = getClass().getResource("/Tetris_Frame_Icon.png");
        final ImageIcon icon = new ImageIcon(url);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(myMenuBar);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        add(myBoardDisplay, BorderLayout.CENTER);
        add(myNextPieceDisplay, BorderLayout.EAST);
        final Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation(
                    (int) ((kit.getScreenSize().getWidth() - this.getWidth()) / 2),
                    (int) ((kit.getScreenSize().getHeight() - this.getHeight()) / 2));
        setVisible(true);  
        setResizable(false);
        setIconImage(icon.getImage());
        
        startGame();
        setUpBoardSizeOption();      
    }
    
    /** 
     * Create three different Action for size option. 
     * Let's user change the size of the Board. 
     */
    private void setUpBoardSizeOption() {
        
        final List<SetSizeOption> sizeOption = new ArrayList<SetSizeOption>();
        sizeOption.add(new SetSizeOption("SMALL (200 x 400)", SMALL_WIDTH, SMALL_HEIGHT));
        sizeOption.add(new SetSizeOption("MEDIUM (300 x 600)", MEDIUM_WIDTH, MEDIUM_HEIGHT));
        sizeOption.add(new SetSizeOption("LARGE (350 x 700)", LARGE_WIDTH, LARGE_HEIGHT));
        
        for (final AbstractAction action : sizeOption) {
            myMenuBar.createSizeButton(action);
        }      
    }
    
    /**
     * Private method that will start the game immidiately when the GUI open.
     */
    private void startGame() {

        myBoard.addObserver(myBoardDisplay);
        myBoard.addObserver(myNextPieceDisplay);
        myNextPieceDisplay.addPropertyChangeListener(myBoardDisplay);
        myNextPieceDisplay.addPropertyChangeListener(this);
        myMenuBar.addPropertyChangeListener(myBoardDisplay);
        myMenuBar.addPropertyChangeListener(myNextPieceDisplay);   
        myMenuBar.addPropertyChangeListener(this);
        myBoard.newGame();
        playMusic();
    }
    
    /**
     * Command method that will play background music.
     */
    private void playMusic() {
        
        try {
            final URL urlFile = getClass().getResource("/TetrisMusic.wav");
            final AudioInputStream input = AudioSystem.getAudioInputStream(urlFile);
            myClip = AudioSystem.getClip();
            myClip.open(input);
            myClip.loop(Clip.LOOP_CONTINUOUSLY);
                        
        } catch (final LineUnavailableException e) {
            System.out.println("Can't reach the music file.");
        } catch (final IOException e) {
            System.out.println("Can't find file.");
        } catch (final UnsupportedAudioFileException e) {
            System.out.println("File didn't input correctly.");
        }
    }
    
    /**
     * Command method that will play sound effect whenever line cleared.
     */
    private void playCheerSound() {
        
        final Clip cheerEffect;
        try {
            
            final URL urlFile = getClass().getResource("/CheerSound 1.wav");
            final AudioInputStream input2 = AudioSystem.getAudioInputStream(urlFile);
            cheerEffect = AudioSystem.getClip();
            cheerEffect.open(input2);
            cheerEffect.start();
            
        } catch (final LineUnavailableException e) {
            System.out.println("Can't reach cheer sound.");
        } catch (final IOException e) {
            System.out.println("Can't find cheer sound file.");
        } catch (final UnsupportedAudioFileException e) {
            System.out.println("Cheer sound didn't input correctly.");
        }
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if ("ChangeWidth".equals(theEvent.getPropertyName())) {    
            myBoard.setWidth((int) theEvent.getNewValue());
        } else if ("ChangeHeight".equals(theEvent.getPropertyName())) {
            myBoard.setHeight((int) theEvent.getNewValue());          
        } else if ("ControlMute".equals(theEvent.getPropertyName())) {
            mySoundFlag = (Boolean) theEvent.getNewValue();
            if (mySoundFlag) {
                myClip.stop();
            } else {
                myClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } else if ("Cheer".equals(theEvent.getPropertyName())) {
            playCheerSound();
        }
    }
    
    /**
     * Inner class that will generate AbstratAction for each menuItem. 
     * @author Dat Ly datly@uw.edu
     * @version 12/02/2016
     *
     */
    private class SetSizeOption extends AbstractAction {
        /** Auto generate serialVersion. */   
        private static final long serialVersionUID = 5136420015692712583L;
        /** Constant used to generate appropriate size for JFrame. */
        private static final int HEIGHT_OFFSET = 50;
        /**
         * Width use to update the size for each of the components.
         */
        private final int myNewWidth; 
        /**
         * Height use to update the size for each of the components.
         */
        private final int myNewHeight; 
        
        /**
         * Constructor initialized instance fields.
         * @param theName Name of the Action
         * @param theWidth update new width.
         * @param theHeight update new height.
         */
        SetSizeOption(final String theName, final int theWidth, final int theHeight) {
            
            super(theName);
            myNewWidth = theWidth;
            myNewHeight = theHeight;
            addPropertyChangeListener(myBoardDisplay);
            addPropertyChangeListener(myNextPieceDisplay);
            putValue(Action.MNEMONIC_KEY, 
                     KeyEvent.getExtendedKeyCodeForChar(theName.charAt(0)));
        }
        
        @Override 
        public void actionPerformed(final ActionEvent theEvent) {
            
            final int newFrameWidth = myNewWidth * 2;
            final int newFrameHeight = myNewHeight + HEIGHT_OFFSET;   
            setSize(newFrameWidth, newFrameHeight);
            firePropertyChange("Board", myBoardDisplay.getDimension(), 
                               new Dimension(myNewWidth, myNewHeight));
            firePropertyChange("PreviewBoard", myNextPieceDisplay.getDimension(), 
                               new Dimension(myNewWidth, newFrameHeight));
        }
    }
}