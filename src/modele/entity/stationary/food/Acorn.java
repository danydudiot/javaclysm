package modele.entity.stationary.food;

public class Acorn extends Food {
    public Acorn(int x, int y) {
        super(x,y);
        this.representation = ANSI_RED + "G" + ANSI_RESET;
    }
}
