import javax.swing.*;
import ExplorationGame.ExplorationGame;

public class Main {
    public static void main(String[] args) {
        //create the JFrame to hold all our graphical objects
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Exploration Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //ExplorationGame extends JPanel, so we can add it to the frame
            frame.add(new ExplorationGame());
            frame.pack();
            //center it on the display
            frame.setLocationRelativeTo(null);
            //show the frame
            frame.setVisible(true);
        });
    }
}
