package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.FoxRaidState;
import modele.interaction.Interaction;

/**
 * Classe représentant un renard prédateur dans le jeu.
 */
public class Fox extends Predator {
    /**
     * Constructeur de la classe Fox.
     * Initialise la position du renard, son état initial, sa représentation visuelle et sa liste d'interactions.
     *
     * @param x La coordonnée X du renard.
     * @param y La coordonnée Y du renard.
     */
    public Fox(int x, int y) {
        super(x, y);
        this.currentState = new FoxRaidState(this);
        this.representation = "R";
        this.displayName = "Renard";
        this.interactionList = new Interaction[0];
    }

    /**
     * Méthode appelée après qu'un renard ait été attaqué.
     *
     * @param killed Indique si le renard a été tué.
     */
    @Override
    public void afterHit(boolean killed) {
    }
}