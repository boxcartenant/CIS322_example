package ExplorationGame;

import ExplorationGame.ExplorationGame.Direction;

import java.awt.*;
import java.util.ArrayList;

public abstract class Actor {
    protected int x,y,hp,maxHP,speed;
    private Color color;
    protected Direction lastDirection;

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
//    + Get/set ADD THESE LATER
    public void move(Direction moveDir)
    {
        //move in the direction indicated by "speed"
    }

    public void changeHP(int change)
    {
        //add change to HP, and then make sure it's between 0-max
    }

    public abstract void attack(ArrayList<Actor> enemies);
    public abstract Rectangle getAttackArea();
    public abstract boolean isAttacking();
}
