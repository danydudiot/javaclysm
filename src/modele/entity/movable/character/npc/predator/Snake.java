package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.RaidState;
import modele.entity.movable.character.npc.state.predator.SnakeRaidState;
import modele.entity.movable.character.npc.state.predator.SnakeRestState;
import modele.interaction.Interaction;

/**
 * Classe représentant un serpent prédateur dans le jeu.
 */
public class Snake extends Predator {

    /**
     * Constructeur de la classe Snake.
     * Initialise la position du serpent, son état initial, sa représentation visuelle et son nom d'affichage.
     *
     * @param x La coordonnée X du serpent.
     * @param y La coordonnée Y du serpent.
     */
    public Snake(int x, int y) {
        super(x, y);
        this.currentState = new SnakeRaidState(this);
        this.representation = "E";
        this.displayName = "Serpent";
    }

    /**
     * Méthode appelée après qu'un serpent ait été attaqué.
     * Change l'état du serpent à l'état de repos si le serpent a été tué.
     *
     * @param killed Indique si le serpent a été tué.
     */
    @Override
    public void afterHit(boolean killed) {
        if (killed) {
            setCurrentState(new SnakeRestState(this));
        }
    }

    /**
     * Obtient la liste des interactions possibles pour le serpent.
     *
     * @return Un tableau d'interactions si le serpent n'est pas en état de raid, sinon un tableau vide.
     */
    @Override
    public Interaction[] getInteractions() {
        if (currentState instanceof RaidState) {
            return new Interaction[0];
        } else {
            return interactionList;
        }
    }
}