import java.util.*; // Pour List et ArrayList
public class SacADos {
    int capaciteMax;
    List<Objet> objets;

    public SacADos(int capaciteMax) {
        this.capaciteMax = capaciteMax;
        this.objets = new ArrayList<>();
    }

    public void ajouterObjet(Objet objet) {
        objets.add(objet);
    }
}
