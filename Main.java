
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.util.*;

public class Main {
    public static void main(String[] argvs) {
        Environnement env = new Environnement(42, 54);

        // Create obstacles
        env.generateRandomObstacles("medium");

        // Create multiple drones and targets
        for (int i = 0; i < 5; i++) {  // Example with 5 drones
            position dronePos = new position(i * 5 + 5, i * 5 + 5, 0);
            drone myDrone = new drone(100 + i, 100.0 - i * 10, dronePos);
            env.ajout_drone(myDrone);

            position targetPos = new position(30 + i * 2, 14 + i * 2, 0);
            env.ajout_dest(targetPos);
        }

        // Create and display GUI
        JFrame frame = new JFrame("Drone Environment");
        frame.setSize(1000, 800);  // Adjusted size for visibility
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EnvironmentPanel panel = new EnvironmentPanel(env);
        frame.add(panel);
        frame.setVisible(true);

        // Start drone navigation in separate threads
        for (int i = 0; i < env.getdrones().size(); i++) {
            drone currentDrone = env.getdrones().get(i);
            position currentTarget = env.getdestination().get(i);

            new Thread(() -> {
                currentDrone.navigateTo(currentTarget, env, panel);
                System.out.println("Drone position : " + currentDrone.getposition());
            }).start();
        }

        // Dynamic obstacle simulation
        new Thread(() -> {
            Random rand = new Random();
            while (true) {
                int x = rand.nextInt(env.width);
                int y = rand.nextInt(env.height);
                env.definir_obstacles(x, y);
                panel.repaint();

                try {
                    Thread.sleep(5000);  // Modify obstacles every 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
