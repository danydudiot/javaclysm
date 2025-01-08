package modele.entity.movable.character;

import modele.entity.movable.MovableEntity;

/**
 * Classe abstraite représentant un personnage dans le jeu.
 */
public abstract class Character extends MovableEntity {
    /**
     * Constructeur de la classe Character.
     * Initialise la position du personnage.
     *
     * @param x La coordonnée X du personnage.
     * @param y La coordonnée Y du personnage.
     */
    public Character(int x, int y) {
        super(x, y);
    }
}