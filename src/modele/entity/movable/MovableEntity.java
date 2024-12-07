package modele.entity.movable;

import modele.Board;
import modele.entity.Entity;

public abstract class MovableEntity extends Entity {

    public MovableEntity(int x, int y) {
        super(x, y);
    }

    public boolean move(char direction, Board board){
        int [] new_position = board.moveEntity(x,y,direction);
        if (this.x != new_position[0] || this.y != new_position[1]) {
            this.x = new_position[0];
            this.y = new_position[1];
            return true;
        } else {
            return false;
        }
    }
}
