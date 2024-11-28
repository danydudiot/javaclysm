package modele.entity.stationary.food;

public class Banana extends Food {
    public Banana(int x, int y) {
        super(x,y);
        this.representation = ANSI_YELLOW + "B" + ANSI_RESET;
    }
}
