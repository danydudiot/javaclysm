package modele.entity.movable.character.npc.predator;

import modele.Board;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.predator.RestState;

/**
 * Classe abstraite représentant un prédateur dans le jeu.
 */
public abstract class Predator extends NonPlayerCharacter {
    /**
     * Le niveau de fatigue.
     */
    protected int restLevel;

    /**
     * Constructeur de la classe Predator.
     * Initialise la position du prédateur.
     *
     * @param x La coordonnée X du prédateur.
     * @param y La coordonnée Y du prédateur.
     */
    public Predator(int x, int y) {
        super(x, y);
    }

    /**
     * Gère l'attaque d'un personnage joueur sur le prédateur.
     * Si le prédateur est en état de repos, il est tué et son état est mis à jour.
     *
     * @param aggressor Le personnage joueur attaquant.
     * @return true si l'attaque est réussie et le prédateur est tué, sinon false.
     */
    @Override
    public boolean hit(Character aggressor) {
        if (currentState instanceof RestState) {
            Board.getInstance().clearCase(x, y);
            setCurrentState(new DeadState(this));
            return true;
        }
        return false;
    }

    /**
     * Méthode abstraite appelée après qu'un prédateur ait été attaqué.
     *
     * @param killed Indique si le prédateur a été tué.
     */
    public abstract void afterHit(boolean killed);

    /**
     * Obtient le niveau de fatigue du prédateur.
     *
     * @return Le niveau de fatigue du prédateur.
     */
    public int getRestLevel() {
        return restLevel;
    }

    /**
     * Définit le niveau de fatigue du prédateur.
     *
     * @param restLevel Le nouveau niveau de fatigue du prédateur.
     */
    public void setRestLevel(int restLevel) {
        this.restLevel = restLevel;
    }
}