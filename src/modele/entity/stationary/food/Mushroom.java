package modele.entity.stationary.food;

import modele.Colors;

/**
 * Classe représentant un champignon normal.
 */
public class Mushroom extends Food {
    /**
     * Constructeur qui défini la représentation "C" et le displayName "Champignon".
     *
     * @param x La position vertical.
     * @param y La position horizontal.
     */
    public Mushroom(int x, int y) {
        super(x, y);
        this.representation = Colors.PURPLE + "C" + Colors.RESET;
        this.displayName = "Champignon";
    }
}
