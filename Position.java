import java.util.Objects;  

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



    public boolean equalPosition(Position other) {
    double epsilon = 1e-6; // Tolérance pour les doubles
    return Math.abs(this.Axes - other.  Axes) < epsilon && Math.abs(this.Ayes - other.Ayes) < epsilon;
}


    public double distanceA(Position positionZ){
        return Math.sqrt(Math.pow(this.Axes - positionZ.Axes, 2)+Math.pow(this.Ayes - positionZ.Ayes, 2));
    }


    //--------------------------------------------------------------------------------------

    //cette partie sera indispensable pour determiner les boucle dans la navigation des drones avec cartes car il faut     

    // Redéfinition de la méthode equals() : 
    // deux objets Position doivent être considérés comme égaux si leurs coordonnées (x, y, z) sont égales.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Si les deux objets sont identiques
        if (obj == null || getClass() != obj.getClass()) return false;  // Vérifier si l'objet est de la même classe
        Position position = (Position) obj;  // Convertir l'objet en Position
        return this.Axes == position.getX() && this.Ayes == position.getY() && this.hauteur == position.getH();  // Comparer les coordonnées
    }

    // Redéfinition de la méthode hashCode() qui sera utilisée dans hashMap plutart dans la classe droneAvecCarte 
    @Override
    public int hashCode() {
        return Objects.hash(Axes,Ayes, hauteur);  // Générer le code de hachage basé sur les coordonnées : Si deux objets sont considérés comme égaux (selon la méthode equals()), leur code de hachage doit être identique.
    }


}