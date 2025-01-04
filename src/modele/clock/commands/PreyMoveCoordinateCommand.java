package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.terrain.Terrain;

public class PreyMoveCoordinateCommand implements Command {
	private Prey prey;
	private Terrain terrain;
	private Terrain old_terrain;
	private int old_hunger;
	private State old_state;

	public PreyMoveCoordinateCommand(Prey prey, Terrain terrain) {
		this.prey 		 = prey;
		this.terrain 	 = terrain;
		this.old_terrain = Board.getInstance().getAt(prey.getX(), prey.getY());
		this.old_hunger  = prey.getHungryCount()+1;
		this.old_state 	 = prey.getCurrentState();
	}

	@Override
	public void doCommand() {
		if (!old_terrain.equals(terrain)){
			prey.move(terrain);
		}
		prey.setHasMoved(true);
	}

	@Override
	public void undoCommand() {
		if (!old_terrain.equals(terrain)) {
			prey.move(old_terrain);
		}
		prey.setHungryCount(old_hunger);
		prey.setCurrentState(old_state);
	}



}
