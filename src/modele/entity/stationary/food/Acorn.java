package modele.entity.stationary.food;

import modele.Colors;

/**
 * Classe représentant un gland.
 */
public class Acorn extends Food {
    /**
     * Constructeur qui défini la représentation "G" et le displayName "Gland".
     *
     * @param x La position vertical.
     * @param y La position horizontal.
     */
    public Acorn(int x, int y) {
        super(x, y);
        this.representation = Colors.YELLOW + "G" + Colors.RESET;
        this.displayName = "Gland";
    }
}
