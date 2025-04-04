package ExplorationGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

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

    private ArrayList<Actor> enemies;
    private ArrayList<HPItem> items;
    private Random random = new Random();
    private ArrayList<Actor> playerUnits;
    private Actor activeUnit;

    public ExplorationGame()
    {
        setPreferredSize(new Dimension(VIEW_WIDTH,VIEW_HEIGHT));
        setFocusable(true);

        // make the player and enemy objects
        enemies = new ArrayList<>();
        items = new ArrayList<>();

        // call spawnInitialObjects
        spawnInitialObjects();

        //player
        playerUnits = new ArrayList<>();
        playerUnits.add(new Knight(WORLD_WIDTH/2, WORLD_HEIGHT/2, 100, 5, new Color(180, 100, 60, 255)));
        playerUnits.add(new Knight(WORLD_WIDTH/2, WORLD_HEIGHT/2 + TILE_SIZE*2, 100, 5, new Color(0,30,105, 255)));
        playerUnits.add(new Knight(WORLD_WIDTH/2 + TILE_SIZE*2, WORLD_HEIGHT/2, 100, 5, new Color(30,150,20, 255)));
        activeUnit = playerUnits.get(0);

        // add key listener
        addKeyListener(new KeyAdapter() {
           @Override
           public void keyPressed(KeyEvent e) {
               switch (e.getKeyCode()) {
                   case KeyEvent.VK_UP:
                       movePressed = true;
                       moveDirection = Direction.UP;
                       break;
                   case KeyEvent.VK_DOWN:
                       movePressed = true;
                       moveDirection = Direction.DOWN;
                       break;
                   case KeyEvent.VK_LEFT:
                       movePressed = true;
                       moveDirection = Direction.LEFT;
                       break;
                   case KeyEvent.VK_RIGHT:
                       movePressed = true;
                       moveDirection = Direction.RIGHT;
                       break;
                   case KeyEvent.VK_SPACE:
                       activeUnit.attack(enemies);
                       break;
                   case KeyEvent.VK_SHIFT:
                       switchActiveUnit();
                       break;
               }
           }

           @Override
           public void keyReleased(KeyEvent e) {
               switch (e.getKeyCode()) {
                   case KeyEvent.VK_UP:
                       movePressed = false;
                       break;
                   case KeyEvent.VK_DOWN:
                       movePressed = false;
                       break;
                   case KeyEvent.VK_LEFT:
                       movePressed = false;
                       break;
                   case KeyEvent.VK_RIGHT:
                       movePressed = false;
                       break;
               }
           }
       }
       );

        //Game Loop
        Timer timer = new Timer(16, e -> {
            updateGame();
            repaint();
        });
        timer.start();
    }

    private void spawnInitialObjects()
    {
        //spawn enemies
        for (int i = 0; i < 20; i++) {
            enemies.add(new Enemy(
                    random.nextInt(WORLD_WIDTH),
                    random.nextInt(WORLD_HEIGHT)
            ));
        }
        //spawn HP items
        for (int i = 0; i < 10; i++) {
            items.add(new HPItem(
                    random.nextInt(WORLD_WIDTH),
                    random.nextInt(WORLD_HEIGHT)
            ));
        }
    }

    private void switchActiveUnit()
    {
        //change which unit the player is controlling
        int currentIndex = playerUnits.indexOf(activeUnit);
        int nextIndex = (currentIndex +1) % playerUnits.size();
        activeUnit = playerUnits.get(nextIndex);
    }

    private void updateGame()
    {
        //update visibility by moving the view window on the world
        // this will include calls to getViewBounds.
        Rectangle viewBounds = getViewBounds();
        long currentTime = System.currentTimeMillis();

        // for each enemy, update the enemies
        for (Actor enemy : enemies) {
            if (((Enemy)enemy).getVisibile(viewBounds)) {
                ((Enemy)enemy).move(activeUnit);
                if (currentTime - lastDamageTime >= DAMAGE_COOLDOWN)
                {
                    enemy.attack(playerUnits);
                    lastDamageTime = currentTime;
                }
            }
        }

        //update HP items
        for (HPItem item : items) {
            item.visible = viewBounds.contains(item.x, item.y);
            if (item.visible && viewBounds.contains(activeUnit.x, activeUnit.y)) {
                if (Math.abs(item.x - activeUnit.x) < TILE_SIZE &&
                        Math.abs(item.y - activeUnit.y) < TILE_SIZE) {
                    activeUnit.changeHP(20);
                    items.remove(item);
                    break;
                }
            }
        }
        //update player stuff
        if (movePressed)
        {
            activeUnit.move(moveDirection);
        }
        //check if the player died here
        for (Actor unit : playerUnits) {
            if (unit.hp <= 0) {
                gameOver = true;
                break;
            }
        }

    }

    private Rectangle getViewBounds()
    {
        //return a view boundary centered on the active unit
        int viewX = activeUnit.x - VIEW_WIDTH/2;
        int viewY = activeUnit.y - VIEW_HEIGHT/2;
        return new Rectangle(viewX, viewY, VIEW_WIDTH,VIEW_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        //this function puts all the graphics on the display.
        // it overrides a function in the swing library.
        // when swing decides to repaint, it will call this overridden function, and consequently paint all our stuff.
        
        //set up the canvas
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Rectangle viewBounds = getViewBounds();
        //draw background grid
        g2d.setColor(Color.GREEN.darker());
        int startX = viewBounds.x - (viewBounds.x % TILE_SIZE);
        int startY = viewBounds.y - (viewBounds.y % TILE_SIZE);
        for (int x = startX; x < viewBounds.x + VIEW_WIDTH; x += TILE_SIZE){
            for (int y = startY; y < viewBounds.y + VIEW_HEIGHT; y += TILE_SIZE){
                g2d.drawRect(x - viewBounds.x, y - viewBounds.y, TILE_SIZE, TILE_SIZE);
            }
        }
        //draw the world edge lines
        //draw HP items if visible
        g2d.setColor(Color.BLUE);
        for (HPItem item : items) {
            if (item.visible) {
                g2d.fillRect(item.x - viewBounds.x, item.y - viewBounds.y,
                        TILE_SIZE/2, TILE_SIZE/2);
            }
        }
        //draw enemies if visible
        for (Actor enemy : enemies) {
            g2d.setColor(enemy.color);
            if (((Enemy)enemy).visible) {
                g2d.fillRect(enemy.x - viewBounds.x, enemy.y - viewBounds.y,
                        TILE_SIZE, TILE_SIZE);
            }
        }
        // draw the player
        for(Actor unit : playerUnits)
        {
            g2d.setColor(unit.color);
            g2d.fillRect(unit.x - viewBounds.x, unit.y - viewBounds.y, TILE_SIZE, TILE_SIZE);
        }
        //draw attacks
        if (activeUnit.isAttacking()) {
            g2d.setColor(new Color(100,0,255,100));
            Rectangle attackArea = activeUnit.getAttackArea();
            g2d.fillRect(attackArea.x -viewBounds.x, attackArea.y - viewBounds.y,
                    attackArea.width, attackArea.height);
        }
        //draw HP bar
        g2d.setColor(Color.BLACK);
        g2d.fillRect(10,10,102,22);
        g2d.setColor(Color.RED);
        g2d.fillRect(11, 11, activeUnit.hp, 20);
        g2d.setColor(Color.WHITE);
        g2d.drawString("HP: " + activeUnit.hp + "/" + activeUnit.maxHP, 15,25);
        //if gameover, then draw gameover text
        if (gameOver) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 52));
            String loseText = "GAME OVER";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(loseText);
            int textHeight = fm.getHeight();
            int x = (VIEW_WIDTH - textWidth) /2;
            int y = (VIEW_HEIGHT - textHeight) /2;
            g2d.drawString(loseText, x, y);
        }
    }
}
