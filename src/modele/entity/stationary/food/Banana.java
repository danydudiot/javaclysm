package modele.entity.stationary.food;

import modele.Colors;

/**
 * Classe représentant une banane.
 */
public class Banana extends Food {
    /**
     * Constructeur qui défini la représentation "B" et le displayName "Banane".
     *
     * @param x La position vertical.
     * @param y La position horizontal.
     */
    public Banana(int x, int y) {
        super(x, y);
        this.representation = Colors.ANSI_YELLOW + "B" + Colors.ANSI_RESET;
        this.displayName = "Banane";
    }
}
