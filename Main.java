
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // generation automatique
        // Déclaration du nombre d'objets et de sacs à dos
        int nbObjets = 10; // Nombre d'objets quelconque
        int nbSacs = 3; // Nombre de sacs quelconque

        // Génération aléatoire des poids et des capacités
        Random random = new Random();
        List<Objet> objets = new ArrayList<>();
        List<SacADos> sacs = new ArrayList<>();

        int sommePoids = 0;
        int sommeCapacites = 0;

        // Génération aléatoire des objets
        for (int i = 0; i < nbObjets; i++) {
            int poids = random.nextInt(10) + 1; // Poids aléatoire entre 1 et 10
            int valeur = random.nextInt(20) + 1; // Valeur aléatoire entre 1 et 20
            sommePoids += poids;
            objets.add(new Objet(poids, valeur,i));
        }

        // Génération aléatoire des sacs à dos
        for (int i = 0; i < nbSacs; i++) {
            int capacite = random.nextInt(11) + 10; // Capacité aléatoire légèrement supérieure à la somme des poids
            sommeCapacites += capacite;
            sacs.add(new SacADos(capacite,i));
        }
        // Assurer que la somme des capacités des sacs est supérieure ou égale à la
        // somme des poids des objets
        while (sommeCapacites < sommePoids) {
            int index = random.nextInt(sacs.size()); // Sélectionner un sac aléatoire
            SacADos sac = sacs.get(index);
            sac.capaciteMax += random.nextInt(10) + 1; // Augmenter sa capacité aléatoirement
            sommeCapacites += random.nextInt(10) + 1; // l'ajout d'une capacité supplémentaire modifie la somme des
                                                      // capacités
        }

        // Affichage des objets et des sacs à dos générés
        System.out.println("Objets générés : ");
        for (Objet objet : objets) {
            System.out.println("Poids : " + objet.poids + ", Valeur : " + objet.valeur);
        }

        System.out.println("\nSacs à dos générés : ");
        for (SacADos sac : sacs) {
            System.out.println("Capacité : " + sac.capaciteMax);
        }
        int x = sommeCapacites - sommePoids;
        System.out.println("Capacité -poids: " + x);
        
        System.out.println("dfs");
        DFS dfs = new DFS();
  
        dfs.dfs(objets, sacs);
        
        System.out.println("A*");
        A a = new A();
       
        a.a(objets, sacs);

          // Exemple d'utilisation de l'algorithme DFS
          System.out.println("bfs");
          BFS bfs = new BFS();
  
          bfs.bfs(objets, sacs);
  
       
    }
}
