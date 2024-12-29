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


    @Override
    public void move(char direction) {
        if (canMove(direction)) {
            super.move(direction);
            changeOrientation(direction);
        }
    }

    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(x,y,direction);
        if (target != null && target.isEmpty() && (target instanceof Empty)) {
            return true;
        } else {
            return false;
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

    public int[] getTarget(){
        int[] target = new int[2];
        switch (orientation) {
            case 'z' :
                target[0] = x;
                target[1] = y-1;
                break;
			case 'q' :
                target[0] = x-1;
                target[1] = y;
                break;
            case 's' :
                target[0] = x;
                target[1] = y+1;
                break;
            case 'd' :
                target[0] = x+1;
                target[1] = y;
                break;
            default :
                target[0] = x;
                target[1] = y;
                break;
		}
        return target;
    }
}
