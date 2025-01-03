package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.terrain.Terrain;

public class PreyMoveCoordinateCommand implements Command {
	public Prey prey;
	Terrain terrain;
	Terrain old_terrain;
	int old_hunger;
	State old_state;

	public PreyMoveCoordinateCommand(Prey prey, Terrain terrain) {
		this.prey 		 = prey;
		this.terrain 	 = terrain;
		this.old_terrain = Board.getInstance().getAt(prey.getX(), prey.getY());
		this.old_hunger  = prey.getHungryCount();
		this.old_state 	 = prey.getCurrentState();
	}

	@Override
	public void doCommand() {
		if (!old_terrain.equals(terrain)){
			System.out.println("DOPreyMoveCoordinateCommand");
			prey.move(terrain);
		}
		if (!(prey.getCurrentState() instanceof DeadState) && Board.getInstance().getAt(prey.getX(), prey.getY()).getEntityOnCase() == null){
			System.out.println("problème6 " + prey.id);
		}

		prey.setHasMoved(true);

		if (!(prey.getCurrentState() instanceof DeadState) && Board.getInstance().getAt(prey.getX(), prey.getY()).getEntityOnCase() == null){
			System.out.println("problème7 " + prey.id);
		}

	}

	@Override
	public void undoCommand() {
		if (!old_terrain.equals(terrain)) {
			System.out.println("UNDOPreyMoveCoordinateCommand");
			prey.move(old_terrain);
		}
		prey.setHungryCount(old_hunger);
		prey.setCurrentState(old_state);
	}



}
