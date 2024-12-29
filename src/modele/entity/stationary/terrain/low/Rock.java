package modele.entity.stationary.terrain.low;

import modele.Colors;

/**
 * Classe représentant un rocher sur la carte.
 */
public class Rock extends Low {

    /**
     * Constructeur qui défini la représentation "R" et le displayName "Rocher".
     *
     * @param x La position verticale du rocher.
     * @param y La position horizontale du rocher.
     */
    public Rock(int x, int y) {
        super(x, y);
        this.representation = Colors.WHITE + "R" + Colors.RESET;
        this.displayName = "Rocher";
        this.highlightColor = Colors.WHITE_BACKGROUND + Colors.BLACK;
    }
}
