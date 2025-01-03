package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.terrain.Terrain;

public class PredatorMoveCoordinateCommand implements Command {
	private Predator predator;
	private Terrain terrain;
	private Terrain old_terrain;
	private State old_state;

	public PredatorMoveCoordinateCommand(Predator predator, Terrain terrain) {
		this.predator = predator;
		this.terrain 	= terrain;
		this.old_terrain = Board.getInstance().getAt(predator.getX(), predator.getY());
		this.old_state 	= predator.getCurrentState();
	}

	@Override
	public void doCommand() {
		if (!old_terrain.equals(terrain)) {
			predator.move(terrain);
		}
	}

	@Override
	public void undoCommand() {
		predator.setCurrentState(old_state);
		if (!old_terrain.equals(terrain)) {
			predator.move(old_terrain);
		}
	}



}
