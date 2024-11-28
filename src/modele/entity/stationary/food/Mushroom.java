package modele.entity.stationary.food;

public class Mushroom extends Food {
    public Mushroom(int x, int y) {
        super(x,y);
        this.representation = ANSI_PURPLE + "C" + ANSI_RESET;
    }
}
