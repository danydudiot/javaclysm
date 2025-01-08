package modele.entity.movable.character.npc;

import modele.clock.Observateur;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.terrain.Terrain;
import modele.interaction.Hit;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

/**
 * Classe abstraite représentant un personnage non-joueur (PNJ) dans le jeu.
 * Implémente les interfaces Interactible et Observateur.
 */
public abstract class NonPlayerCharacter extends Character implements Interactible, Observateur {
    /**
     * L'état actuel du PNJ.
     */
    protected State currentState;
    /**
     * La liste des interactions possibles pour le PNJ.
     */
    protected Interaction[] interactionList;

    /**
     * Constructeur de la classe NonPlayerCharacter.
     * Initialise la position du PNJ et sa liste d'interactions.
     *
     * @param x La coordonnée X du PNJ.
     * @param y La coordonnée Y du PNJ.
     */
    public NonPlayerCharacter(int x, int y) {
        super(x, y);
        this.interactionList = new Interaction[1];
        this.interactionList[0] = new Hit();
    }

    /**
     * Obtient la liste des interactions possibles pour le PNJ.
     *
     * @return Un tableau d'interactions.
     */
    public Interaction[] getInteractions() {
        return interactionList;
    }

    /**
     * Met à jour l'état du PNJ en appelant les méthodes de déplacement et de mise à jour de l'état.
     */
    public void mettreAJour() {
        currentState.deplacement();
        currentState.updateState();
    }

    /**
     * Vérifie si le PNJ peut se déplacer vers le terrain spécifié.
     *
     * @param terrain Le terrain cible.
     * @return true si le PNJ peut se déplacer vers le terrain cible, sinon false.
     */
    @Override
    public boolean canMove(Terrain terrain) {
        return currentState.canMove(terrain);
    }

    /**
     * Définit l'état actuel du PNJ.
     *
     * @param currentState Le nouvel état du PNJ.
     */
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    /**
     * Obtient l'état actuel du PNJ.
     *
     * @return L'état actuel du PNJ.
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du PNJ,
     * en appliquant un modificateur de couleur basé sur son état actuel.
     *
     * @return Une chaîne de caractères représentant le PNJ.
     */
    @Override
    public String toString() {
        return currentState.applyColorModifier();
    }

    /**
     * Méthode abstraite pour gérer l'attaque d'un personnage joueur sur le PNJ.
     *
     * @param aggressor Le personnage joueur attaquant.
     * @return true si l'attaque est réussie, sinon false.
     */
    public abstract boolean hit(Character aggressor);
}
