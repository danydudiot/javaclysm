package modele.boardFactory;

import modele.Board;
import modele.clock.Clock;

public abstract class BoardFactoryGenerator extends BoardFactory {

	int height;
	int width;

	public BoardFactoryGenerator(Clock clock, int height, int width) {
		this.clock = clock;
		this.height = height;
		this.width = width;
	}

	protected int distance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1),2));
	}
	public Board makeBoard() {
		return generateBoard();
	}
	public Board generateBoard() {
		return null;
	}
}
