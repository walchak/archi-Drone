public class DroneAvecCarte extends drone {
    private String carteInterne;

    public DroneAvecCarte(int var1, double var4, Position var2, String var3) {
       super(var1, var4, var2); // j'ai corrigé l'erreur ici : maintenant les types et l'ordre correspondent au constructeur parent
       this.carteInterne = var3;
    }
 
    public void mettreAJourCarte(String var1) {
       this.carteInterne = var1;
    }
 
    public String toString() {
       String var10000 = super.toString();
       return var10000 + ", avec carte interne : " + this.carteInterne;
    }
}
