import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Environnement {
    public List<drone> drones;
    public List<Position> obstacles;
    private boolean[][] carte;
    private List<Position> positionsDest;
    public int width, height;

    public Environnement(int width, int height){    
        this.width = width;
        this.height = height;
        drones = new ArrayList<>();
        obstacles = new ArrayList<>();
        positionsDest = new ArrayList<>();     //les points de livraison 
        carte = new boolean[width][height];

    }

    public boolean[][] getCarte(){
        return carte;
    }
    public int getWeight(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public void generateRandomObstacles(String difficulty) {
        int totalCells = width * height;
        int obstacleCount;

        //  number of obstacles based on the difficulty level
        switch (difficulty.toLowerCase()) {
            case "easy":
                obstacleCount = totalCells / 6;  // 6% of the grid
                break;
            case "medium":
                obstacleCount = totalCells / 5;  // 20% of the grid
                break;
            case "hard":
                obstacleCount = (int) (totalCells * 0.5);  // 30% of the grid
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level. Choose easy, medium, or hard.");
        }

        Random rand = new Random();
        Set<String> occupiedPositions = new HashSet<>();

        while (occupiedPositions.size() < obstacleCount) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            // s'assurer que la position est unique et non occupée 
            String positionKey = x + "," + y;
            if (!occupiedPositions.contains(positionKey)) {
                definirObstacles(x, y);  // Placer l'obstacle
                occupiedPositions.add(positionKey);
            }
        }
    }
    
    public void definirObstacles(int x, int y){
        if(x>=0 && x<getWeight() && y>=0 && y<getHeight()){
            carte[x][y] = true;
            obstacles.add(new Position(x, y, 0));
        }
        else{
            System.out.println("Obstacle hors de la carte");
        }
    }
    
    /*public void resetCarte() {
        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[0].length; j++) {
                carte[i][j] = false;
            }
        }
        obstacles.clear();
    }*/
        public boolean isPositionOccupied(int x, int y) {
            for (drone d : drones) {
                if ((int) d.getPosition().getX() == x && (int) d.getPosition().getY() == y) {
                    return true;
                }
            }
            return carte[x][y];
        }


    public void ajoutDrone(drone D){
        drones.add(D);
    }
   
    public void ajoutDest(Position posFinale){
        if((posFinale.getX()>=0 && posFinale.getX()<getWeight()) && (posFinale.getY()>=0 && posFinale.getY()<getHeight()))
            {
                positionsDest.add(posFinale);
            }
        else{
            System.out.println("Destination hors de la carte");
        }
    }    


    public List<drone> getdrones(){return drones;}
    public List<Position> getobstacles(){return obstacles;}
    public List<Position> getdestination(){return positionsDest;}


}