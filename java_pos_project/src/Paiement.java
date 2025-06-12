import java.util.Date;

public class Paiement {
    private int id;
    private Commande commande;
    private double montant;
    private String typePaiement; // "espèces", "carte", etc.
    private Date date;

    public Paiement(int id, Commande commande, double montant, String typePaiement) {
        this.id = id;
        this.commande = commande;
        this.montant = montant;
        this.typePaiement = typePaiement;
        this.date = new Date();
    }

    // Getters
    public int getId() { return id; }
    public Commande getCommande() { return commande; }
    public double getMontant() { return montant; }
    public String getTypePaiement() { return typePaiement; }
    public Date getDate() { return date; }
}
