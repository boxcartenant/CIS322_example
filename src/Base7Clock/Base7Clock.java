package Base7Clock;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.text.DecimalFormat;
import java.util.Calendar;

public class Base7Clock extends JFrame {
    private ClockPanel clockPanel;

    public Base7Clock() {
        setTitle("Base-7 Chronometer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);

        clockPanel = new ClockPanel();
        add(clockPanel);

        // Set up a Swing Timer to repaint the clock every 100 milliseconds
        javax.swing.Timer timer = new javax.swing.Timer(100, e -> clockPanel.repaint());
        timer.start();

        setLocationRelativeTo(null);
    }

    static class ClockPanel extends JPanel {
        private static final int CLOCK_SIZE = 300;
        private static final double TICK_LENGTH = 0.1 * CLOCK_SIZE / 2;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2 - 50;

            // Draw clock face
            g2d.setColor(Color.WHITE);
            g2d.fillOval(centerX - CLOCK_SIZE / 2, centerY - CLOCK_SIZE / 2,
                    CLOCK_SIZE, CLOCK_SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(centerX - CLOCK_SIZE / 2, centerY - CLOCK_SIZE / 2,
                    CLOCK_SIZE, CLOCK_SIZE);

            // Get current base-7 time
            double[] base7Time = getBase7Time();
            double cycles = base7Time[0]; // Larger unit
            double phases = base7Time[1];
            double spans = base7Time[2];
            double ticks = base7Time[3];  // Smallest unit

            // Draw tick marks
            drawTickMarks(g2d, centerX, centerY);

            // Draw clock hands
            drawHand(g2d, centerX, centerY, cycles, 7, 0.5, Color.RED);    // Cycles hand
            drawHand(g2d, centerX, centerY, phases, 7, 0.7, Color.BLUE);   // Phases hand
            drawHand(g2d, centerX, centerY, spans, 7, 0.85, Color.GREEN);  // Spans hand
            drawHand(g2d, centerX, centerY, ticks, 7, 0.95, Color.BLACK);  // Ticks hand

            // Draw base-7 date below the clock
            drawDate(g2d, centerX, centerY + CLOCK_SIZE / 2 + 20);
        }

        private void drawTickMarks(Graphics2D g2d, int centerX, int centerY) {
            for (int i = 0; i < 7; i++) {
                double angle = Math.toRadians((i * 360.0 / 7) - 90);
                int x1 = (int) (centerX + Math.cos(angle) * (CLOCK_SIZE / 2));
                int y1 = (int) (centerY + Math.sin(angle) * (CLOCK_SIZE / 2));
                int x2 = (int) (centerX + Math.cos(angle) * (CLOCK_SIZE / 2 - TICK_LENGTH));
                int y2 = (int) (centerY + Math.sin(angle) * (CLOCK_SIZE / 2 - TICK_LENGTH));
                g2d.drawLine(x1, y1, x2, y2);
            }
        }

        private void drawHand(Graphics2D g2d, int centerX, int centerY,
                              double value, double base, double length, Color color) {
            double angle = Math.toRadians((value / base * 360) - 90);
            int handLength = (int) (CLOCK_SIZE / 2 * length);
            int x = (int) (centerX + Math.cos(angle) * handLength);
            int y = (int) (centerY + Math.sin(angle) * handLength);

            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(centerX, centerY, x, y);
        }

        private void drawDate(Graphics2D g2d, int x, int y) {
            int[] base7Date = getBase7Date();
            int month = base7Date[0];
            int week = base7Date[1];
            int day = base7Date[2];

            DecimalFormat df = new DecimalFormat("0");
            String dateStr = "M:" + df.format(month) +
                    " W:" + df.format(week) +
                    " D:" + df.format(day);

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(dateStr);
            g2d.drawString(dateStr, x - textWidth / 2, y);
        }

        private double[] getBase7Time() {
            Calendar cal = Calendar.getInstance();
            long millisToday = (cal.get(Calendar.HOUR_OF_DAY) * 3600L +
                    cal.get(Calendar.MINUTE) * 60L +
                    cal.get(Calendar.SECOND)) * 1000L +
                    cal.get(Calendar.MILLISECOND);
            double fractionOfDay = (double) millisToday / (24 * 60 * 60 * 1000);
            double base7Ticks = fractionOfDay * 2401; // 7^4 = 2401 ticks per day

            double cycles = base7Ticks / (7 * 7 * 7);   // 343 ticks per cycle
            double phases = (base7Ticks / (7 * 7)) % 7;  // 49 ticks per phase
            double spans = (base7Ticks / 7) % 7;         // 7 ticks per span
            double ticks = base7Ticks % 7;               // 1 tick

            // Include fractional parts for smooth hand movement
            double cycleFraction = cycles - Math.floor(cycles);
            double phaseFraction = phases - Math.floor(phases);
            double spanFraction = spans - Math.floor(spans);
            double tickFraction = ticks - Math.floor(ticks);

            return new double[]{
                    Math.floor(cycles) + cycleFraction,
                    Math.floor(phases) + phaseFraction,
                    Math.floor(spans) + spanFraction,
                    Math.floor(ticks) + tickFraction
            };
        }

        private int[] getBase7Date() {
            long millisSinceEpoch = System.currentTimeMillis();
            long daysSinceEpoch = millisSinceEpoch / (1000 * 60 * 60 * 24);

            // Base-7 year has 343 days (7 months * 7 weeks * 7 days)
            int dayOfYear = (int) (daysSinceEpoch % 343);
            int month = dayOfYear / 49;        // 49 days per month
            int week = (dayOfYear % 49) / 7;   // 7 days per week
            int day = dayOfYear % 7;           // Day within the week

            return new int[]{month, week, day};
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Base7Clock().setVisible(true);
        });
    }
}