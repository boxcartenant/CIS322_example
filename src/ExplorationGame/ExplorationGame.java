package ExplorationGame;

import javax.swing.*;
import java.awt.*;

public class ExplorationGame extends JPanel {
    // Game Constants
    static final int TILE_SIZE = 32; // for the background and unit size
    private static final int VIEW_WIDTH = 800; // size of our view window
    private static final int VIEW_HEIGHT = 600;
    protected static final int WORLD_WIDTH = 2000; // size of the world
    protected static final int WORLD_HEIGHT = 2000;
    private long lastDamageTime = 0; // last time player was hit
    private static final long DAMAGE_COOLDOWN = 500; //how often player can be hit
    private boolean gameOver = false; //when the user dies, the game ends

    //this enum will be used for controlling player character movement.
    protected enum Direction {UP, DOWN, LEFT, RIGHT}

    // key state flag
    // movePresssed will be true when a button is pressed
    private boolean movePressed = false;
    private Direction moveDirection;

    public ExplorationGame()
    {
        // make the player and enemy objects
        // call spawnInitialObjects
        // set up the keylistener to get button presses and call our controlling functions (switchActiveUnit, move, etc)
        // set a timer to periodically run our updateGame() function, then request for swing to repaint.

        // that timer will function as the "game loop". 
        // We're putting that in the constructor so that it will start working 
        // as soon as it's added to the JFrame in main. This line: frame.add(new ExplorationGame());
    }

    private void spawnInitialObjects()
    {
        //spawn enemies
        //spawn HP items
    }

    private void switchActiveUnit()
    {
        //change which unit the player is controlling
    }

    private void updateGame()
    {
        //update visibility by moving the view window on the world
        // this will include calls to getViewBounds.

        // for each enemy, update the enemies

        //update HP items

        //update player stuff
    }

    private Rectangle getViewBounds()
    {
        //return a view boundary centered on the active unit
        return new Rectangle();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        //this function puts all the graphics on the display.
        // it overrides a function in the swing library.
        // when swing decides to repaint, it will call this overridden function, and consequently paint all our stuff.
        
        //set up the canvas
        //draw the world edge lines
        //draw HP items if visible
        //draw enemies if visible
        // draw the player
        //draw attacks
        //draw HP bar
        //if gameover, then draw gameover text
    }
}
