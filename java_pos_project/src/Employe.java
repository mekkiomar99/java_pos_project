// Employe.java
public class Employe {
    private int id;
    private String nom;
    private String role; // "serveur", "gérant", "cuisinier"
    private String motDePasse;
    private boolean actif;

    public Employe(int id, String nom, String role, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.role = role;
        this.motDePasse = motDePasse;
        this.actif = true;
    }

    public boolean authentifier(String mdp) {
        return this.actif && this.motDePasse.equals(mdp);
    }

    // Getters & setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getRole() { return role; }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public void setRole(String role) {
        this.role = role;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}