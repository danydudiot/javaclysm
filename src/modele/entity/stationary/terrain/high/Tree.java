package modele.entity.stationary.terrain.high;

import modele.Colors;

/**
 * Classe représentant un arbre sur la carte.
 */
public class Tree extends High {

    /**
     * Constructeur qui défini la représentation "A" et le displayName "Arbre".
     *
     * @param x La position verticale du rocher.
     * @param y La position horizontale du rocher.
     */
    public Tree(int x, int y) {
        super(x, y);
        this.representation = Colors.ANSI_GREEN + "A" + Colors.ANSI_RESET;
        this.displayName = "Arbre";
    }

    @Override
    public String toString() {
        if (entityOnCase == null) {
            return representation;
        } else {
            return Colors.ANSI_GREEN_BACKGROUND + Colors.ANSI_BLACK + entityOnCase.toString() + Colors.ANSI_RESET;
        }
    }
}
