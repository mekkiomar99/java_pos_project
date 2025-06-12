// GestionUtilisateurs.java
import java.util.ArrayList;
import java.util.List;

public class GestionUtilisateurs {
    private List<Employe> employes;
    private int nextId = 1;

    public GestionUtilisateurs() {
        this.employes = new ArrayList<>();
        // Ajout d'un admin par défaut
        employes.add(new Employe(nextId++, "Admin", "gérant", "admin123"));
        employes.add(new Employe(nextId++, "rhimi", "serveur", "rhimi123"));
    }

    public Employe authentifier(String nom, String mdp) {
        for (Employe e : employes) {
            if (e.getNom().equals(nom) && e.authentifier(mdp)) {
                return e;
            }
        }
        return null;
    }

    public void ajouterEmploye(String nom, String role, String mdp) {
        employes.add(new Employe(nextId++, nom, role, mdp));
    }

    public void modifierEmploye(int id, String nom, String role, boolean actif) {
        for (Employe e : employes) {
            if (e.getId() == id) {
                e.setNom(nom);
                e.setRole(role);
                e.setActif(actif);
                break;
            }
        }
    }

    public void changerMotDePasse(int id, String nouveauMdp) {
        for (Employe e : employes) {
            if (e.getId() == id) {
                e.setMotDePasse(nouveauMdp);
                break;
            }
        }
    }

    public List<Employe> getEmployes() {
        return new ArrayList<>(employes);
    }
}