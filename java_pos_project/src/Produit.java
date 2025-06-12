public class Produit {
    private int id;
    private String nom;
    private double prix;
    private int stock;
    private Categorie categorie;

    public Produit(int id, String nom, double prix, int stock, Categorie categorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
        this.categorie = categorie;
    }

    // Getters & setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public int getStock() { return stock; }
    public Categorie getCategorie() { return categorie; }

    public void setStock(int stock) { this.stock = stock; }
}
