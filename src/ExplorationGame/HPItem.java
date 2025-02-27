package ExplorationGame;

//The HPItem class satisfies the requirement that our player character health can go up and down
public class HPItem {
    //these just have a location and visibility. We'll set up collisions and HP modification on the world map.
    int x, y;
    boolean visible;
    HPItem(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.visible = false;
    }
}
