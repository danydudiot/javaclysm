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
        this.representation = Colors.ANSI_GREEN + "B" + Colors.ANSI_RESET;
        this.displayName = "Buisson";
    }

    @Override
    public String toString() {
        if (entityOnCase == null) {
            return representation;
        } else {
            return Colors.ANSI_YELLOW_BACKGROUND + Colors.ANSI_BLACK
                    + entityOnCase.toString() + Colors.ANSI_RESET;
        }
    }
}
