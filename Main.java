import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        AlgoGen algo = new AlgoGen();

        int nbObjets = 10; // Nombre d'objets quelconque
        int nbSacs =3; // Nombre de sacs quelconque
        int nbGen = 25;
        int taillePop = 100;

        // Génération aléatoire des poids et des capacités
        Random random = new Random();
        Objet[] objets = new Objet[nbObjets];
        Sac[] sacs = new Sac[nbSacs];
       
       

        // Génération aléatoire des objets
        for (int i = 0; i < nbObjets; i++) {
            int poids = random.nextInt(10) + 1; // Poids aléatoire entre 1 et 10
            int valeur = random.nextInt(20) + 1; // Valeur aléatoire entre 1 et 20
            objets[i]=new Objet(poids, valeur,i);
            
        }

        // Génération aléatoire des sacs à dos
        for (int i = 0; i < nbSacs; i++) {
            int capacite = random.nextInt(11) + 10; // Capacité aléatoire légèrement supérieure à la somme des poids
            sacs[i]=new Sac(capacite,i);
        
            
        }
         // Affichage des objets et des sacs à dos générés
         System.out.println("Objets générés : ");
         for (Objet objet : objets) {
             System.out.println("Poids : " + objet.poids + ", Valeur : " + objet.valeur);
         }
 
         System.out.println("\nSacs à dos générés : ");
         for (Sac sac : sacs) {
             System.out.println("Capacité : " + sac.capaciteMax);
            
         }

         long startTime = System.nanoTime();
        int[][] solution = algo.recherche((float) 0.5, (float) 0.1, nbGen, taillePop, objets,  sacs);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // Temps d'exécution en nanosecondes

        System.out.println("\n-------------------Solution trouvée  !!---------------");
        algo.afficher(solution, sacs, objets);
        System.out.println("interprétation de la solution" );
        String output=algo.interpretationSol(solution, sacs, objets);
        System.out.println(output);
        System.out.println("\n-------------------temps d'execution  !!---------------");
        System.out.println("Temps d'exécution : " + duration + " nanosecondes");
      
       
    }
    
}


/*
 * 
 * (10,3) 243576695500
 * (100,25)
 * (500,125)
 * (1000,225)
 * (1500,375)
 * (5000,1250)
 */