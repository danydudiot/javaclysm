package modele.entity.stationary.terrain.high;

import modele.Colors;

/**
 * Classe représentant un cocotier sur la carte.
 */
public class PalmTree extends High {

    /**
     * Constructeur qui défini la représentation "P" et le displayName "Cocotier
     *
     * @param x La position verticale du rocher.
     * @param y La position horizontale du rocher.
     */
    public PalmTree(int x, int y) {
        super(x, y);
        this.representation = Colors.CYAN + "P" + Colors.RESET;
        this.displayName = "Cocotier";
        this.highlightColor = Colors.CYAN_BACKGROUND + Colors.BLACK;
    }
}
