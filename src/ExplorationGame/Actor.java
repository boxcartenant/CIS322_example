package ExplorationGame;

import ExplorationGame.ExplorationGame.Direction;

import java.awt.*;
import java.util.ArrayList;

import static ExplorationGame.ExplorationGame.*;

public abstract class Actor {

    //this class will contain the movement and HP effects for our player characters and enemies
    //making it abstract enables me to use it as a reference to objects of any class-type that extends it.
    //also, since it's abstract, every class-type that extends it must implement its abstract methods.
    
    public int x,y,hp,maxHP,speed;
    public Color color;
    public Direction lastDirection;

    public Actor(int x, int y, int maxHP, int speed, Color color)
    {
        this.x = x;
        this.y = y;
        this.maxHP = maxHP;
        this.speed = speed;
        this.color = color;
        this.hp = maxHP;
        this.lastDirection = Direction.UP;
    }
    
    public void move(Direction moveDir)
    {
        switch (moveDir) {
            case Direction.UP:
                y = Math.max(0, y - speed);
                lastDirection = Direction.UP;
                break;
            case Direction.DOWN:
                y = Math.min(WORLD_HEIGHT - TILE_SIZE, y + speed);
                lastDirection = Direction.DOWN;
                break;
            case Direction.LEFT:
                x = Math.max(0, x - speed);
                lastDirection = Direction.LEFT;
                break;
            case Direction.RIGHT:
                x = Math.min(WORLD_WIDTH - TILE_SIZE, x + speed);
                lastDirection = Direction.RIGHT;
                break;
        }
        //move in the direction indicated by moveDir
        // at a rate determined by speed
        // and set last_direction equal to moveDir
    }

    public void changeHP(int change)
    {
        //add change to HP, and then make sure it's between 0-max
    }

    //these abstract methods must be implemented by any class that extends Actor.
    public abstract void attack(ArrayList<Actor> enemies);
    public abstract Rectangle getAttackArea();
    public abstract boolean isAttacking();
}
