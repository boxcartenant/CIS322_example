package ExplorationGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static ExplorationGame.ExplorationGame.TILE_SIZE;

public class Knight extends Actor {
    public boolean attacking, visible;
    private int attackRange, attackWidth;
    public Knight (int x, int y, int maxHP, int speed, Color color)
    {
        super(x, y, maxHP, speed, color);
        attackRange = TILE_SIZE *2;
        attackWidth = TILE_SIZE;
    }

    @Override
    public Rectangle getAttackArea() {
        switch (lastDirection) {
            case UP:
                return new Rectangle(x, y - attackRange, attackWidth, attackRange);
            case DOWN:
                return new Rectangle(x, y + attackRange, attackWidth, attackRange);
            case LEFT:
                return new Rectangle(x - attackRange, y, attackRange, attackWidth);
            case RIGHT:
                return new Rectangle(x + TILE_SIZE, y, attackRange, attackWidth);
        }
        return null;
    }

    @Override
    public boolean isAttacking() {
        return attacking;
    }

    @Override
    public void attack(ArrayList<Actor> enemies) {
        attacking = true;
        int damage = -25;

        Rectangle attackarea = getAttackArea();

        for (Actor enemy : enemies)
        {
            if (((Enemy)enemy).visible && isEnemyHit(enemy, attackarea))
            {
                enemy.changeHP(damage);
                if (enemy.hp <= 0)
                {
                    enemies.remove(enemy);
                    break;
                }
            }
        }

        Timer attackTimer = new Timer(200, e -> attacking = false);
        attackTimer.setRepeats(false);
        attackTimer.start();
    }

    private boolean isEnemyHit(Actor enemy, Rectangle attackArea) {
        Rectangle enemyBounds = ((Enemy)enemy).getBounds();
        if ((enemyBounds.x + enemyBounds.width <= attackArea.x) || (attackArea.x + attackArea.width <= enemyBounds.x)
        || (enemyBounds.y + enemyBounds.height <= attackArea.y) || (attackArea.y + attackArea.height <= enemyBounds.y))
        {
            return false;
        }
        return true;
    }

}
