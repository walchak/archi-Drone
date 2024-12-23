import java.util.ArrayList;
import java.util.List;

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