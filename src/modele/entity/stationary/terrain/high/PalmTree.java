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
        this.representation = Colors.ANSI_CYAN + "P" + Colors.ANSI_RESET;
        this.displayName = "Cocotier";
    }

    @Override
    public String toString() {
        if (entityOnCase == null) {
            return representation;
        } else {
            return Colors.ANSI_CYAN_BACKGROUND + Colors.ANSI_BLACK + entityOnCase.toString() + Colors.ANSI_RESET;
        }
    }
}
