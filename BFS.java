import java.util.*;
import java.util.stream.IntStream;

public class BFS {

    public void bfs(List<Objet> objets, List<SacADos> sacs) {
        Queue<List<SacADos>> queue = new LinkedList<>();
        List<List<SacADos>> ferme = new ArrayList();
        queue.add(sacs); // État initial : aucun objet placé dans les sacs i.e les sac sont vides

        while (!queue.isEmpty()) {
            List<SacADos> etat = queue.poll();
            ferme.add(etat);
            // Vérifier si l'état actuel est une solution
            if (estSolution(etat, objets)) {
               
                afficherSolution(etat); 
                return;
                
            }
            // Générer les états suivants et les ajouter à la file
            List<List<SacADos>> succ = successuers(etat, objets);
            for (List<SacADos> s : succ) {
                if (!containsState(ferme, s)) {
                    queue.add(s);
                } 
                
            }
        }
        System.out.println("pas de solution");
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
        boolean ajoute = false;

        for (Objet objet : objets) {
            // ne placer l'objet de l'un des sac des etats successeurs que si l'etat actuel
            // ne contient pas deja cet objet
            if (!contenu_etat(etat, objet)) {
                // essayer de placer l'objet dans les differents sac , si l'objet peut etre
                // placer dans un sac donné alors un nouvel etat sera creer
                for (SacADos sac : etat) {
                    if (sac.peutAjouter(objet)) { // verification de la capacite du sac
                        ajoute = true;
                        // creation d'un nouvel etat et recopier le contenu de l'etat actuel dans ce
                        // dernier , la creation se fait manuellment car autrement on aura qu'une
                        // reference vers l'eat actuel et non pas un nouvel etat qui a les memes sac
                        // contenant les meme objets que l'etat actuel
                        List<SacADos> nouvelEtat = new ArrayList<>();
                        for (SacADos s : etat) {
                            // Créer une copie de chaque sac et ajouter cette copie à nouvelEtat
                            SacADos nouveauSac = new SacADos(s.capaciteMax, s.id);

                            for (Objet o : s.objets) {
                                nouveauSac.objets.add(o);
                            }
                            nouvelEtat.add(nouveauSac);
                        }
                        // trouver l'indice du sac ou on va placer notre objet
                        int index = indice(nouvelEtat, sac);
                        // mettre a jour le sac en lui ajoutant l'objet
                        nouvelEtat.get(index).ajouterObjet(objet);
                        enfants.add(nouvelEtat);
                    }
                    if (ajoute) {
                        break; // quitter la boucle afin d'eviter de placer l'objet dans deux sacs d'un meme
                               // etat
                    }
                }

            }

        }
        return enfants;

    }

    

    public boolean containsState(List<List<SacADos>> etatsVisistes, List<SacADos> etat) {

        for (List<SacADos> e : etatsVisistes) {
           
            if (identiques(e, etat)) {
                return true;
            }
        }
        return false;
    }

    public boolean identiques(List<SacADos> l1, List<SacADos> l2) {

        // Créer des copies des listes pour ne pas les modifier
        List<SacADos> copyL1 = new ArrayList<>(l1);
        List<SacADos> copyL2 = new ArrayList<>(l2);

        // Trier les sacs par identifiant pour une comparaison ordonnée
        Collections.sort(copyL1, Comparator.comparingInt(s -> s.id));
        Collections.sort(copyL2, Comparator.comparingInt(s -> s.id));

        // Comparer les sacs un par un
        for (int i = 0; i < copyL1.size(); i++) {
            SacADos sac1 = copyL1.get(i);
            SacADos sac2 = copyL2.get(i);

            // Comparer les objets dans chaque sac
            List<Objet> objets1 = new ArrayList<>(sac1.objets);
            List<Objet> objets2 = new ArrayList<>(sac2.objets);

            boolean areEqual = objets1.size() == objets2.size() &&
                    IntStream.range(0, objets1.size())
                            .allMatch(index -> objets1.get(index).equals(objets2.get(index)));
           // System.out.println("eqaul:" + areEqual);

            if (!areEqual) {
                return false;
            }
        }
        return true;
    }

    private int indice(List<SacADos> nouvelEtat, SacADos sac) {
        int index = -1; // Initialiser l'index à une valeur par défaut

        for (int i = 0; i < nouvelEtat.size(); i++) {

            if (nouvelEtat.get(i).id == sac.id) { // Remplacez idRecherche par l'ID que vous recherchez
                index = i; // Affecter l'index si l'ID est trouvé
                break; // Sortir de la boucle une fois que l'ID est trouvé
            }
        }
        return index;
    }
    
    // pour verifier si un objet donné est deja placé dans l'un des sacs d'un etat
    private boolean contenu_etat(List<SacADos> etat, Objet objet) {
        boolean trouve = false;
        for (SacADos sac : etat) {
            if (sac.contient(objet)) {
               // System.out.println("le sec est deja dans l'etat");
                trouve = true;
                break;
            }
        }

        return trouve;
    }

}

