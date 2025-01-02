package modele.entity.stationary.food;

import modele.Board;
import modele.InventoryItem;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.stationary.StaticEntity;
import modele.entity.stationary.terrain.Terrain;
import modele.interaction.Grab;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

import java.util.List;
import java.util.Map;


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

    public boolean isPlayerNearby() {
        List<Terrain> neighbours = Board.getInstance().getNeighbours(x,y);
		for (Terrain terrain : neighbours) {
			if (terrain.getEntityOnCase() instanceof PlayerCharacter) {
				return true;
			}
		}
        return false;
    }

    /**
     * Retourne la liste des interactions possibles avec la pierre précieuse.
     * @return Un tableau d'interactions.
     */
    public Interaction[] getInteractions(){
        return interactionList;
    }

}
