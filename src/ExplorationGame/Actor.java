package ExplorationGame;

import ExplorationGame.ExplorationGame.Direction;

import java.awt.*;
import java.util.ArrayList;

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
