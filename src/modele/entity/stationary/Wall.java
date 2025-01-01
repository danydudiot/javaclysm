package modele.entity.stationary;

import modele.Colors;

public class Wall extends StaticEntity {
    public Wall(int x, int y) {
        super(x, y);
        this.representation = Colors.WHITE + "#" + Colors.RESET;
        this.displayName = "Mur";
    }


}
