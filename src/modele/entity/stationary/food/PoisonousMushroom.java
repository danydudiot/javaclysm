package modele.entity.stationary.food;

import modele.Colors;

public class PoisonousMushroom extends BadFood{
    public PoisonousMushroom(int x, int y) {
        super(x,y);
        this.representation = Colors.ANSI_YELLOW + "C" + Colors.ANSI_RESET;
        this.displayName = "Champignon vénéneux";
    }
}
