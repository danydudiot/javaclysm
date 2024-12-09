package modele.entity.stationary.terrain.high;

public class PalmTree extends High {

    public PalmTree(int x, int y) {
        super(x, y);
        this.representation = ANSI_CYAN + "P" + ANSI_RESET;
        this.displayName = "Cocotier";
    }

    @Override
    public String toString() {
        if (entityOnCase == null) {
            return representation;
        } else {
            return ANSI_CYAN_BACKGROUND + ANSI_BLACK + entityOnCase.toString() + ANSI_RESET;
        }
    }
}
