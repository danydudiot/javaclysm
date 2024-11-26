package modele.entity.movable.character;

import modele.Board;

public class PlayerCharacter extends Character{
    private char direction;
    public PlayerCharacter(int x, int y) {
        super(x, y);
        this.representation = "@";
        this.direction = 'z';
    }

    public char getDirection() {
        return direction;
    }


    @Override
    public void move(char direction, Board board) {
        super.move(direction, board);
        changeDirection(direction);
    }

    public void changeDirection(char direction){
        if ("oklm".indexOf(direction) != -1){
            this.direction = switch (direction) {
                case 'o' -> 'z';
                case 'k' -> 'q';
                case 'l' -> 's';
                case 'm' -> 'd';
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            };
        } else if ("zqsd".indexOf(direction) != -1){
            this.direction = direction;
        }
    }
}
