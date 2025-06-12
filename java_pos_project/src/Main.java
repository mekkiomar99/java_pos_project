import java.util.*;
import java.util.InputMismatchException;

public class Main {
    private static List<Produit> produits = new ArrayList<>();
    private static List<Table> tables = new ArrayList<>();
    private static List<Commande> commandes = new ArrayList<>();
    private static GestionUtilisateurs gestionUtilisateurs = new GestionUtilisateurs();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextCommandeId = 1;
    private static Employe utilisateurCourant = null;

    public static void main(String[] args) {
        initialiserProduitsEtTables();

        while (true) {
            // Écran de connexion
            utilisateurCourant = null;
            while (utilisateurCourant == null) {
                System.out.println("\n=== SYSTÈME DE GESTION DE RESTAURANT ===");
                System.out.println("=== CONNEXION ===");
                System.out.print("Nom d'utilisateur : ");
                String nom = scanner.nextLine();
                System.out.print("Mot de passe : ");
                String mdp = scanner.nextLine();

                utilisateurCourant = gestionUtilisateurs.authentifier(nom, mdp);
                if (utilisateurCourant == null) {
                    System.out.println("Erreur : Identifiants incorrects !");
                } else {
                    System.out.println("\nConnexion réussie. Bienvenue, " + utilisateurCourant.getNom() +
                            " (" + utilisateurCourant.getRole() + ")");
                }
            }

            // Session utilisateur
            boolean deconnecter = false;
            while (!deconnecter) {
                afficherMenuPrincipal();
                String choix = scanner.nextLine().trim();

                switch (choix) {
                    case "1":
                        afficherProduits();
                        break;
                    case "2":
                        if (verifierPermission("serveur")) {
                            creerCommande();
                        }
                        break;
                    case "3":
                        afficherCommandes();
                        break;
                    case "4":
                        payerCommande();
                        break;
                    case "5":
                        if (verifierPermission("gérant")) {
                            gererUtilisateurs();
                        }
                        break;
                    case "d":
                        System.out.println("\nDéconnexion de " + utilisateurCourant.getNom() + "...");
                        deconnecter = true;
                        break;
                    case "0":
                        System.out.println("\nFermeture du système. À bientôt !");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Erreur : Choix invalide. Veuillez réessayer.");
                }
            }
        }
    }

    // ============ METHODES UTILITAIRES ============

    private static boolean verifierPermission(String roleRequis) {
        if (!utilisateurCourant.getRole().equals(roleRequis)) {
            System.out.println("Erreur : Action réservée aux " + roleRequis + "s !");
            return false;
        }
        return true;
    }

    private static int saisirEntier(String message) {
        while (true) {
            try {
                System.out.print(message);
                int valeur = Integer.parseInt(scanner.nextLine());
                if (valeur >= 0) return valeur;
                System.out.println("Erreur : Veuillez entrer un nombre positif.");
            } catch (NumberFormatException e) {
                System.out.println("Erreur : Veuillez entrer un nombre valide.");
            }
        }
    }

    private static double saisirDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double valeur = Double.parseDouble(scanner.nextLine());
                if (valeur >= 0) return valeur;
                System.out.println("Erreur : Veuillez entrer un nombre positif.");
            } catch (NumberFormatException e) {
                System.out.println("Erreur : Veuillez entrer un nombre décimal valide.");
            }
        }
    }

    private static boolean saisirOuiNon(String message) {
        while (true) {
            System.out.print(message + " (o/n) : ");
            String reponse = scanner.nextLine().trim().toLowerCase();
            if (reponse.equals("o") || reponse.equals("oui")) return true;
            if (reponse.equals("n") || reponse.equals("non")) return false;
            System.out.println("Erreur : Répondez par 'o' (oui) ou 'n' (non).");
        }
    }

    // ============ GESTION MENU ============

    private static void afficherMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Afficher les produits");
        System.out.println("2. Créer une commande");
        System.out.println("3. Afficher les commandes");
        System.out.println("4. Payer une commande");
        if (utilisateurCourant.getRole().equals("gérant")) {
            System.out.println("5. Gérer les utilisateurs");
        }
        System.out.println("d. Se déconnecter");
        System.out.println("0. Quitter l'application");
        System.out.print("Votre choix : ");
    }

    // ============ INITIALISATION ============

    private static void initialiserProduitsEtTables() {
        // Catégories
        Categorie boissons = new Categorie(1, "Boissons");
        Categorie plats = new Categorie(2, "Plats principaux");
        Categorie desserts = new Categorie(3, "Desserts");

        // Produits
        produits.add(new Produit(1, "Café", 3.5, 50, boissons));
        produits.add(new Produit(2, "Thé", 2.5, 30, boissons));
        produits.add(new Produit(3, "Jus d'orange", 4.0, 20, boissons));
        produits.add(new Produit(4, "Pizza Margherita", 12.5, 15, plats));
        produits.add(new Produit(5, "Salade César", 9.0, 10, plats));
        produits.add(new Produit(6, "Tiramisu", 6.5, 8, desserts));

        // Tables
        for (int i = 1; i <= 10; i++) {
            tables.add(new Table(i));
        }
    }

    // ============ GESTION PRODUITS ============

    private static void afficherProduits() {
        System.out.println("\n=== LISTE DES PRODUITS ===");
        System.out.printf("%-4s %-20s %-8s %-8s %-15s\n", "ID", "Nom", "Prix", "Stock", "Catégorie");
        for (Produit p : produits) {
            System.out.printf("%-4d %-20s %-8.2f %-8d %-15s\n",
                    p.getId(), p.getNom(), p.getPrix(), p.getStock(), p.getCategorie().getNom());
        }
    }

    private static Produit trouverProduitParId(int id) {
        for (Produit p : produits) {
            if (p.getId() == id) return p;
        }
        System.out.println("Aucun produit trouvé avec l'ID " + id);
        return null;
    }

    // ============ GESTION COMMANDES ============

    private static void creerCommande() {
        System.out.println("\n=== NOUVELLE COMMANDE ===");

        // Choix de la table
        Table table = choisirTable();
        if (table == null) return;

        Commande commande = new Commande(nextCommandeId++, table, utilisateurCourant);
        boolean continuer = true;

        while (continuer) {
            afficherProduits();
            int idProduit = saisirEntier("\nID produit à ajouter (0 pour terminer) : ");

            if (idProduit == 0) {
                continuer = false;
            } else {
                Produit produit = trouverProduitParId(idProduit);
                if (produit != null) {
                    int quantite = saisirEntier("Quantité : ");

                    if (quantite <= 0) {
                        System.out.println("Erreur : La quantité doit être positive.");
                    } else if (quantite > produit.getStock()) {
                        System.out.println("Erreur : Stock insuffisant. Disponible : " + produit.getStock());
                    } else {
                        commande.ajouterLigne(produit, quantite);
                        produit.setStock(produit.getStock() - quantite);
                        System.out.printf("Ajout réussi : %d x %s\n", quantite, produit.getNom());
                    }
                }
            }
        }

        if (!commande.getLignes().isEmpty()) {
            commandes.add(commande);
            table.changerEtat("occupée");
            System.out.println("\nCommande #" + commande.getId() + " créée avec succès.");
            System.out.println("Total : " + commande.calculerTotal() + " DT");
        } else {
            System.out.println("Aucun produit ajouté. Commande annulée.");
        }
    }

    private static Table choisirTable() {
        System.out.println("\n=== TABLES DISPONIBLES ===");
        System.out.printf("%-6s %-10s\n", "Numéro", "État");
        for (Table t : tables) {
            System.out.printf("%-6d %-10s\n", t.getNumero(), t.getEtat());
        }

        int numero = saisirEntier("\nNuméro de table : ");
        for (Table t : tables) {
            if (t.getNumero() == numero) {
                if (!t.getEtat().equals("libre")) {
                    System.out.println("Erreur : La table " + numero + " est " + t.getEtat());
                    return null;
                }
                return t;
            }
        }

        System.out.println("Erreur : Table " + numero + " introuvable.");
        return null;
    }

    private static void afficherCommandes() {
        System.out.println("\n=== LISTE DES COMMANDES ===");
        if (commandes.isEmpty()) {
            System.out.println("Aucune commande enregistrée.");
            return;
        }

        System.out.printf("%-10s %-6s %-15s %-8s %-10s %-15s\n",
                "Commande", "Table", "Serveur", "Articles", "Total", "Statut");

        for (Commande c : commandes) {
            System.out.printf("%-10d %-6d %-15s %-8d %-10.2f %-15s\n",
                    c.getId(),
                    c.getTable().getNumero(),
                    c.getServeur().getNom(),
                    c.getLignes().size(),
                    c.calculerTotal(),
                    c.getStatut());
        }
    }

    private static Commande trouverCommandeParId(int id) {
        for (Commande c : commandes) {
            if (c.getId() == id) return c;
        }
        System.out.println("Erreur : Aucune commande trouvée avec l'ID " + id);
        return null;
    }

    private static void payerCommande() {
        afficherCommandes();
        if (commandes.isEmpty()) return;

        int idCommande = saisirEntier("\nID de la commande à payer : ");
        Commande commande = trouverCommandeParId(idCommande);
        if (commande == null) return;

        if (commande.getStatut().equals("payée")) {
            System.out.println("Erreur : Cette commande est déjà payée.");
            return;
        }

        // Affichage détaillé
        System.out.println("\n=== DÉTAILS DE LA COMMANDE #" + commande.getId() + " ===");
        System.out.println("Table : " + commande.getTable().getNumero());
        System.out.println("Serveur : " + commande.getServeur().getNom());
        System.out.println("\nArticles :");
        System.out.printf("%-20s %-6s %-10s %-10s\n", "Produit", "Prix", "Quantité", "Sous-total");

        for (LigneCommande lc : commande.getLignes()) {
            System.out.printf("%-20s %-6.2f %-10d %-10.2f\n",
                    lc.getProduit().getNom(),
                    lc.getProduit().getPrix(),
                    lc.getQuantite(),
                    lc.calculerSousTotal());
        }

        double total = commande.calculerTotal();
        System.out.println("\nTotal à payer : " + total + " DT");

        double montantPaye = saisirDouble("Montant payé : ");
        if (montantPaye < total) {
            System.out.println("Erreur : Montant insuffisant. Il manque " + (total - montantPaye) + " DT.");
            return;
        }

        commande.setStatut("payée");
        commande.getTable().changerEtat("libre");
        System.out.println("Paiement accepté. Merci !");

        if (montantPaye > total) {
            System.out.println("Monnaie à rendre : " + (montantPaye - total) + " DT");
        }
    }

    // ============ GESTION UTILISATEURS ============

    private static void gererUtilisateurs() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n=== GESTION DES UTILISATEURS ===");
            System.out.println("1. Lister les utilisateurs");
            System.out.println("2. Ajouter un utilisateur");
            System.out.println("3. Modifier un utilisateur");
            System.out.println("4. Changer mot de passe");
            System.out.println("0. Retour");
            System.out.print("Votre choix : ");

            String choix = scanner.nextLine().trim();
            switch (choix) {
                case "1":
                    afficherUtilisateurs();
                    break;
                case "2":
                    ajouterUtilisateur();
                    break;
                case "3":
                    modifierUtilisateur();
                    break;
                case "4":
                    changerMotDePasse();
                    break;
                case "0":
                    retour = true;
                    break;
                default:
                    System.out.println("Erreur : Choix invalide.");
            }
        }
    }

    private static void afficherUtilisateurs() {
        List<Employe> employes = gestionUtilisateurs.getEmployes();
        if (employes.isEmpty()) {
            System.out.println("Aucun utilisateur enregistré.");
            return;
        }

        System.out.println("\n=== LISTE DES UTILISATEURS ===");
        System.out.printf("%-4s %-20s %-15s %-10s\n", "ID", "Nom", "Rôle", "Statut");
        for (Employe e : employes) {
            System.out.printf("%-4d %-20s %-15s %-10s\n",
                    e.getId(), e.getNom(), e.getRole(), e.isActif() ? "Actif" : "Inactif");
        }
    }

    private static void ajouterUtilisateur() {
        System.out.println("\n=== AJOUT D'UN UTILISATEUR ===");

        System.out.print("Nom complet : ");
        String nom = scanner.nextLine().trim();
        if (nom.isEmpty()) {
            System.out.println("Erreur : Le nom ne peut pas être vide.");
            return;
        }

        System.out.print("Rôle (serveur/gérant/cuisinier) : ");
        String role = scanner.nextLine().trim().toLowerCase();
        if (!Arrays.asList("serveur", "gérant", "cuisinier").contains(role)) {
            System.out.println("Erreur : Rôle invalide.");
            return;
        }

        System.out.print("Mot de passe (min 4 caractères) : ");
        String mdp = scanner.nextLine().trim();
        if (mdp.length() < 4) {
            System.out.println("Erreur : Le mot de passe doit contenir au moins 4 caractères.");
            return;
        }

        gestionUtilisateurs.ajouterEmploye(nom, role, mdp);
        System.out.println("Utilisateur ajouté avec succès !");
    }

    private static void modifierUtilisateur() {
        afficherUtilisateurs();
        List<Employe> employes = gestionUtilisateurs.getEmployes();
        if (employes.isEmpty()) return;

        int id = saisirEntier("\nID de l'utilisateur à modifier : ");

        System.out.print("Nouveau nom (laisser vide pour ne pas modifier) : ");
        String nouveauNom = scanner.nextLine().trim();

        System.out.print("Nouveau rôle (laisser vide pour ne pas modifier) : ");
        String nouveauRole = scanner.nextLine().trim().toLowerCase();
        if (!nouveauRole.isEmpty() && !Arrays.asList("serveur", "gérant", "cuisinier").contains(nouveauRole)) {
            System.out.println("Erreur : Rôle invalide.");
            return;
        }

        Boolean actif = null;
        if (saisirOuiNon("Modifier le statut ?")) {
            actif = saisirOuiNon("Activer le compte ?");
        }

        try {
            gestionUtilisateurs.modifierEmploye(id,
                    nouveauNom.isEmpty() ? null : nouveauNom,
                    nouveauRole.isEmpty() ? null : nouveauRole,
                    actif);
            System.out.println("Utilisateur modifié avec succès !");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private static void changerMotDePasse() {
        afficherUtilisateurs();
        List<Employe> employes = gestionUtilisateurs.getEmployes();
        if (employes.isEmpty()) return;

        int id = saisirEntier("\nID de l'utilisateur : ");
        System.out.print("Nouveau mot de passe (min 4 caractères) : ");
        String nouveauMdp = scanner.nextLine().trim();

        if (nouveauMdp.length() < 4) {
            System.out.println("Erreur : Le mot de passe doit contenir au moins 4 caractères.");
            return;
        }

        try {
            gestionUtilisateurs.changerMotDePasse(id, nouveauMdp);
            System.out.println("Mot de passe changé avec succès !");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}