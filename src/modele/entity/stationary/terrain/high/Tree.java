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
        this.representation = Colors.GREEN + "A" + Colors.RESET;
        this.displayName = "Arbre";
        this.highlightColor = Colors.GREEN_BACKGROUND + Colors.BLACK;
    }
}
