import java.util.*;

public class DFS {

    public void dfs(List<Objet> objets, List<SacADos> sacs, List<SacADos> etatActuel) {
        if (estSolution(etatActuel, objets)) {
            System.out.println("hi 00 :");
            afficherSolution(etatActuel); // Action à effectuer lorsque nous avons trouvé une solution
            return;
        }

        // Pour chaque objet et chaque sac, essayez d'ajouter l'objet au sac
        for (Objet objet : objets) {
            for (SacADos sac : sacs) {
                if (!sac.contient(objet) && sac.peutAjouter(objet)) {
                    System.out.println("hi 0 :");
                    List<SacADos> nouvelEtat = new ArrayList<>(etatActuel);
                    nouvelEtat.add(sac.ajouterObjet(objet));
                    dfs(objets, sacs, nouvelEtat);
                }
            }
        }
    }

    // Vérifie si l'état actuel est une solution
    private boolean estSolution(List<SacADos> etat, List<Objet> objets) {
        // Vérifie si tous les objets ont été placés dans les sacs à dos
        System.out.println("hi 1 :");
        for (Objet objet : objets) {
            boolean estPlace = false;
            for (SacADos sac : etat) {
                if (sac.contient(objet)) {
                    estPlace = true;
                    break;
                }
            }
            if (!estPlace) {
                return false;
            }
        }
        // Implémentez d'autres critères de solution spécifiques selon vos besoins
        return true;
    }

    // Affiche la solution
       private void afficherSolution(List<SacADos> etat) {
            System.out.println("Solution trouvée :");
            for (SacADos sac : etat) {
                System.out.println("Sac à dos : Capacité = " + sac.capaciteMax);
                for (Objet objet : sac.objets) {
                    System.out.println("   Objet : Poids = " + objet.poids + ", Valeur = " + objet.valeur);
                }
            }
            // Implémentez d'autres actions à effectuer lors de la découverte d'une solution
        }
}
