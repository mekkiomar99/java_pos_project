public class Table {
    private int numero;
    private String etat; // "libre", "occupée", "en attente", etc.

    public Table(int numero) {
        this.numero = numero;
        this.etat = "libre";
    }

    public int getNumero() { return numero; }
    public String getEtat() { return etat; }

    public void changerEtat(String nouvelEtat) {
        this.etat = nouvelEtat;
    }
}
