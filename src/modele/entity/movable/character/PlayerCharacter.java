package modele.entity.movable.character;

import modele.Board;

public class PlayerCharacter extends Character{
    protected char orientation;
    public PlayerCharacter(int x, int y) {
        super(x, y);
        this.representation = "@";
        this.orientation = 'z';
    }

    public char getOrientation() {
        return orientation;
    }


    @Override
    public void move(char direction, Board board) {
        super.move(direction, board);
        changeOrientation(direction);
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
		};
        return target;
    }
}
