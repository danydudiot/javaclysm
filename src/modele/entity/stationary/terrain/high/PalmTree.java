package modele.entity.stationary.terrain.high;

import modele.Colors;

public class PalmTree extends High {

    public PalmTree(int x, int y) {
        super(x, y);
        this.representation = Colors.ANSI_CYAN + "P" + Colors.ANSI_RESET;
        this.displayName = "Cocotier";
    }

    @Override
    public String toString() {
        if (entityOnCase == null) {
            return representation;
        } else {
            return Colors.ANSI_CYAN_BACKGROUND + Colors.ANSI_BLACK + entityOnCase.toString() + Colors.ANSI_RESET;
        }
    }
}
