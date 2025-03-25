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
        return null;
    }

    @Override
    public boolean isAttacking() {
        return false;
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
    }

    private boolean isEnemyHit(Actor enemy, Rectangle attackArea) {
        return true;
    }

}
