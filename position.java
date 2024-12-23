public class position {
    private double Axes;
    private double Ayes;
    private double hauteur;
 
    public position(double var1, double var3, double var5) {
       this.Axes = var1;
       this.Ayes = var3;
       this.hauteur = var5;
    }
 
    public double getX() {
       return this.Axes;
    }
 
    public double getY() {
       return this.Ayes;
    }
 
    public double getH() {
       return this.hauteur;
    }
 
    public void setX(double var1) {
       this.Axes = var1;
    }
 
    public void SetY(double var1) {
       this.Ayes = var1;
    }
 
    public void SetH(double var1) {
       this.hauteur = var1;
    }
 
    public boolean Equel_po(position var1) {
       return this.Axes == var1.Axes && this.Ayes == var1.Ayes;
    }
 
    public double distanceA(position var1) {
       return Math.sqrt(Math.pow(this.Axes - var1.Axes, 2.0) + Math.pow(this.Ayes - var1.Ayes, 2.0));
    }
}
