public class Categorie {
    private int id;
    private String nom;

    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Getters & setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
