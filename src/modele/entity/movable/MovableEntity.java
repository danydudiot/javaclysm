package modele.entity.movable;

import modele.Board;
import modele.entity.Entity;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

public abstract class MovableEntity extends Entity {

    public MovableEntity(int x, int y) {
        super(x, y);
    }


	public void move(Terrain terrain) {
		if (canMove(terrain)) {
			Board.getInstance().moveTo(this, terrain.getX(), terrain.getY());
		}
	}

	public boolean canMove(Terrain target) {
		return target != null && target.isEmpty() && (target instanceof Empty);
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
