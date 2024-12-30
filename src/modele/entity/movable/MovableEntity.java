package modele.entity.movable;

import modele.Board;
import modele.entity.Entity;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

public abstract class MovableEntity extends Entity {

    public MovableEntity(int x, int y) {
        super(x, y);
    }

	public boolean canMove(char direction) {
		Terrain target = Board.getInstance().getToward(x,y,direction);
		return target != null && target.isEmpty() && (target instanceof Empty);
	}
	public void move(char direction) {
		if (canMove(direction)) {
			Board.getInstance().moveToward(this, direction);
		}
	}

    public char getInverseDirection(char direction) {
		return switch (direction) {
			case 'z' -> 's';
			case 'q' -> 'd';
			case 's' -> 'z';
			case 'd' -> 'q';
			default -> direction;
		};
    }
}
