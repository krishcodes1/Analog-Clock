import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AnalogClock extends JFrame {
    private ClockPanel clockPanel;

    public AnalogClock() {
        setTitle("Analog Clock");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center frame

        clockPanel = new ClockPanel();
        add(clockPanel);
        // time delay for hands
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clockPanel.repaint();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AnalogClock().setVisible(true);
            }
        });
    }

    class ClockPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            LocalTime now = LocalTime.now();
            int hour = now.getHour();
            int minute = now.getMinute();
            int second = now.getSecond();

            int clockRadius = Math.min(getWidth(), getHeight()) / 2;
            int clockX = getWidth() / 2;
            int clockY = getHeight() / 2;

            // clock skeletion setup
            g.setColor(Color.BLACK);
            g.drawOval(clockX - clockRadius, clockY - clockRadius, 2 * clockRadius, 2 * clockRadius);

            // numbers setup / points
            g.drawString("12", clockX - 5, clockY - clockRadius + 15);
            g.drawString("3", clockX + clockRadius - 15, clockY + 5);
            g.drawString("6", clockX - 5, clockY + clockRadius - 5);
            g.drawString("9", clockX - clockRadius + 5, clockY + 5);

            double angle;
            int handMax;

            // second hand calc
            angle = Math.PI * (second - 15) / 30;
            handMax = clockRadius - 20;
            g.setColor(Color.RED);
            g.drawLine(clockX, clockY,
                    clockX + (int) (handMax * Math.cos(angle)),
                    clockY + (int) (handMax * Math.sin(angle)));

            // minute hand calc
            angle = Math.PI * (minute - 15) / 30;
            handMax = clockRadius - 30;
            g.setColor(Color.BLUE);
            g.drawLine(clockX, clockY,
                    clockX + (int) (handMax * Math.cos(angle)),
                    clockY + (int) (handMax * Math.sin(angle)));

            // hour hand calc
            angle = Math.PI * (hour - 3) / 6;
            handMax = clockRadius - 40;
            g.setColor(Color.GREEN);
            g.drawLine(clockX, clockY,
                    clockX + (int) (handMax * Math.cos(angle)),
                    clockY + (int) (handMax * Math.sin(angle)));

            // digital clock setup
            g.setColor(Color.BLACK);
            g.drawString(now.format(DateTimeFormatter.ofPattern("hh:mm:ss a")), clockX - 50, clockY + clockRadius + 20);
        }
    }
}
