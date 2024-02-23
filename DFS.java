import java.util.*;
import java.util.stream.IntStream;

public class DFS {

    public void dfs(List<Objet> objets, List<SacADos> sacs) {
        Stack<List<SacADos>> ouvert = new Stack<>();
        List<List<SacADos>> ferme = new ArrayList();

        ouvert.push(sacs); // acr les sacs vides representent l'etat initial

        while (!ouvert.isEmpty()) {
            List<SacADos> etatActuel = ouvert.pop();
            //System.out.println("etat actuel");
            //afficherDEtat(etatActuel);

            if (estSolution(etatActuel, objets)) {
                // System.out.println("hi 00 :");
                afficherSolution(etatActuel);
                return;
            }
            // calculer les successeurs de l'état
            List<List<SacADos>> succ = successuers(etatActuel, objets);

            for (List<SacADos> e : succ) {
                if (!containsState(ferme, e)) {
                    //System.out.println("etat ajouté à ouvert");
                    //afficherDEtat(e);
                    ouvert.push(e);
                }
            }
            ferme.add(etatActuel);
        }
        System.out.println("pas de solution");

    }

    // Vérifie si l'état actuel est une solution
    private boolean estSolution(List<SacADos> etat, List<Objet> objets) {
        // Vérifie si tous les objets ont été placés dans les sacs à dos

        for (Objet objet : objets) {
            boolean estPlace = false;
            for (SacADos sac : etat) {
                for (Objet o : sac.objets) {
                    if (o.id == objet.id) {
                        estPlace = true;
                        break;
                    }
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
            System.out.println("Sac à dos " + sac.id + ": Capacité = " + sac.capaciteMax);
            for (Objet objet : sac.objets) {
                System.out.println("   Objet :" + objet.id + " Poids = " + objet.poids + ", Valeur = " + objet.valeur);
            }
        }

    }

    // for test purposes
    // Afficher etat produts et ajouté aux successurs
    private void afficherDEtat(List<SacADos> etat) {
        // System.out.println("Etat crée et ajouté :");
        for (SacADos sac : etat) {
            System.out.println("Sac à dos " + sac.id + " capacite=" + sac.capaciteMax);
            for (Objet objet : sac.objets) {
                System.out.println("   Objet " + objet.id + ": Poids = " + objet.poids + ", Valeur = " + objet.valeur);
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
                                Objet nouveauObjet = new Objet(o.poids, o.valeur, o.id);
                                nouveauSac.objets.add(nouveauObjet);
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

    // pour verifier si un objet donné est deja placé dans l'un des sacs d'un etat
    private boolean contenu_etat(List<SacADos> etat, Objet objet) {
        boolean trouve = false;
        for (SacADos sac : etat) {// on parcours les sacs de l'etat
            for (Objet o : sac.objets) { // on parcours les objets de chaque sac
                if (o.id == objet.id) {// si on trouve un objet avec le meme id alors on retouren vrai
                    trouve = true;
                    break;
                }
            }
        }

        return trouve;
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

        // on compare sac par sac
        for (int i = 0; i < l1.size(); i++) { // forcement les deux listes vont contenir le meme ordre de sacs car
                                              // l'etat initial contient la liste des sacs vides qui a ete crée dans le
                                              // main et on place des objets dans le sac sans changer leur ordre
            // trier les objets selon les id pour pouvoir les comparer
            Collections.sort(l1.get(i).objets, new Comparator<Objet>() {
                @Override
                public int compare(Objet o1, Objet o2) {
                    return Integer.compare(o1.id, o2.id);
                }
            });
            Collections.sort(l2.get(i).objets, new Comparator<Objet>() {
                @Override
                public int compare(Objet o1, Objet o2) {
                    return Integer.compare(o1.id, o2.id);
                }
            });
            if (l1.get(i).objets.size() != l2.get(i).objets.size()) {
                return false;
            } else {
                for (int j = 0; j < l1.get(i).objets.size(); j++) {
                    if (l1.get(i).objets.get(j).id != l2.get(i).objets.get(j).id) {
                        return false;

                    }
                }

            }

        }

        return true;
    }

    private int indice(List<SacADos> nouvelEtat, SacADos sac) {
        int index = -1; // Initialiser l'index à une valeur par défaut

        for (int i = 0; i < nouvelEtat.size(); i++) {

            if (nouvelEtat.get(i).id == sac.id) {
                index = i; // Affecter l'index si l'ID est trouvé
                break; // Sortir de la boucle une fois que l'ID est trouvé
            }
        }
        return index;
    }

}
