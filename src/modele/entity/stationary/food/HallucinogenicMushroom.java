package modele.entity.stationary.food;

import modele.Colors;

/**
 * Classe représentant un champignon hallucinogène.
 */
public class HallucinogenicMushroom extends BadFood {

    /**
     * Constructeur qui défini la représentation "C" et le displayName "Champignon hallucinogène".
     *
     * @param x La position vertical.
     * @param y La position horizontal.
     */
    public HallucinogenicMushroom(int x, int y) {
        super(x, y);
        this.representation = Colors.BLUE + "C" + Colors.RESET;
        this.displayName = "Champignon hallucinogène";
    }
}
