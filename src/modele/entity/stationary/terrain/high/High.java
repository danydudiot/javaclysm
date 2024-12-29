package modele.entity.stationary.terrain.high;

import modele.entity.stationary.terrain.Terrain;

/**
 * Classe abstraite représentant un terrain haut sur la carte.
 */
public abstract class High extends Terrain {

    /**
     * Constructeur de la classe High.
     *
     * @param x La position verticale du terrain.
     * @param y La position horizontale du terrain.
     */
    public High(int x, int y) {
        super(x, y);
    }
}