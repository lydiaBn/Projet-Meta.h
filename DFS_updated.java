import java.util.*;

public class DFS_updated {

    public void dfs(List<Objet> objets, List<SacADos> sacs, List<SacADos> etatActuel) {
        if (estSolution(etatActuel, objets)) {
            System.out.println("hi 00 :");
            afficherSolution(etatActuel);
            return;
        }
        // calculer les suuccessurs de l'etat 
        List<List<SacADos>> succ = successuers(etatActuel, objets);
        // essayer de trouver une solution à partir de chaque enfant jusqu'a ce la solution est trouver 
        // si un chemin ne mene pas vers une solution alors il fait un retour arriere => cela est implementer grace a la recursivite
        for (List<SacADos> e : succ) {
            dfs(objets, sacs, e);
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

    }
    // for test purposes 
    // Afficher etat produts et ajouté aux successurs 
    private void afficherDEtat(List<SacADos> etat) {
        System.out.println("Etat crée et ajouté :");
        for (SacADos sac : etat) {
            System.out.println("Sac à dos : Capacité = " + sac.capaciteMax);
            for (Objet objet : sac.objets) {
                System.out.println("   Objet : Poids = " + objet.poids + ", Valeur = " + objet.valeur);
            }
        }

    }
    private List<List<SacADos>> successuers(List<SacADos> etat, List<Objet> objets) {
        List<List<SacADos>> enfants = new ArrayList<>();
        boolean ajoute=false;

        for (Objet objet : objets) {
            // ne placer l'objet de l'un des sac des etats successeurs que si l'etat acutuel ne contient pas deja cet objet 
            if (!contenu_etat(etat, objet)) {
                // essayer de placer l'objet dans les differents sac , si l'objet peut etre placer dans un sac donné alors un nouvel etat sera creer
                for (SacADos sac : etat) {
                    if (sac.peutAjouter(objet)) { // verification de la capacite du sac
                        ajoute=true; 
                        // creation d'un nouvel etat et recopier le contenu de l'etat actuel dans ce dernier
                        List<SacADos> nouvelEtat = new ArrayList<>();
                        for (SacADos s : etat) {
                            nouvelEtat.add(s);
                        }
                        // trouver l'indice du sac ou on va placer notre objet
                        int index = nouvelEtat.indexOf(sac);
                        // mettre a jour le sac en lui ajoutant l'objet

                        nouvelEtat.get(index).ajouterObjet(objet);

                        // afficher l'etat
                        afficherDEtat(nouvelEtat);
                        //ajouter l'etat au successeurs
                        enfants.add(nouvelEtat);
                    }
                    if(ajoute){
                        break; //  quitter la boucle afin d'eviter de placer l'objet dans deux sacs d'un meme etat 
                    }
                }

            }

        }
        return enfants;

    }
    
    // pour verifier si un objet donné est deja placé dans l'un des sacs d'un etat
    private boolean contenu_etat(List<SacADos> etat, Objet objet) {
        boolean trouve = false;
        for (SacADos sac : etat) {
            if (sac.contient(objet)) {
                System.out.println("le sec est deja dans l'etat");
                trouve = true;
                break;
            }
        }

        return trouve;
    }
}
