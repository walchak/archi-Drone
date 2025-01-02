import java.util.*;

class drone {
    private int num_serie;
    public double niveau_batterie;
    private position position_drone;

    public drone(int num_serie, double niveau_batterie, position position) {
        this.num_serie = num_serie;
        this.niveau_batterie = niveau_batterie;
        this.position_drone = position;
    }

    public void moveTo(position newPosition) {
        this.position_drone = newPosition;
    }

    public void navigateTo(position target, Environnement env, EnvironmentPanel panel) {
        List<position> path = findPath(position_drone, target, env.getCarte());
        for (position step : path) {
            if (!env.isPositionOccupied((int) step.getX(), (int) step.getY())) {
                position_drone.setX(step.getX());
                position_drone.SetY(step.getY());
                panel.repaint();

                try {
                    Thread.sleep(200);  // Visualize movement
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<position> findPath(position start, position target, boolean[][] carte) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        Set<position> closedSet = new HashSet<>();

        openList.add(new Node(start, null, 0, start.distanceA(target)));

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.getPosition().Equel_po(target)) {
                return reconstructPath(current);
            }

            closedSet.add(current.getPosition());

            for (position neighbor : getNeighbors(current.getPosition(), carte)) {
                if (closedSet.contains(neighbor)) continue;

                double tentativeG = current.getG() + current.getPosition().distanceA(neighbor);
                Node neighborNode = new Node(neighbor, current, tentativeG, tentativeG + neighbor.distanceA(target));
                openList.add(neighborNode);
            }
        }

        return new ArrayList<>();
    }

    private List<position> reconstructPath(Node node) {
        List<position> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node.getPosition());
            node = node.getParent();
        }
        return path;
    }

    private List<position> getNeighbors(position pos, boolean[][] carte) {
        List<position> neighbors = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            int newX = (int) pos.getX() + dir[0];
            int newY = (int) pos.getY() + dir[1];

            if (newX >= 0 && newX < carte.length && newY >= 0 && newY < carte[0].length && !carte[newX][newY]) {
                neighbors.add(new position(newX, newY, 0));
            }
        }

        return neighbors;
    }

    @Override
    public String toString() {
        return "Drone ID: " + this.num_serie + ", Battery Level: " + this.niveau_batterie + ", Position: " + this.position_drone;
    }

    public position getposition() {
        return this.position_drone;
    }
}
