import javax.swing.*;
import ExplorationGame.ExplorationGame;

public class Main {
    public static void main(String[] args) {
        //We're using swing for our graphics
        //invokelater puts our explorationgame in another thread
        SwingUtilities.invokeLater(() -> {
            //this jframe is the window that our game will be in.
            JFrame frame = new JFrame("Exploration Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //ExplorationGame extends JPanel, so we can add it to the frame
            frame.add(new ExplorationGame());
            //pack prepares the frame for rendering
            frame.pack();
            //center it on the display
            frame.setLocationRelativeTo(null);
            //show the frame
            frame.setVisible(true);
        });
    }
}

// 3/22/2025
// added line initializing activeUnit, starting timer, and changed horizontal moves in actor from y to x