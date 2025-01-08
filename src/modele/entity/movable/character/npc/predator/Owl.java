package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.OwlRaidState;
import modele.entity.movable.character.npc.state.predator.OwlRestState;
import modele.entity.movable.character.npc.state.predator.RaidState;
import modele.interaction.Interaction;

/**
 * Classe représentant un hibou prédateur dans le jeu.
 */
public class Owl extends Predator {
    /**
     * Constructeur de la classe Owl.
     * Initialise la position du hibou, son état initial, sa représentation visuelle et son nom d'affichage.
     *
     * @param x La coordonnée X du hibou.
     * @param y La coordonnée Y du hibou.
     */
    public Owl(int x, int y) {
        super(x, y);
        this.currentState = new OwlRaidState(this);
        this.representation = "H";
        this.displayName = "Hibou";
    }

    /**
     * Méthode appelée après qu'un hibou ait été attaqué.
     * Change l'état du hibou à l'état de repos.
     *
     * @param killed Indique si le hibou a été tué.
     */
    @Override
    public void afterHit(boolean killed) {
        setCurrentState(new OwlRestState(this));
    }

    /**
     * Obtient la liste des interactions possibles pour le hibou.
     *
     * @return Un tableau d'interactions si le hibou n'est pas en état de raid, sinon un tableau vide.
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