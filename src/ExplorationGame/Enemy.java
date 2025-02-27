package ExplorationGame;

import java.awt.*;
import java.util.ArrayList;

import static ExplorationGame.ExplorationGame.TILE_SIZE;

// All the enemies in the game will be objects of this class type.
public class Enemy extends Actor{
    //enemies will be visible if they're inside the render window
    protected boolean visible;
    //attack damage tells how much they hurt the player
    protected int attackDamage;
    public Enemy(int x, int y)
    {
        //the Actor superclass doesn't have a no-arg constructor, 
        // so we have to set all of its variables in this constructor, 
        // or else call the super constructor
        super(x,y,50,2,new Color(255,0,0)); //Actor(int x, int y, int maxHP, int speed, Color color)
        this.visible = true;
        this.attackDamage = 5;
    }
    //getters and setters
    public void setVisible(boolean visible){this.visible = visible;}
    public boolean getVisibile(){return this.visible;}
    //we're just going to have a getter for the whole position and size of the enemy
    //rectangles are useful, so returning that type will help us later.
    Rectangle getBounds(){return new Rectangle(x,y,TILE_SIZE,TILE_SIZE);}
    //I want the enemy to track the player and follow him
    //rather than writing the code to find the user's direction and then move toward it in the ExplorationGame
    //I'm overriding the Actor move function here.
    //this will also mean that enemies don't have to move strictly in directions defined by our "Direction" enum.
    public void move(Actor activeUnit)
    {
        //compare location of self and activeUnit
        //pick a new location "speed" distance toward activeUnit
        //update x,y
    }

    //this Override flag is optional, but it's helpful for the programmer and sometimes the IDE to know
    // explicitly that this method is intended to override something in the superclass.
    // Some compilers will also throw an error if you place the override flag and then write something that isn't
    // actually in your superclass. That can be helpful if you misspell the name of the function your'e overriding.
    @Override
    public void attack(ArrayList<Actor> enemies) {
        //under whatever circumstances, damage the player
    }
    @Override
    public Rectangle getAttackArea() {
        //we'll probably just damage the player on collision, rather than implementing a fancy attack
        //so most likely this method and isAttacking won't have any code in them.
        return null;
    }
    @Override
    public boolean isAttacking() {
        return false;
    }
}
