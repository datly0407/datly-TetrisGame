/**
 * TCSS 305 - Autumn 2016
 * Assignment 6b - tetris
 */

package listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.Board;

/**
 * Class that will listen to the KeyBoard event.
 * @author Dat Ly datly@uw.edu
 * @version 12/09/2016
 *
 */
public class KeyListener extends KeyAdapter {
    
    /**
     * Reference to the Board class.
     */
    private final Board myBoard;
    /**
     * Enable or disable keyBoard event.
     */
    private boolean myActive;
    
    /**
     * Constructor that initialize instance field.
     * @param theBoard Board class.
     */
    public KeyListener(final Board theBoard) {
        
        super();
        myBoard = theBoard;     
        myActive = true;
    }
    
    /**
     * Change the enable status.
     * @param theAction True for enable or False for disable.
     */
    public void setAction(final boolean theAction) {
        
        myActive = !theAction;
    }
    
    @Override
    public void keyPressed(final KeyEvent theEvent) {
       
        if (myActive) {
            switch (theEvent.getKeyCode()) {
                case KeyEvent.VK_A:
                    myBoard.left();
                    break;
                case KeyEvent.VK_S:
                    myBoard.down();
                    break;
                case KeyEvent.VK_D:
                    myBoard.right();
                    break;
                case KeyEvent.VK_R:
                    myBoard.rotate();
                    break;
                case KeyEvent.VK_SPACE:
                    myBoard.drop();
                    break;
                    
                default:
                    break;
            }
        }
    }
}