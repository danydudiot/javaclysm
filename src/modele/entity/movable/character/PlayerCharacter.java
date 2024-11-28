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

        board.logAction("Joueur bouge vers " + switch (direction) {
            case 'z' -> "le haut";
            case 's' -> "le bas";
            case 'q' -> "la gauche";
            case 'd' -> "la droite";
            default -> "nulle part ???? ceci est une erreur ?";
        });
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
