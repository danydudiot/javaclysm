package modele.entity.stationary.food;

import modele.Colors;

public class Mushroom extends Food {
    public Mushroom(int x, int y) {
        super(x,y);
        this.representation = Colors.ANSI_PURPLE + "C" + Colors.ANSI_RESET;
        this.displayName = "Champignon";
    }
}
