
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



    @Override
    public String toString(){
         
         System.out.println("Drone definie sous: " + this.numSerie + "  ; sa position : " 
         + "( " + this.positionDrone.getX() + "," + this.positionDrone.getY() + " )");
         return "Drone definie sous: " + this.numSerie + ", son niveau de batterie : " + this.niveauBatterie + ", se trouve actuellement à : " 
         + "( " + this.positionDrone.getX() + "," + this.positionDrone.getY() + " )" ;
    }

    protected Position getPosition(){return this.positionDrone;}
}



