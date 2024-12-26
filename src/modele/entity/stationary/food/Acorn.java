package modele.entity.stationary.food;

import modele.Colors;

public class Acorn extends Food {
    public Acorn(int x, int y) {
        super(x,y);
        this.representation = Colors.ANSI_RED + "G" + Colors.ANSI_RESET;
        this.displayName = "Gland";
    }
}
