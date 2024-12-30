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
		Board board = Board.getInstance();
		int[] new_position = board.moveEntity(x, y, direction);
		if (this.x != new_position[0] || this.y != new_position[1]) {
			this.x = new_position[0];
			this.y = new_position[1];
		}
	}

	public void move(int new_x, int new_y) {
		Board board = Board.getInstance();
		int[] new_position = board.moveEntity(x, y, new_x, new_y );
		if (this.x != new_position[0] || this.y != new_position[1]) {
			this.x = new_position[0];
			this.y = new_position[1];
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
