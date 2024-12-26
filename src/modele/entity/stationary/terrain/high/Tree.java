package modele.entity.stationary.terrain.high;

import modele.Colors;

public class Tree extends High {

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
