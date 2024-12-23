


public class drone {
    private int num_serie;
    public double niveau_batterie;
    private position position_drone;
  


    public  drone (int num_serie,double niveau_batterie, position position ){
        this.num_serie = num_serie;
        this.niveau_batterie = niveau_batterie;
        this.position_drone = position;
        
    }

    public void moveTo(position newPosition) {
        this.position_drone = newPosition;
    }

    public void navigateTo(position target, boolean[][] carte, EnvironmentPanel panel) {
        while (!position_drone.Equel_po(target)) {
            int dx = (int) Math.signum(target.getX() - position_drone.getX());
            int dy = (int) Math.signum(target.getY() - position_drone.getY());
            int nextX = (int) position_drone.getX() + dx;
            int nextY = (int) position_drone.getY() + dy;
    
            // Check for obstacles
            if (carte[nextX][nextY]) {
                // Adjust movement to avoid obstacle (basic detour)
                if (!carte[nextX][(int) position_drone.getY()]) {
                    nextY = (int) position_drone.getY(); // Move horizontally only
                } else if (!carte[(int) position_drone.getX()][nextY]) {
                    nextX = (int) position_drone.getX(); // Move vertically only
                }
            }
            panel.repaint();
            // Update position
            position_drone.setX(nextX);
            position_drone.SetY(nextY);
    
            // Add a delay to visualize movement
            try {
                Thread.sleep(200); // 200 ms delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    

    @Override
    public String toString(){
         return "Drone definie sous: " + this.num_serie + ", son niveau de batterie : " + this.niveau_batterie + ", se trouve actuellement à : " 
         + this.position_drone ;
    }

    public position getposition(){return this.position_drone;}

}


class drone_avec_carte extends drone {
      private boolean[][] carte_interne;

      public drone_avec_carte(int num_serie, position position_actuelle, double niveau_batterie,boolean[][] carte){
        super(num_serie, niveau_batterie, position_actuelle);
        this.carte_interne = carte;
      }
      
      public void mettreAJourCarte(boolean[][] nouvelleCarte) {
        this.carte_interne = nouvelleCarte;
    }
    public boolean[][] getCarte() {
        return this.carte_interne;
    }

    @Override
    public String toString() {
        return super.toString() + ", avec carte interne : " + this.carte_interne;
    }

}

class position {
    private double Axes;
    private double Ayes;
    private double hauteur;

    public  position (double x, double y, double h){
        this.Axes = x;
        this.Ayes = y;
        this.hauteur = h;
    }


//geters
    public double getX(){
        return Axes;
    } 

    public double getY(){
        return Ayes;
    }

    public double getH(){
        return hauteur;
    }

//Setters
   public void setX(double x1){
        this.Axes = x1;
       }

    public void SetY(double y1){
        this.Ayes = y1;
    }

    public void SetH(double h1){
        this.hauteur = h1;
    }


    public boolean Equel_po(position positionZ){
        return this.Axes == positionZ.Axes && this.Ayes == positionZ.Ayes;
    }

    public double distanceA(position positionZ){
        return Math.sqrt(Math.pow(this.Axes - positionZ.Axes, 2)+Math.pow(this.Ayes - positionZ.Ayes, 2));
    }
}