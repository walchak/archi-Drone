
import java.util.*;

public class DroneAvecCarte extends drone {
   
    private int rayonDetection;

    private TraqueurTrajectoire tracker;
    
    
    // Mémoire des obstacles détectés
    private Set<Position> memoireObstacles;
    // Mémoire des positions visitées avec nombre de passages
    private Map<Position, Integer> positionsVisitees;
    // Limite de passages sur une même position avant de détecter une boucle
    private static final int LIMITE_BOUCLE = 3;
    
    private static final int[][] DIRECTIONS = {
        {0, 1},   // haut
        {1, 0},   // droite
        {0, -1},  // bas
        {-1, 0}   // gauche
    };


    //constructeur
    public DroneAvecCarte(int numSerie, double niveauBatterie, Position positionDrone, int rayonDetection) {
    super(numSerie, niveauBatterie, positionDrone);
    this.rayonDetection = rayonDetection;
    this.memoireObstacles = new HashSet<>();
    this.positionsVisitees = new HashMap<>();
    this.tracker = new TraqueurTrajectoire();
    this.positionDepart = new Position(positionDrone.getX(), positionDrone.getY(), 0);
}

    private List<Position> detecterObstacles(Environnement env) {
            List<Position> nouveauxObstacles = new ArrayList<>();
            int x = (int)positionDrone.getX();
            int y = (int)positionDrone.getY();

            for (int dx = -rayonDetection; dx <= rayonDetection; dx++) {
                for (int dy = -rayonDetection; dy <= rayonDetection; dy++) {
                    int newX = x + dx;
                    int newY = y + dy;
                    
                    if (newX >= 0 && newX < env.getWeight() && 
                        newY >= 0 && newY < env.getHeight()) {
                        Position pos = new Position(newX, newY, 0);
                        
                        if (env.isPositionOccupied(newX, newY)) {
                            if (!memoireObstacles.contains(pos)) {
                                nouveauxObstacles.add(pos);
                                memoireObstacles.add(pos);
                            }
                        }
                    }
                }
            }
            return nouveauxObstacles;
        }


    public void naviguerLivraisonEtRetour(Position dest, Environnement env, EnvironmentPanel panel, drone d) {
            // Navigation vers la destination
            naviguerVersDestination(dest, env, panel, d, "LIVRAISON");
        
        
            // Pause de 2 secondes
            System.out.println("\n🕒 Pause de 2 secondes à la destination...");
             System.out.println("\n +++++++++++++++ commande livrée ++++++++++++++ ");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // Retour au point de départ ()
            System.out.println("\n🏠 Retour au point de départ...");
            parcourirTrajectoireEnArriere(env,panel);
        
    }
    public void naviguerVersDestination(Position dest, Environnement env, EnvironmentPanel panel,drone d, String phased) {
        // Reset des positions visitées pour chaque nouvelle navigation
        positionsVisitees.clear();
        boolean estBloque = false;
        int etape = 0;

        
        while (!positionDrone.equalPosition(dest) && !estBloque) {
            // Mettre à jour le compteur de passages
            etape++;
            System.out.println("\n--- Drone numéro :  " + this.numSerie + " ---");
            System.out.println("\n--- Étape " + etape + " ---");
            d.toString();
            
            

            Position posActuelle = new Position(positionDrone.getX(), positionDrone.getY(), 0);
            positionsVisitees.put(posActuelle, positionsVisitees.getOrDefault(posActuelle, 0) + 1);
            
            // Vérifier si on est dans une boucle
            if (positionsVisitees.get(posActuelle) > LIMITE_BOUCLE) {
                System.out.println("⚠️ ALERTE:Boucle détectée ! Recherche d'un chemin alternatif...");
                if (!rechercheCheminAlternatif(dest, env)) {
                    estBloque = true;
                    System.out.println("❌ ÉCHEC: Drone bloqué - Impossible de trouver un chemin alternatif");
                    break;
                }
            }

            List<Position> obstaclesDetectes = detecterObstacles(env);
            if (!obstaclesDetectes.isEmpty()) {
                System.out.println("📡 " + obstaclesDetectes.size()+"  Nouveaux obstacles détectés: " );
                
            }

            Position nextPosition = calculerProchainDeplacement(dest, env);
            // Vérifier à la fois les obstacles et les autres drones
            boolean positionOccupee = env.isPositionOccupied((int)nextPosition.getX(), (int)nextPosition.getY());
            boolean obstaclePresent = env.getCarte()[(int)nextPosition.getX()][(int)nextPosition.getY()];
        
            if ( !positionOccupee && !obstaclePresent) {
                //System.out.println("🔄 Déplacement: " + positionDrone + " -> " + nextPosition);
                positionDrone.setX(nextPosition.getX());
                positionDrone.SetY(nextPosition.getY());
                //System.out.println("📍 Nouvelle position: " + positionDrone);
                System.out.println("📏 Distance restante: " + positionDrone.distanceA(dest));


                if (!positionOccupee && !obstaclePresent) {
                    positionDrone.setX(nextPosition.getX());
                    positionDrone.SetY(nextPosition.getY());
                    tracker.addPosition(new Position(nextPosition.getX(), nextPosition.getY(), 0));
                    System.out.println("\uD83D\uDCCF Distance restante: " + positionDrone.distanceA(dest));
                    
                panel.repaint();
                
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("⚠️ Navigation interrompue");
                    break;
                }
            } else {
                estBloque = true;
                System.out.println("❌ ÉCHEC:Aucun chemin possible vers la destination");
                break;
            }
        }
         if (positionDrone.equalPosition(dest)) {
            System.out.println("\n✅ SUCCÈS: Destination atteinte en " + etape + " pas!");
            System.out.println("📍  position finale : " + "( " + this.positionDrone.getX() + "," + this.positionDrone.getY() + " )");
            System.out.println("\n=== Fin de la navigation du drone " + this.numSerie +  "===" );
            
        }
     } 
    }
    public void parcourirTrajectoireEnArriere(Environnement env, EnvironmentPanel panel) {
    if (tracker != null && tracker.getTrajectoire() != null) {
        List<Position> trajectoirePositions = tracker.getTrajectoire();
        
        for (int i = trajectoirePositions.size() - 1; i >= 0; i--) {
            Position position = trajectoirePositions.get(i);

            // Vérifiez si la position est occupée dans l'environnement
            if (!env.isPositionOccupied((int) position.getX(), (int) position.getY())) {
                // Déplacez le drone à cette position
                positionDrone.setX(position.getX());
                positionDrone.SetY(position.getY());
                panel.repaint();

                // Pause pour la visualisation
                try {
                    Thread.sleep(200); // Pause de 200ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    } else {
        System.out.println("Traqueur ou trajectoire non initialisé !");
    }
}

    public Position calculerProchainDeplacement(Position dest, Environnement env) {
        int currentX = (int)positionDrone.getX();
        int currentY = (int)positionDrone.getY();
        
        double bestDistance = Double.MAX_VALUE;
        Position bestPosition = null;
        List<Position> possibleMoves = new ArrayList<>();

        // Collecter tous les mouvements possibles avec leurs scores
        for (int[] dir : DIRECTIONS) {
            int newX = currentX + dir[0];
            int newY = currentY + dir[1];
            
            if (newX >= 0 && newX < env.getWeight() && 
                newY >= 0 && newY < env.getHeight()) {
                
                Position newPos = new Position(newX, newY, 0);
                double distance = newPos.distanceA(dest);

                // Si la position est libre (pas d'obstacle ni de drone)
                if (!memoireObstacles.contains(newPos) && !env.isPositionOccupied(newX, newY)) {
                    // Calculer un score pour cette position
                    double score = distance;
                    
                    // Pénaliser les positions trop visitées
                    int visites = positionsVisitees.getOrDefault(newPos, 0);
                    score += visites * 5; // Augmentation de la pénalité pour les positions visitées
                    
                    // Favoriser les positions moins visitées quand le drone est potentiellement bloqué
                    Position posActuelle = new Position(currentX, currentY, 0);
                    if (positionsVisitees.getOrDefault(posActuelle, 0) >= LIMITE_BOUCLE - 1) {
                        score -= (1.0 / (visites + 1)) * 10; // Bonus pour les positions moins visitées
                    }
                    
                    possibleMoves.add(newPos);
                    if (score < bestDistance) {
                        bestDistance = score;
                        bestPosition = newPos;
                    }
                }
            }
        }

        // Si aucune position n'a été trouvée, chercher une position de repli
        if (bestPosition == null) {
            // Chercher la position accessible la moins visitée
            int minVisites = Integer.MAX_VALUE;
            for (int[] dir : DIRECTIONS) {
                int newX = currentX + dir[0];
                int newY = currentY + dir[1];
                
                if (newX >= 0 && newX < env.getWeight() && 
                    newY >= 0 && newY < env.getHeight()) {
                    
                    Position newPos = new Position(newX, newY, 0);
                    if (!env.isPositionOccupied(newX, newY)) {
                        int visites = positionsVisitees.getOrDefault(newPos, 0);
                        if (visites < minVisites) {
                            minVisites = visites;
                            bestPosition = newPos;
                        }
                    }
                }
            }
        }

        return bestPosition;
    }
    private boolean rechercheCheminAlternatif(Position dest, Environnement env) {
        // Essayer de s'éloigner temporairement de la destination
        int currentX = (int)positionDrone.getX();
        int currentY = (int)positionDrone.getY();
        
        // Chercher la direction la moins visitée
        Position meilleurEchappatoire = null;
        int minVisites = Integer.MAX_VALUE;

        for (int[] dir : DIRECTIONS) {
            int newX = currentX + dir[0];
            int newY = currentY + dir[1];
            
            if (newX >= 0 && newX < env.getWeight() && 
                newY >= 0 && newY < env.getHeight()) {
                
                Position newPos = new Position(newX, newY, 0);
                if (!memoireObstacles.contains(newPos)) {
                    int visites = positionsVisitees.getOrDefault(newPos, 0);
                    if (visites < minVisites) {
                        minVisites = visites;
                        meilleurEchappatoire = newPos;
                    }
                }
            }
        }

        return meilleurEchappatoire != null;
    }

    public Set<Position> getObstaclesConnus() {
        return new HashSet<>(memoireObstacles);
    }

    public List<Position> getTrajectoire() {
        return tracker.getTrajectoire();
    }
 }
