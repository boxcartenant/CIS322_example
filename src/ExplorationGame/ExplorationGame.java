package ExplorationGame;

import javax.swing.*;
import java.awt.*;

public class ExplorationGame extends JPanel {
    // Game Constants
    static final int TILE_SIZE = 32;
    private static final int VIEW_WIDTH = 800;
    private static final int VIEW_HEIGHT = 600;
    protected static final int WORLD_WIDTH = 2000;
    protected static final int WORLD_HEIGHT = 2000;
    private long lastDamageTime = 0;
    private static final long DAMAGE_COOLDOWN = 500;
    private boolean gameOver = false;

    protected enum Direction {UP, DOWN, LEFT, RIGHT}

    // key state flag
    private boolean movePressed = false;
    private Direction moveDirection;

    public ExplorationGame()
    {
    }


    //                +	KeyListener WRITE THIS LATER

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

        // for each enemy, update the enemies

        //update HP items

        //update player stuff
    }

    private Rectangle getViewBounds()
    {
        //return a view boundary centered on the active unit
        return new Rectangle();
    }

    protected void paintComponent(Graphics g)
    {
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
