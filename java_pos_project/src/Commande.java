import java.util.*;

public class Commande {
    private int id;
    private Date date;
    private Table table;
    private Employe serveur;
    private List<LigneCommande> lignes;
    private String statut; // "en cours", "servie", "réglée"

    public Commande(int id, Table table, Employe serveur) {
        this.id = id;
        this.date = new Date();
        this.table = table;
        this.serveur = serveur;
        this.lignes = new ArrayList<>();
        this.statut = "en cours";
    }

    public void ajouterLigne(Produit produit, int quantite) {
        lignes.add(new LigneCommande(produit, quantite));
    }

    public double calculerTotal() {
        return lignes.stream().mapToDouble(LigneCommande::calculerSousTotal).sum();
    }

    public int getId() { return id; }
    public List<LigneCommande> getLignes() { return lignes; }
    public String getStatut() { return statut; }

    public void changerStatut(String nouveauStatut) {
        this.statut = nouveauStatut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Table getTable() { return table;
    }

    public Employe getServeur() {
        return serveur;
    }
}

