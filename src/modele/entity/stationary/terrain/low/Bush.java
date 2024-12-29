package modele.entity.stationary.terrain.low;

import modele.Colors;

/**
 * Classe représentant un buisson sur la carte.
 */
public class Bush extends Low {
    /**
     * Constructeur qui défini la représentation "B" et le displayName "Buisson".
     *
     * @param x La position verticale du buisson.
     * @param y La position horizontale du buisson.
     */
    public Bush(int x, int y) {
        super(x, y);
        this.representation = Colors.GREEN + "B" + Colors.RESET;
        this.displayName = "Buisson";
        this.highlightColor = Colors.YELLOW_BACKGROUND + Colors.BLACK;
    }
}
