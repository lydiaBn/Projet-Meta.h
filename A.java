import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class A {

    public void a(List<Objet> objets, List<SacADos> sacs) {

        List<Etat> ouvert = new ArrayList<>();
        List<List<SacADos>> ferme = new ArrayList();

        // creer l'etat initial
        Etat e = new Etat(sacs, 0);
        e.setF(objets.size()); // equivalent a faire e.setF(e.getG() + h(e, objets)); car e.getG() =0 vu
                               // qu'acune opération s'est effecutée pour le moment et h(e, objets) qui est
                               // cencée retournée le nombre d'objets qui ne sont pas encore placés dans un
                               // etat va retourner le nombre de tout les objets car nous avons rien placer
                               // pour l'instant

        ouvert.add(e);

        while (!ouvert.isEmpty()) {
            Etat etat = ouvert.get(0);
            ouvert.remove(0);
            ferme.add(etat.sacs);
            // Vérifier si l'état actuel est une solution
            if (estSolution(etat.getSacs(), objets)) {
                afficherSolution(etat);
                return;

            }
            // Générer les états suivants et les ajouter à la file
            List<Etat> successuers = successuers(etat, objets);
            for (Etat successuer : successuers) {
                if (!containsState(ferme, successuer.sacs)) {
                    ouvert.add(successuer);
                    //afficherDEtat(successuer);
                    // trier la liste ouvert selon la valeur de f
                    Collections.sort(ouvert, new Comparator<Etat>() {
                        @Override
                        public int compare(Etat e1, Etat e2) {
                            return Integer.compare(e1.f, e2.f);
                        }
                    });

                }

            }
        }
        System.out.println("pas de solutions");
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

        return true;
    }

    // Affiche la solution
    private void afficherSolution(Etat etat) {
        System.out.println(
                "Solution trouvée avec cout egale à " + etat.getG() + " et valeur de f egale à " + etat.getF());
        for (SacADos sac : etat.getSacs()) {
            System.out.println("Sac à dos : Capacité = " + sac.capaciteMax);
            for (Objet objet : sac.objets) {
                System.out.println("   Objet : Poids = " + objet.poids + ", Valeur = " + objet.valeur);
            }
        }

    }

    // for test purposes
    // Afficher etat produts et ajouté aux successurs
    private void afficherDEtat(Etat etat) {
        System.out.println("Etat crée et ajouté à ouvert avec f=" + etat.getF());
        for (SacADos sac : etat.getSacs()) {
            System.out.println("Sac à dos : Capacité = " + sac.capaciteMax);
            for (Objet objet : sac.objets) {
                System.out.println("   Objet : Poids = " + objet.poids + ", Valeur = " + objet.valeur);
            }
        }

    }

    private List<Etat> successuers(Etat etat, List<Objet> objets) {
        List<Etat> successuers = new ArrayList<>();
        boolean ajoute = false;

        for (Objet objet : objets) {
            // ne placer l'objet de l'un des sac des etats successeurs que si l'etat acutuel
            // ne contient pas deja cet objet
            if (!contenu_etat(etat.getSacs(), objet)) {
                // essayer de placer l'objet dans les differents sac , si l'objet peut etre
                // placer dans un sac donné alors un nouvel etat sera creer
                for (SacADos sac : etat.getSacs()) {
                    if (sac.peutAjouter(objet)) { // verification de la capacite du sac
                        ajoute = true;
                        // creation d'un nouvel etat et recopier le contenu de l'etat actuel dans ce
                        // dernier
                        List<SacADos> nouvelEtatSacs = new ArrayList<>();
                       
                        for (SacADos s : etat.getSacs()) {
                            // Créer une copie de chaque sac et ajouter cette copie à nouvelEtat
                            SacADos nouveauSac = new SacADos(s.capaciteMax, s.id);

                            for (Objet o : s.objets) {
                                nouveauSac.objets.add(o);
                            }
                            nouvelEtatSacs.add(nouveauSac);
                        }
                       
                        Etat nouvelEtat = new Etat(nouvelEtatSacs, etat.getG() + 1);
                       

                        // trouver l'indice du sac ou on va placer notre objet
                        int index = indice(nouvelEtat.sacs, sac);

                        // mettre a jour le sac en lui ajoutant l'objet
                        nouvelEtat.getSacs().get(index).ajouterObjet(objet);

                        // calculer la valeur f de l'etat
                        nouvelEtat.setF(nouvelEtat.getG() + h(nouvelEtat, objets));

                        // afficher l'etat
                        //afficherDEtat(nouvelEtat);
                        // ajouter l'etat au successeurs
                        successuers.add(nouvelEtat);
                    }
                    if (ajoute) {
                        break; // quitter la boucle afin d'eviter de placer l'objet dans deux sacs d'un meme
                               // etat
                    }
                }

            }

        }
        return successuers;

    }

    // pour verifier si un objet donné est deja placé dans l'un des sacs d'un etat
    private boolean contenu_etat(List<SacADos> sacs, Objet objet) {
        boolean trouve = false;
        for (SacADos sac : sacs) {
            for (Objet o : sac.objets) {
                if (o.id==objet.id) {
                    // System.out.println("le sec est deja dans l'etat");
                    trouve = true;
                    break;
                }
            }
            
        }

        return trouve;
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

    // l'heuristique : le nombre d'objets non encore placé dans des sacs
    private int h(Etat e, List<Objet> objets) {
        int nb_sacs_non_places = 0;
        for (Objet objet : objets) {
            if (!contenu_etat(e.getSacs(), objet)) {

                nb_sacs_non_places += 1;
            }
        }

        return nb_sacs_non_places;
    }
}