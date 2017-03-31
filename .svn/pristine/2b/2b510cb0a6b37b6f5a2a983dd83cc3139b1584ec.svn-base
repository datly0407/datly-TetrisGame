/**
 * TCSS 305 - Autumn 2016
 * Assignment 6b - tetris
 */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class represent the menu of the project.
 * @author Dat Ly datly@uw.edu
 * @version 12/09/2016
 *
 */
public class TetrisMenuBar extends JMenuBar {
    /** Auto generate serialVersion. */
    private static final long serialVersionUID = 7771035201772715710L;
    /** Name of the Pause Game button. */
    private static final String PAUSE_GAME = "Pause Game";
    /** Name of the New Game button. */
    private static final String NEW_GAME = "New Game";
    /** Name of the End Game button. */
    private static final String END_GAME = "End Game";
    /** Name of the Quit button. */
    private static final String QUIT = "Quit";
    /** Name of the About button. */
    private static final String ABOUT = "About...";
    /** Initial start size of the Board. */
    private static final int INITIAL_SIZE = 10;
    /** Maximum size that user can adjust for the Game's Board. */
    private static final int MAX_SIZE = 20; 
    /** Increment 1 per short line. */
    private static final int MINOR_INCREMENT = 1; 
    /** Increment 5 per long line in JSlider. */
    private static final int MAJOR_INCREMENT = 5;
    /**
     * Option menu for the user to change size of Game's Board.
     */
    private final JMenu myOptions;
    /**
     * Sub-menu for the user to custom the size of the Game's Board.
     */
    private JMenu mySubMenu;
    /**
     * Button group that make sure one button select at a time.
     */
    private final ButtonGroup myButtonGroup;
    /**
     * Pause game flag. It will let other components 
     * know about the status of the game.
     */
    private boolean myPause; 
    /**
     * Action that allow user to start a new game.
     */
    private Action myNewGame;
    /**
     * Action that allow user to end a game.
     */
    private Action myEndGame;
    
    /**
     * Constructor initialize instance fields.
     * @param theFrame Container which hold all the components.
     */
    public TetrisMenuBar(final JFrame theFrame) {
        
        super();
        myOptions = new JMenu("Options");
        myButtonGroup = new ButtonGroup();
        myPause = false; 
        setUpMenu(theFrame);
    }
    
    /**
     * Command method that will set up the view for the MenuBar.
     * @param theFrame container of the game.
     */
    private void setUpMenu(final JFrame theFrame) {
        
        // Set up Action for End Game menuItem.
        myEndGame = new AbstractAction(END_GAME) {

            /** Auto generate serialVersion. */
            private static final long serialVersionUID = 8248079210539758142L;

            @Override 
            public void actionPerformed(final ActionEvent theEvent) {

                TetrisMenuBar.this.firePropertyChange("EndGame", null, true);
                myNewGame.setEnabled(true);
                mySubMenu.setEnabled(true);
                myEndGame.setEnabled(false);
                
            }
        };
        // Set up Action for New Game menuItem.
        myNewGame = new AbstractAction(NEW_GAME) {
            
            /** Auto generate serialVersion. */
            private static final long serialVersionUID = 8248079210539758142L;

            @Override 
            public void actionPerformed(final ActionEvent theEvent) {
            
                TetrisMenuBar.this.firePropertyChange("NewGame", null, false);
                myNewGame.setEnabled(false);
                mySubMenu.setEnabled(false);
                myEndGame.setEnabled(true);
            }
        };
              
        myNewGame.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
        myEndGame.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E); 
        
        final JMenu menu = new JMenu("Menu");
        menu.add(pauseGame(PAUSE_GAME));
        menu.addSeparator();
        menu.add(myNewGame);
        menu.add(myEndGame);
        menu.addSeparator();
        menu.add(quitAction(QUIT, theFrame));
        
        final JMenu feature = new JMenu("Features");
        mySubMenu = gridSize("Grid Size");
        feature.add(mySubMenu);
        feature.addSeparator();
        feature.add(gridButton("Grid"));
        feature.add(muteButton("Mute"));
        
        final JMenu help = new JMenu("Help");
        help.add(aboutAction(ABOUT));
        
        menu.setMnemonic(KeyEvent.VK_M);
        myOptions.setMnemonic(KeyEvent.VK_O);
        feature.setMnemonic(KeyEvent.VK_F);
        help.setMnemonic(KeyEvent.VK_H);
        
        add(menu);  
        add(myOptions);
        add(feature);
        add(help); 
        
        // Set enable or disable feature for different kinds of menu item.
        myNewGame.setEnabled(false);
        mySubMenu.setEnabled(false);
        myEndGame.setEnabled(true);   
    }
    
    /**
     * Pause Action of the Game.
     * @param theString name of the Action
     * @return AbstractAction.
     */
    private Action pauseGame(final String theString) {
        
        final Action pauseGame = new AbstractAction(theString) {
            
            /** Auto generate serialVersion. */
            private static final long serialVersionUID = 8936202369426877774L;

            @Override 
            public void actionPerformed(final ActionEvent theEvent) {
                myPause = !myPause;
                TetrisMenuBar.this.firePropertyChange("PauseGame", null, myPause);
            }
        };
        
        pauseGame.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        return pauseGame;
    }
       
    /**
     * Action to close the game.
     * @param theString name of the Action
     * @param theFrame close every components and the container included.
     * @return AbstractAction.
     */
    private Action quitAction(final String theString, final JFrame theFrame) {
        
        final Action quitAction = new AbstractAction(theString) {
            
            /** Auto generate serialVersion. */
            private static final long serialVersionUID = 2070587338032059286L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                theFrame.dispatchEvent(new WindowEvent(theFrame, 
                                                       WindowEvent.WINDOW_CLOSING));
            }
        };
        quitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
        return quitAction;
    }
    
    /**
     * Sub-menu that allow user to choose custom grid for the game.
     * @param theString Name of the Menu.
     * @return Sub-menu.
     */
    private JMenu gridSize(final String theString) {
        
        final JMenu boardSize = new JMenu(theString);
        
        final JSlider width = new JSlider(SwingConstants.HORIZONTAL, INITIAL_SIZE, 
                                          MAX_SIZE, INITIAL_SIZE);
        final JLabel widthLabel = new JLabel("WIDTH");
        width.setMajorTickSpacing(MAJOR_INCREMENT);
        width.setMinorTickSpacing(MINOR_INCREMENT);
        width.setPaintLabels(true);
        width.setPaintTicks(true);
        width.addChangeListener(new ChangeListener() {
            
            @Override 
            public void stateChanged(final ChangeEvent theEvent) {
                TetrisMenuBar.this.firePropertyChange("ChangeWidth", null, width.getValue());
            }
        });
        
        final JSlider height = new JSlider(SwingConstants.HORIZONTAL, INITIAL_SIZE, 
                                           MAX_SIZE, INITIAL_SIZE);
        final JLabel heightLabel = new JLabel("HEIGHT");    
        height.setMajorTickSpacing(MAJOR_INCREMENT);
        height.setMinorTickSpacing(MINOR_INCREMENT);
        height.setPaintLabels(true);
        height.setPaintTicks(true);
        height.addChangeListener(new ChangeListener() {
            
            @Override 
            public void stateChanged(final ChangeEvent theEvent) {
                TetrisMenuBar.this.firePropertyChange("ChangeHeight", null, height.getValue());
            }
        });
        
        boardSize.add(widthLabel);
        boardSize.add(width);
        boardSize.add(heightLabel);
        boardSize.add(height);
        
        return boardSize;
    }
    
    /**
     * JCheckBox button use to turn on and off grid display.
     * @param theString name of the button
     * @return JCheckBoxMenuItem
     */
    private JCheckBoxMenuItem gridButton(final String theString) {
        
        final JCheckBoxMenuItem gridButton = new JCheckBoxMenuItem(theString);
        
        gridButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                boolean grid = false;
                if (gridButton.isSelected()) {
                    grid = !grid;
                } 
                TetrisMenuBar.this.firePropertyChange("EnableGrid", null, grid);             
            }   
        });
        return gridButton;
    }
    
    /**
     * Mute button use to mute background music.
     * @param theString Name of the Button.
     * @return JCheckBoxMenuItem.
     */
    private JCheckBoxMenuItem muteButton(final String theString) {
        
        final JCheckBoxMenuItem muteButton = new JCheckBoxMenuItem(theString);
        
        muteButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                boolean mute = false; 
                if (muteButton.isSelected()) {
                    mute = !mute;
                }
                TetrisMenuBar.this.firePropertyChange("ControlMute", null, mute);
            }
        });
        
        return muteButton;
    }
    /**
     * Instruction will pop-up when user click on this button.
     * @param theString name for the button.
     * @return Action event of the butotn.
     */
    private Action aboutAction(final String theString) {
        
        final URL urlFile = getClass().getResource("/Tetris_Frame_Icon.png");
        final ImageIcon icon = new ImageIcon(urlFile);
        final Action aboutAction = new AbstractAction(theString) {
            
            /** Auto generate serialVersion. */
            private static final long serialVersionUID = 1228017523677948203L;

            @Override 
            public void actionPerformed(final ActionEvent theEvent) {
            
                JOptionPane.showMessageDialog(null, "Clear 1-3 lines worth 100 pts each. \n"
                                    + "Clear 4 lines in a row worth 1000 pts. \n"
                                    + "Clear 5 lines to level up. \n",
                                      "Scoring Algorithm: ", 
                                    JOptionPane.INFORMATION_MESSAGE, icon);
            }
        };
      
        aboutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
        return aboutAction;
    }
    
    /**
     * Create a group of button to change size for the Game Board.
     * @param theAction user can choose one button at the time.
     */
    public void createSizeButton(final AbstractAction theAction) {
        
        final JRadioButtonMenuItem rb = new JRadioButtonMenuItem(theAction);
        myOptions.add(rb);
        myButtonGroup.add(rb);
    }
}