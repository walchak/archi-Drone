
import java.util.*;


public class drone {
    protected int numSerie;
    protected double niveauBatterie;
    protected Position positionDrone;
    protected static Position positionDepart; // Nouvelle variable pour stocker le point de départ

    //constucteur
    public  drone (int numSerie,double niveauBatterie, Position positionDrone ){
        this.numSerie = numSerie;
        this.niveauBatterie = niveauBatterie;
        this.positionDrone = positionDrone;
        
    }

    //
    public void moveTo(Position newPosition) {
        this.positionDrone = newPosition;
    }

  

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//la méthode d'avant que j'ai commenté ne permet pas de trouver le chemin optimale
//et échoue si les obstacles bloquent la trajectoire
//-----> Pour résoudre ce problème la nouvelle version de navigateTo implémente l'algo A* (je l'ai trouvé sur le net) qui garantit que le drone trouvera un chemin meme dans des env complexes :) 


    public void navigateTo(Position target, Environnement env, EnvironmentPanel panel) {
        // Trouve le chemin du point de départ (position actuelle du drone) à la cible
        List<Position> path = findPath(positionDrone, target, env.getCarte());
        
        // Parcourt chaque étape du chemin trouvé
        for (Position step : path) {
            // Vérifie si la position actuelle dans le chemin n'est pas occupée par un obstacle
            if (!env.isPositionOccupied((int) step.getX(), (int) step.getY())) {
                // Met à jour les coordonnées du drone pour avancer à la position suivante
                positionDrone.setX(step.getX());
                positionDrone.SetY(step.getY());

                // Rafraîchit l'affichage pour montrer la position mise à jour du drone
                panel.repaint(); // -----> appel la méthode paintComponent qui est redéfinie dans la classe environnementPanel 

                try {
                    // Pause pour ralentir le mouvement du drone et visualiser son déplacement
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // Gère les interruptions éventuelles du thread (utile en cas d'annulation ou de fin prématurée)
                    e.printStackTrace();
                }
            }
        }
    }


        // méthode findPath
        public List<Position> findPath(Position start, Position target, boolean[][] carte) {

            PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
            Set<Position> closedSet = new HashSet<>();
            openList.add(new Node(start, null, 0, start.distanceA(target)));

            while (!openList.isEmpty()) {
                Node current = openList.poll();
                if (current.getPosition().equalPosition(target)) {
                    return reconstructPath(current);
                }

                closedSet.add(current.getPosition());

                for (Position neighbor : getNeighbors(current.getPosition(), carte)) {
                    if (closedSet.contains(neighbor)) continue;

                    double tentativeG = current.getG() + current.getPosition().distanceA(neighbor);
                    Node neighborNode = new Node(neighbor, current, tentativeG, tentativeG + neighbor.distanceA(target));
                    openList.add(neighborNode);
                }
            }
                    return new ArrayList<>();
        }

        private List<Position> reconstructPath(Node node) {
            List<Position> path = new ArrayList<>();
            while (node != null) {
                path.add(0, node.getPosition());
                node = node.getParent();
            }
            return path;
        }

        private List<Position> getNeighbors(Position pos, boolean[][] carte) {
            List<Position> neighbors = new ArrayList<>();
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : directions) {
                int newX = (int) pos.getX() + dir[0];
                int newY = (int) pos.getY() + dir[1];
                if (newX >= 0 && newX < carte.length && newY >= 0 && newY < carte[0].length && !carte[newX][newY]) {
                    neighbors.add(new Position(newX, newY, 0));
                }
            }
            return neighbors;
        }





    @Override
    public String toString(){
         
         System.out.println("Drone definie sous: " + this.numSerie + "  ; sa position : " 
         + "( " + this.positionDrone.getX() + "," + this.positionDrone.getY() + " )");
         return "Drone definie sous: " + this.numSerie + ", son niveau de batterie : " + this.niveauBatterie + ", se trouve actuellement à : " 
         + "( " + this.positionDrone.getX() + "," + this.positionDrone.getY() + " )" ;
    }

    protected Position getPosition(){return this.positionDrone;}
}



