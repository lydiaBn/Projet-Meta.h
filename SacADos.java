import java.util.*;
public class SacADos {
    int capaciteMax;
    List<Objet> objets;

    public SacADos(int capaciteMax) {
        this.capaciteMax = capaciteMax;
        this.objets = new ArrayList<>();
    }

    // Vérifie si un objet est déjà dans le sac à dos
    public boolean contient(Objet objet) {
        return objets.contains(objet);
    }

    // Vérifie si un objet peut être ajouté au sac à dos sans dépasser sa capacité maximale
    public boolean peutAjouter(Objet objet) {
        int poidsTotal = objets.stream().mapToInt(o -> o.poids).sum();
        return poidsTotal + objet.poids <= capaciteMax;
    }

    // Ajoute un objet au sac à dos
    public SacADos ajouterObjet(Objet objet) {
        objets.add(objet);
        return this;
    }
}
