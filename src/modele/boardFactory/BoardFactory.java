package modele.boardFactory;

import modele.clock.Clock;
import modele.Board;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

import java.io.FileNotFoundException;

public abstract class BoardFactory {
	Clock clock;

	public Board makeBoard() throws FileNotFoundException {
		return null;
	}


}
