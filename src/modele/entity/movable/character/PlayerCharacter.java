package modele.entity.movable.character;

import modele.Board;
import modele.Colors;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

public class PlayerCharacter extends Character{
    protected char orientation;
    public PlayerCharacter(int x, int y) {
        super(x, y);
        this.representation = Colors.PLAYER + "@" + Colors.RESET;
        this.orientation = 'z';
        this.displayName = "Joueur";
    }

    public char getOrientation() {
        return orientation;
    }


    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(x,y,direction);
        return target != null && target.isEmpty() && (target instanceof Empty);
    }

    public void move(char direction) {
        if (canMove(direction)) {
            Board.getInstance().moveToward(this, direction);
            changeOrientation(direction);
        } else {
            Board.getInstance().logError("Déplacement impossible");
        }
    }

    public void changeOrientation(char orientation){
        if ("oklm".indexOf(orientation) != -1){
            this.orientation = switch (orientation) {
                case 'o' -> 'z';
                case 'k' -> 'q';
                case 'l' -> 's';
                case 'm' -> 'd';
                default -> throw new IllegalStateException("Unexpected value: " + orientation);
            };
        } else if ("zqsd".indexOf(orientation) != -1){
            this.orientation = orientation;
        }
    }
}
