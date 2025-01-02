import java.util.*;

class Environnement {
    public List<drone> drones;
    public List<position> obstacles;
    private boolean[][] carte;
    private List<position> positions_dest;
    public int width, height;

    public Environnement(int width, int height) {
        this.width = width;
        this.height = height;
        drones = new ArrayList<>();
        obstacles = new ArrayList<>();
        positions_dest = new ArrayList<>();
        carte = new boolean[width][height];
    }

    public boolean[][] getCarte() {
        return carte;
    }

    public void generateRandomObstacles(String difficulty) {
        int totalCells = width * height;
        int obstacleCount;

        switch (difficulty.toLowerCase()) {
            case "easy":
                obstacleCount = totalCells / 6;
                break;
            case "medium":
                obstacleCount = totalCells / 5;
                break;
            case "hard":
                obstacleCount = (int) (totalCells * 0.5);
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level. Choose easy, medium, or hard.");
        }

        Random rand = new Random();
        Set<String> occupiedPositions = new HashSet<>();

        while (occupiedPositions.size() < obstacleCount) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            String positionKey = x + "," + y;
            if (!occupiedPositions.contains(positionKey)) {
                definir_obstacles(x, y);
                occupiedPositions.add(positionKey);
            }
        }
    }

    public void definir_obstacles(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            carte[x][y] = true;
            obstacles.add(new position(x, y, 0));
        } else {
            System.out.println("Obstacle hors de la carte");
        }
    }

    public boolean isPositionOccupied(int x, int y) {
        for (drone d : drones) {
            if ((int) d.getposition().getX() == x && (int) d.getposition().getY() == y) {
                return true;
            }
        }
        return carte[x][y];
    }

    public void ajout_drone(drone D) {
        drones.add(D);
    }

    public void ajout_dest(position pos_finale) {
        if ((pos_finale.getX() >= 0 && pos_finale.getX() < width) && (pos_finale.getY() >= 0 && pos_finale.getY() < height)) {
            positions_dest.add(pos_finale);
        } else {
            System.out.println("Destination hors de la carte");
        }
    }

    public List<drone> getdrones() {
        return drones;
    }

    public List<position> getdestination() {
        return positions_dest;
    }
}
