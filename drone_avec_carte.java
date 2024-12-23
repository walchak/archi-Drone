public class drone_avec_carte extends drone {
    private String carte_interne;

    public drone_avec_carte(int var1, position var2, String var3, double var4) {
       super(var1, var2, var4);
       this.carte_interne = var3;
    }
 
    public void mettreAJourCarte(String var1) {
       this.carte_interne = var1;
    }
 
    public String toString() {
       String var10000 = super.toString();
       return var10000 + ", avec carte interne : " + this.carte_interne;
    }
}
