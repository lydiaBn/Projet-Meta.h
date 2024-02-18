import java.util.*;

public class BFS {

    public void bfs(List<Objet> objets, List<SacADos> sacs) {
        Queue<List<SacADos>> queue = new LinkedList<>();
        queue.add(new ArrayList<>()); // État initial : aucun objet placé dans les sacs

        while (!queue.isEmpty()) {
            List<SacADos> etat = queue.poll();
            // Vérifier si l'état actuel est une solution
            if (estSolution(etat, objets)) {
                afficherSolution(etat); // Action à effectuer lorsque nous avons trouvé une solution
                continue; // Passer à l'état suivant
            }
            // Générer les états suivants en ajoutant ou retirant un objet de chaque sac
            for (Objet objet : objets) {
                for (SacADos sac : sacs) {
                    if (!sac.contient(objet) && sac.peutAjouter(objet)) {
                        List<SacADos> nouvelEtat = new ArrayList<>(etat);
                        nouvelEtat.add(sac.ajouterObjet(objet));
                        queue.add(nouvelEtat);
                    }
                }
            }
        }
    }

    // Vérifie si l'état actuel est une solution
    private boolean estSolution(List<SacADos> etat, List<Objet> objets) {
        // Vérifie si tous les objets ont été placés dans les sacs à dos
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

