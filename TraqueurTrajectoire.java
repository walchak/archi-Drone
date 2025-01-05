import java.util.*;

public class TraqueurTrajectoire {
    private List<Position> trajectorePositions;
    
    public TraqueurTrajectoire() {
        this.trajectorePositions = new ArrayList<>();
    }
    
    public void addPosition(Position pos) {
        trajectorePositions.add(new Position(pos.getX(), pos.getY(), 0));
    }
    
    public List<Position> getTrajectoire() {
        return trajectorePositions;
    }
}