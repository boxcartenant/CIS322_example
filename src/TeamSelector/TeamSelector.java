package TeamSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamSelector extends JFrame {
    private final TeamPanel teamPanel;

    public TeamSelector() {
        setTitle("Team Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        teamPanel = new TeamPanel();
        add(teamPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TeamSelector().setVisible(true);
        });
    }
}

class TeamPanel extends JPanel {
    private static final int RECT_WIDTH = 150;
    private static final int RECT_HEIGHT = 100;
    private static final int GAP = 50;
    private static final int CYCLE_COUNT = 150; // Number of highlight cycles
    private static final int CYCLE_DELAY = 10; // Delay per cycle in ms

    private final List<TeamRectangle> rectangles;
    private int currentHighlightIndex;
    private int cycleCounter;
    private Timer timer;
    private boolean isCycling;
    private final Random random;

    public TeamPanel() {
        rectangles = new ArrayList<>();
        random = new Random();
        currentHighlightIndex = -1;
        cycleCounter = 0;
        isCycling = false;

        // Initialize rectangles: Team A, Team B, Team C
        rectangles.add(new TeamRectangle("Team A", 50, 100, Color.BLUE));
        rectangles.add(new TeamRectangle("Team B", 50 + RECT_WIDTH + GAP, 100, Color.GREEN));
        rectangles.add(new TeamRectangle("Team C", 50 + 2 * (RECT_WIDTH + GAP), 100, Color.RED));

        // Mouse listener for toggling rectangles
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isCycling) { // Only allow toggling when not cycling
                    for (TeamRectangle rect : rectangles) {
                        if (rect.contains(e.getX(), e.getY())) {
                            rect.toggleEnabled();
                            repaint();
                            break;
                        }
                    }
                }
            }
        });

        // Start button to initiate cycling
        JButton startButton = new JButton("Start Selection");
        startButton.setBounds(50, 250, 150, 30);
        setLayout(null);
        add(startButton);

        startButton.addActionListener(e -> {
            if (!isCycling && hasEnabledRectangles()) {
                startCycling();
            }
        });
    }

    private boolean hasEnabledRectangles() {
        return rectangles.stream().anyMatch(TeamRectangle::isEnabled);
    }

    private void startCycling() {
        isCycling = true;
        cycleCounter = 0;
        currentHighlightIndex = -1;

        timer = new Timer(CYCLE_DELAY+CYCLE_COUNT-cycleCounter, e -> {
            cycleCounter++;
            if (cycleCounter <= CYCLE_COUNT) {
                // Randomly select an enabled rectangle
                List<TeamRectangle> enabledRects = rectangles.stream()
                        .filter(TeamRectangle::isEnabled)
                        .toList();
                if (!enabledRects.isEmpty()) {
                    int newIndex = random.nextInt(rectangles.size());
                    while ((newIndex == currentHighlightIndex) || !(rectangles.get(newIndex).isEnabled())) {
                        newIndex = random.nextInt(rectangles.size());
                    }
                    currentHighlightIndex = newIndex;
                }
                repaint();
            } else {
                // Stop cycling and settle on a random enabled rectangle
                timer.stop();
                List<TeamRectangle> enabledRects = rectangles.stream()
                        .filter(TeamRectangle::isEnabled)
                        .toList();
                if (!enabledRects.isEmpty()) {
                    int newIndex = random.nextInt(rectangles.size());
                    while (!(rectangles.get(newIndex).isEnabled())) {
                        newIndex = random.nextInt(rectangles.size());
                    }
                    currentHighlightIndex = newIndex;
                } else {
                    currentHighlightIndex = -1;
                }
                isCycling = false;
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all rectangles
        for (int i = 0; i < rectangles.size(); i++) {
            TeamRectangle rect = rectangles.get(i);
            // Set color based on enabled state and highlight
            if (!rect.isEnabled()) {
                g2d.setColor(Color.GRAY);
            } else if (i == currentHighlightIndex && isCycling) {
                g2d.setColor(rect.getHighlightColor());
            } else if (i == currentHighlightIndex && !isCycling) {
                g2d.setColor(rect.getHighlightColor().brighter());
            } else {
                g2d.setColor(rect.getBaseColor());
            }

            // Draw rectangle
            g2d.fillRect(rect.getX(), rect.getY(), RECT_WIDTH, RECT_HEIGHT);

            // Draw border
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(rect.getX(), rect.getY(), RECT_WIDTH, RECT_HEIGHT);

            // Draw label
            g2d.setColor(rect.isEnabled() ? Color.WHITE : Color.DARK_GRAY);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String label = rect.getLabel();
            int textWidth = fm.stringWidth(label);
            int textHeight = fm.getAscent();
            int textX = rect.getX() + (RECT_WIDTH - textWidth) / 2;
            int textY = rect.getY() + (RECT_HEIGHT + textHeight) / 2 - 5;
            g2d.drawString(label, textX, textY);

            // Draw underline for final selected team
            if (i == currentHighlightIndex && !isCycling && rect.isEnabled()) {
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                int underlineY = textY + 2; // Position underline just below text
                g2d.drawLine(textX, underlineY, textX + textWidth, underlineY);
            }
        }
    }
}

class TeamRectangle {
    private final String label;
    private final int x;
    private final int y;
    private final Color baseColor;
    private final Color highlightColor;
    private boolean enabled;

    public TeamRectangle(String label, int x, int y, Color highlightColor) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.baseColor = highlightColor.darker();
        this.highlightColor = highlightColor;
        this.enabled = true;
    }

    public String getLabel() {
        return label;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggleEnabled() {
        enabled = !enabled;
    }

    public boolean contains(int mx, int my) {
        return mx >= x && mx <= x + 150 && my >= y && my <= y + 100;
    }
}