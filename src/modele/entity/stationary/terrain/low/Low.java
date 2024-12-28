package modele.entity.stationary.terrain.low;

import modele.Colors;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe abstraite représentant un terrain bas sur la carte.
 */
public abstract class Low extends Terrain {

    /**
     * Constructeur de la classe Low.
     *
     * @param x La position verticale du terrain.
     * @param y La position horizontale du terrain.
     */
    public Low(int x, int y) {
        super(x, y);
    }
}
