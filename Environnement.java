import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Environnement {
    public List<drone> drones;
    public List<position> obstacles;
    private boolean[][] carte;
    private List<position> positions_dest;
    public int width, height;

    public Environnement(int width, int height){    
        this.width = width;
        this.height = height;
         drones = new ArrayList<>();
         obstacles = new ArrayList<>();
         positions_dest = new ArrayList<>();
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

        // Ensure the position is unique and not already occupied
        String positionKey = x + "," + y;
        if (!occupiedPositions.contains(positionKey)) {
            definir_obstacles(x, y);  // Place obstacle
            occupiedPositions.add(positionKey);
        }
    }
}
    
    public void definir_obstacles(int x, int y){
        if(x>=0 && x<getWeight() && y>=0 && y<getHeight()){
            carte[x][y] = true;
            obstacles.add(new position(x, y, 0));
        }
        else{
            System.out.println("Obstacle hors de la carte");
        }
    }
    public void resetCarte() {
        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[0].length; j++) {
                carte[i][j] = false;
            }
        }
        obstacles.clear();
    }


    public void ajout_drone(drone D){
        drones.add(D);
    }
   
    public void ajout_dest(position pos_finale){
        if((pos_finale.getX()>=0 && pos_finale.getX()<getWeight()) && (pos_finale.getY()>=0 && pos_finale.getY()<getHeight()))
            {
                positions_dest.add(pos_finale);
            }
        else{
            System.out.println("Destination hors de la carte");
        }
    }    


    public List<drone> getdrones(){return drones;}
    public List<position> getobstacles(){return obstacles;}
    public List<position> getdestination(){return positions_dest;}


}