package modele.entity.stationary.food;

import modele.InventoryItem;
import modele.entity.stationary.StaticEntity;
import modele.interaction.Grab;
import modele.interaction.Interactible;
import modele.interaction.Interaction;


/**
 * Classe abstraite représentant la nourriture.
 */
public abstract class Food extends StaticEntity implements Interactible, InventoryItem {
    /** Liste des interactions possibles. */
    protected Interaction[] interactionList;

    /**
     * Constructeur abstrait qui ajoute l'interaction Grab.
     *
     * @param x La position vertical.
     * @param y La position horizontal.
     */
    public Food(int x, int y) {
        super(x, y);
        this.interactionList = new Interaction[1];
        interactionList[0] = new Grab();
    }

    /**
     * Retourne la liste des interactions possibles avec la pierre précieuse.
     * @return Un tableau d'interactions.
     */
    public Interaction[] getInteractions(){
        return interactionList;
    }

}
