public class LigneCommande {
    private Produit produit;
    private int quantite;

    public LigneCommande(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public double calculerSousTotal() {
        return produit.getPrix() * quantite;
    }

    public Produit getProduit() { return produit; }
    public int getQuantite() { return quantite; }
}
