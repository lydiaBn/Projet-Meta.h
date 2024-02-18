import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Création d'objets
        Objet obj1 = new Objet(5, 10);
        Objet obj2 = new Objet(3, 7);
        Objet obj3 = new Objet(8, 15);

        // Création de sacs à dos
        SacADos sac1 = new SacADos(10);
        SacADos sac2 = new SacADos(8);
        SacADos sac3 = new SacADos(12);

        // Ajout des objets dans une liste d'objets
        List<Objet> objets = new ArrayList<>();
        objets.add(obj1);
        objets.add(obj2);
        objets.add(obj3);

        // Ajout des sacs à dos dans une liste de sacs à dos
        List<SacADos> sacs = new ArrayList<>();
        sacs.add(sac1);
        sacs.add(sac2);
        sacs.add(sac3);

     /*   // Exemple d'utilisation de l'algorithme BFS
        BFS bfs = new BFS();
        bfs.bfs(objets, sacs); */

        // Exemple d'utilisation de l'algorithme DFS
        DFS dfs = new DFS();
        dfs.dfs(objets, sacs, new ArrayList<>());
    }
}
