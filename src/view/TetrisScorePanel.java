/**
 * TCSS 305 - Autumn 2016
 * Assignment 6b - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Class represent the score info for current Tetris Game.
 * @author Dat Ly datly@uw.edu
 * @version 12/09/2016
 *
 */

public class TetrisScorePanel extends JPanel {
    /** Auto generate serialVersion. */
    private static final long serialVersionUID = -461478013816430608L;
    /** Clear 1-3 lines get this amount of points for each line. */
    private static final int DEFAULT_SCORE = 100;
    /** Clear 4 lines in a row worth this points. */
    private static final int JACK_POT = 1000;
    /** The maximum amount of line clear. */
    private static final int MAX_CLEAR_BLOCK = 4;
    /** The amount of block needed to be clear until next level. */
    private static final int NUM_OF_BLOCK = 5;
    /** Started level at 1. */
    private static final int INITIAL_LEVEL = 1;
    /** Started score at 0. */
    private static final int INITIAL_SCORE = 0;
    /** The game will speed up 100 milisecond per level. (maximum is 100 milisecon) */
    private static final int SPEED_UP = 200;
    /** Scale use by components. */
    private static final int SCALE = 3;
    /** Scale the strut between each JLabel. */
    private static final int SCALE_STRUT = 20;
    /** Display number of line cleared. */
    private static final String LINE_CLEAR = "LINE CLEARED: ";
    /** Display current score. */
    private static final String SCORE = "SCORE: ";
    /** Display current level. */
    private static final String CURRENT_LEVEL = "LEVEL: ";
    /** Display number of line need to be clear until next level. */
    private static final String NEXT_LEVEL = "NEXT LEVEL IN: ";
    /** String display line. */
    private static final String LINE = " LINE";
    /**
     * Dimension of this panel.
     */
    private final Dimension myDimension;
    /**
     * Label for the line clear info.
     */
    private final JLabel myLineClearedLabel;
    /**
     * Label for score info.
     */
    private final JLabel myScoreLabel;
    /**
     * Label for current level info.
     */
    private final JLabel myLevelLabel; 
    /**
     * Label for next level info.
     */
    private final JLabel myNextLevelLabel;
    /**
     * Current level.
     */
    private int myLevel; 
    /**
     * Next level.
     */
    private int myNextLevel;
    /**
     * Current score.
     */
    private int myScore;
    
    /**
     * Constructor of this class use to initialize instance fields.
     */
    public TetrisScorePanel() {
        
        super();
        myDimension = new Dimension(TetrisGUI.DEFAULT_WIDTH - TetrisGUI.MEDIUM_WIDTH, 
                                    TetrisGUI.MEDIUM_HEIGHT / SCALE);
        myLevel = INITIAL_LEVEL;
        myScore = INITIAL_SCORE;
        myNextLevel = NUM_OF_BLOCK;
        myLineClearedLabel = new JLabel(LINE_CLEAR + 0);
        myScoreLabel = new JLabel(SCORE + myScore);  
        myLevelLabel = new JLabel(CURRENT_LEVEL + myLevel);
        myNextLevelLabel = new JLabel(NEXT_LEVEL + myNextLevel + LINE);
        setUpScorePanel();
    }
    
    /**
     * Command method use to set up the initial value when the game begin.
     */
    public void setInitialInfo() {
        
        myLevel = INITIAL_LEVEL; 
        myScore = INITIAL_SCORE; 
        myNextLevel = NUM_OF_BLOCK;
        myLineClearedLabel.setText(LINE_CLEAR + 0); 
        myScoreLabel.setText(SCORE + myScore);
        myLevelLabel.setText(CURRENT_LEVEL + myLevel);
        myNextLevelLabel.setText(NEXT_LEVEL + myNextLevel + LINE);    
    }
    
    /**
     * Command method that do the set up for score panel.
     */
    private void setUpScorePanel() {

        final TitledBorder scoreBorder = new TitledBorder("Game Info: ");
        scoreBorder.setTitleColor(Color.WHITE);
        scoreBorder.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        scoreBorder.setTitleJustification(TitledBorder.CENTER);
        
        final Font font = new Font(Font.DIALOG, Font.BOLD, 15);
        
        myLineClearedLabel.setForeground(Color.WHITE.darker());
        myLineClearedLabel.setFont(font);
        myScoreLabel.setForeground(Color.WHITE.darker());
        myScoreLabel.setFont(font);
        myLevelLabel.setForeground(Color.WHITE.darker());
        myLevelLabel.setFont(font);
        myNextLevelLabel.setForeground(Color.WHITE.darker());
        myNextLevelLabel.setFont(font);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(scoreBorder);
        setBackground(TetrisBoard.DEFAULT_COLOR);
        setPreferredSize(myDimension);
   
        add(Box.createVerticalStrut((int) myDimension.getHeight() / (SCALE_STRUT / 2)));
        add(myLineClearedLabel);
        add(Box.createVerticalStrut((int) myDimension.getHeight() / SCALE_STRUT));
        add(myScoreLabel);
        add(Box.createVerticalStrut((int) myDimension.getHeight() / SCALE_STRUT));
        add(myLevelLabel);
        add(Box.createVerticalStrut((int) myDimension.getHeight() / SCALE_STRUT));
        add(myNextLevelLabel);
        
    }
    
    /**
     * Update the game info.
     * @param theLine total of lines has been clear.
     * @param theLineClearedThisTime total of lines clear at a time.
     */
    public void getGameProgress(final int theLine, final int theLineClearedThisTime) {
        
        // Redesign on how the text should look 
        /**
         * The score algorithm will be 
         * < 4 lines at a time worth 100 pts
         * == 4 line at a time worth 1000 pts 
         */
        if (theLine % NUM_OF_BLOCK == 0 || theLine > myLevel * NUM_OF_BLOCK) {
            myLevel++;
            myNextLevel = myLevel * NUM_OF_BLOCK  - theLine;
        } else {
            myNextLevel = myLevel * NUM_OF_BLOCK  - theLine;
        }
        
        if (theLineClearedThisTime == MAX_CLEAR_BLOCK) {
            myScore += JACK_POT;
        } else {
            myScore += theLineClearedThisTime * DEFAULT_SCORE;
        }
        
        myLineClearedLabel.setText(LINE_CLEAR + theLine); 
        myScoreLabel.setText(SCORE + myScore);
        myLevelLabel.setText(CURRENT_LEVEL + myLevel);
        myNextLevelLabel.setText(NEXT_LEVEL + myNextLevel + LINE);
    }  
    
    /**
     * Command method that will speed up the game whenver the user 
     * level up. 
     * @return speed up 200 milisecond per level (max is 100 milisecond).
     */
    public int updateGameSpeed() {
        
        return TetrisBoard.DELAY - ((myLevel - 1) * SPEED_UP);
    }
}