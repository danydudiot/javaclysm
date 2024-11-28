package modele.entity.stationary.terrain.low;

public class Bush extends Low {
    public Bush(int x, int y) {
        super(x,y);
        this.representation = ANSI_GREEN + "B" + ANSI_RESET;
    }

    @Override
    public String toString() {
        if (entityOnCase == null) {
            return representation;
        } else {
            return ANSI_YELLOW_BACKGROUND + ANSI_BLACK
                    + entityOnCase.toString() + ANSI_RESET;
        }
    }
}
