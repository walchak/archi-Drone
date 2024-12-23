import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;

public class Main {

    public static void main(String[] argvs) {
        Environnement env = new Environnement(42, 54);

        // Create obstacles
        env.definir_obstacles(25, 30);
        env.definir_obstacles(25, 1);
        env.definir_obstacles(25, 20);
        env.definir_obstacles(24, 21);
        env.definir_obstacles(25, 30);
        env.definir_obstacles(25, 1);
        env.definir_obstacles(23, 20);
        env.definir_obstacles(25, 31);
        env.definir_obstacles(23, 10);
        env.definir_obstacles(25, 1);
        env.definir_obstacles(23, 20);
        env.definir_obstacles(25, 11);
        env.definir_obstacles(25, 34);
        env.definir_obstacles(25, 1);
        env.definir_obstacles(23, 20);
        env.definir_obstacles(23, 21);
        env.definir_obstacles(26, 30);
        env.definir_obstacles(25, 1);
        env.definir_obstacles(25, 20);
        env.definir_obstacles(25, 21);

        // Create drones
        position dronePos = new position(20, 20, 0);  // Drone within grid bounds
        drone myDrone = new drone(101, 80.5, dronePos);
        env.ajout_drone(myDrone);

        // Create targets
        position targetPos = new position(30, 14, 0);  // Target within grid bounds
        env.ajout_dest(targetPos);  // Target within grid bounds

        // Create and display GUI
        JFrame frame = new JFrame("Drone Environment");
        frame.setSize(80, 80);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EnvironmentPanel panel = new EnvironmentPanel(env);
        frame.add(panel);
        frame.setVisible(true);

        // Start drone navigation in a separate thread
        new Thread(() -> {
            myDrone.navigateTo(targetPos, env.getCarte(),panel);
            SwingUtilities.invokeLater(() -> frame.repaint());
        }).start();
    }
}

class EnvironmentPanel extends JPanel {
    private Environnement env;

    public EnvironmentPanel(Environnement env) {
        this.env = env;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the grid
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < env.getCarte().length; i++) {
            for (int j = 0; j < env.getCarte()[0].length; j++) {
                if (env.getCarte()[i][j]) {
                    g.setColor(Color.DARK_GRAY);  // Obstacles
                    g.fillRect(i * 20, j * 20, 20, 20);
                } else {
                    g.setColor(Color.WHITE);  // Free cells
                    g.drawRect(i * 20, j * 20, 20, 20);
                }
            }
        }

        // Draw drones
        g.setColor(Color.BLUE);
        for (drone d : env.getdrones()) {
            position pos = d.getposition();
            g.fillOval((int) pos.getX() * 20, (int) pos.getY() * 20, 20, 20);
        }

        // Draw targets
        g.setColor(Color.GREEN);
        for (position target : env.getdestination()) {
            g.fillOval((int) target.getX() * 20, (int) target.getY() * 20, 20, 20);
        }
    }
}
