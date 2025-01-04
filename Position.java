
public class Position {
    private double Axes;
    private double Ayes;
    private double hauteur;

    public  Position (double x, double y, double h){
        this.Axes = x;
        this.Ayes = y;
        this.hauteur = h;
    }


//getters
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


    public boolean equalPosition(Position positionZ){
        return this.Axes == positionZ.Axes && this.Ayes == positionZ.Ayes;
    }

    public double distanceA(Position positionZ){
        return Math.sqrt(Math.pow(this.Axes - positionZ.Axes, 2)+Math.pow(this.Ayes - positionZ.Ayes, 2));
    }
}