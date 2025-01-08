package modele.entity.movable;

import modele.Board;
import modele.entity.Entity;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe abstraite représentant une entité mobile dans le jeu.
 */
public abstract class MovableEntity extends Entity {
    /**
     * Constructeur de la classe MovableEntity.
     * Initialise la position de l'entité mobile.
     *
     * @param x La coordonnée X de l'entité.
     * @param y La coordonnée Y de l'entité.
     */
    public MovableEntity(int x, int y) {
        super(x, y);
    }

    /**
     * Déplace l'entité mobile vers un nouveau terrain.
     *
     * @param terrain Le nouveau terrain vers lequel déplacer l'entité.
     */
    public void move(Terrain terrain) {
        if (canMove(terrain)) {
            Board.getInstance().moveTo(this, terrain.getX(), terrain.getY());
        }
    }

    /**
     * Vérifie si l'entité mobile peut se déplacer vers le terrain cible.
     *
     * @param target Le terrain cible.
     * @return true si l'entité peut se déplacer vers le terrain cible, sinon false.
     */
    public boolean canMove(Terrain target) {
        return target != null && target.isEmpty() && (target instanceof Empty);
    }

    /**
     * Obtient la direction inverse de la direction donnée.
     *
     * @param direction La direction actuelle.
     * @return La direction inverse.
     */
    public char getInverseDirection(char direction) {
        return switch (direction) {
            case 'z' -> 's';
            case 'q' -> 'd';
            case 's' -> 'z';
            case 'd' -> 'q';
            default -> direction;
        };
    }
}
