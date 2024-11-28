package modele.entity.stationary.terrain.high;

public class Tree extends High {

    public Tree(int x, int y) {
        super(x, y);
        this.representation = ANSI_GREEN + "A" + ANSI_RESET;
    }

    @Override
    public String toString() {
        if (entityOnCase == null) {
            return representation;
        } else {
            return ANSI_GREEN_BACKGROUND + ANSI_BLACK + entityOnCase.toString() + ANSI_RESET;
        }
    }
}
