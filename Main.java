import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;


public class Main {
    public static void main(String[] argvs) {
        // Création de l'environnement
        Environnement env = new Environnement(42, 54);
    
        // Création des 3 drones avec des positions différentes
        Position dronePos1 = new Position(5, 5, 0);
        Position dronePos2 = new Position(20, 20, 0);
        Position dronePos3 = new Position(18, 25, 0);

        DroneAvecCarte drone1 = new DroneAvecCarte(1,100.0, dronePos1, 3);
        DroneAvecCarte drone2 = new DroneAvecCarte(2,100.0, dronePos2, 3);
        DroneAvecCarte drone3 = new DroneAvecCarte(3,100.0,dronePos3, 3);

        // Ajout des drones à l'environnement
        env.ajoutDrone(drone1);
        env.ajoutDrone(drone2);
        env.ajoutDrone(drone3);

        // Création des positions cibles pour chaque drone
        
        Position targetPos1 = new Position(30, 34, 0);
        Position targetPos2 = new Position(28, 28, 0);
        Position targetPos3 = new Position(35, 25, 0);

        env.ajoutDest(targetPos1);
        env.ajoutDest(targetPos2);
        env.ajoutDest(targetPos3);

        
        //création des obstacles 
        env.generateRandomObstacles("easy");

        // Création et affichage de l'interface graphique
        JFrame frame = new JFrame("Simulation Multi-Drones");
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EnvironmentPanel panel = new EnvironmentPanel(env);
        frame.add(panel);
        frame.setVisible(true);

        // Démarrage des drones dans des threads séparés
        Thread thread1 = new Thread(() -> {
            System.out.println("\n=== Démarrage navigation Drone 1 ===");
            drone1.naviguerLivraisonEtRetour(targetPos1, env, panel,drone1);
            System.out.println("Drone 1 position finale : " + drone1.getPosition());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("\n=== Démarrage navigation Drone 2 ===");
            drone2.naviguerLivraisonEtRetour(targetPos2, env, panel,drone2);
            System.out.println("Drone 2 position finale : " + drone2.getPosition());
        });

        Thread thread3 = new Thread(() -> {
            System.out.println("\n=== Démarrage navigation Drone 3 ===");
            drone3.naviguerLivraisonEtRetour(targetPos3, env, panel,drone3);
            System.out.println("Drone 3 position finale : " + drone3.getPosition());
        });

        // Démarrage séquentiel des threads avec délai
        try {
            thread1.start();
            Thread.sleep(1000); // Attendre 1 seconde
            thread2.start();
            Thread.sleep(1000); // Attendre 1 seconde
            thread3.start();

            // Attendre que tous les drones terminent
            thread1.join();
            thread2.join();
            thread3.join();

            System.out.println("\n=== Simulation terminée ===");
            System.out.println("Statistiques finales :");
            System.out.println("Drone 1 - Obstacles détectés : " + drone1.getObstaclesConnus().size());
            System.out.println("Drone 2 - Obstacles détectés : " + drone2.getObstaclesConnus().size());
            System.out.println("Drone 3 - Obstacles détectés : " + drone3.getObstaclesConnus().size());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}