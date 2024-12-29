package modele.entity.stationary.food;

import modele.Colors;

/**
 * Classe représentant un champignon vénéneux.
 */
public class PoisonousMushroom extends BadFood {

    /**
     * Constructeur qui défini la représentation "C" et le displayName "Champignon vénéneux".
     *
     * @param x La position vertical.
     * @param y La position horizontal.
     */
    public PoisonousMushroom(int x, int y) {
        super(x, y);
        this.representation = Colors.ANSI_YELLOW + "C" + Colors.ANSI_RESET;
        this.displayName = "Champignon vénéneux";
    }
}
