import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;

public class Main {

    public static void main(String[] argvs) {
        Environnement env = new Environnement(42, 54);

        // Create obstacles
        env.generateRandomObstacles("medium");

        // Create drone
        Position dronePos = new Position(20, 20, 0);  // Drone within grid bounds
        drone myDrone = new drone(101, 80.5, dronePos);
        env.ajoutDrone(myDrone);

        // Create target
        Position targetPos = new Position(30, 14, 0);  // Target within grid bounds
        env.ajoutDest(targetPos);

        // Create and display GUI
        JFrame frame = new JFrame("Drone Environment");
        frame.setSize(1000, 800);  // Adjusted size for visibility
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EnvironmentPanel panel = new EnvironmentPanel(env);
        frame.add(panel);
        frame.setVisible(true);

        // Start drone navigation in a separate thread
        new Thread(() -> {
            myDrone.navigateTo(targetPos, env, panel);
            System.out.println("Drone position : " + myDrone.getPosition() );

        }).start();

    }
}