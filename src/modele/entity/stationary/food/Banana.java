package modele.entity.stationary.food;

import modele.Colors;

public class Banana extends Food {
    public Banana(int x, int y) {
        super(x,y);
        this.representation = Colors.ANSI_YELLOW + "B" + Colors.ANSI_RESET;
        this.displayName = "Banane";
    }
}
