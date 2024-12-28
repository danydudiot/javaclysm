package modele.entity.stationary;

import modele.entity.Entity;

/**
 * Classe abstraite représentant tous les types d'entité qui ne peuvent pas bouger par elle-même.
 */
public abstract class StaticEntity extends Entity {
    /**
     * Constructeur abstrait.
     *
     * @param x La position vertical.
     * @param y La position horizontal.
     */
    public StaticEntity(int x, int y) {
        super(x, y);
    }
}
