public class drone_avec_carte extends drone {
    private String carte_interne;

    // Constructeur
    public drone_avec_carte(int var1, double var4, position var2, String var3) {
       super(var1, var4, var2);  // Corrigé : les types et l'ordre correspondent au constructeur parent
       this.carte_interne = var3;
    }

    // Méthode pour mettre à jour la carte interne
    public void mettreAJourCarte(String var1) {
       this.carte_interne = var1;
    }

    // Surcharge de toString
    public String toString() {
       String var10000 = super.toString();
       return var10000 + ", avec carte interne : " + this.carte_interne;
    }
}

