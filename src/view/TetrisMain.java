/**
 * TCSS 305 - Autumn 2016
 * Assignment 6b - tetris
 */

package view;

import java.awt.EventQueue;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Driver-class contains main method which will start the program.
 * @author Dat Ly datly@uw.edu
 * @version 12/09/2016
 *
 */
public final class TetrisMain {
    
    /** 
     * Private constructor for drvier-class.
     */
    private TetrisMain() {
        
    }
    
    /**
     * Set Look and Feel for JFrame.
     */
    private static void setLookAndFeel() {
        
        try {
            
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (final ClassNotFoundException e) {
            System.out.println("Class doesn't exist!!!!");
        } catch (final InstantiationException e) {
            System.out.println("Can't instantiate LookAndFeel");
        } catch (final IllegalAccessException e) {
            System.out.println("Can't access LookAndFeel");
        } catch (final UnsupportedLookAndFeelException e) {
            System.out.println("Class doesn't contain LookAndFeel");
        }
    }
    
    /**
     * Main method that will kick-off the Tetris game.
     * @param theArgs command line.
     */
    public static void main(final String[] theArgs) {  
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final URL url = getClass().getResource("/tetris.jpg");
                final ImageIcon icon = new ImageIcon(url);
                setLookAndFeel();
                final int startGame = JOptionPane.showConfirmDialog(null, 
                                        "WELCOME TO TETRIS WORLD! \n"
                              + "You are about to go on an adventure. \n"
                              + "Are you READY ???? \n" 
                              + "(WARNING: This application contains sound, "
                              + "Please adjust your volume properly.)", "T-E-T-R-I-S", 
                              JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
                if (startGame == JOptionPane.YES_OPTION) {
                    new TetrisGUI().start();
                } else {
                    System.exit(0);
                }
            }
        });
    }
}