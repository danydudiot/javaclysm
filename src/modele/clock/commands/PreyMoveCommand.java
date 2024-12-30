package modele.clock.commands;

import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;

public class PreyMoveCommand implements Command {
	Prey prey;
	char direction;
	int old_hunger;
	State old_state;

	public PreyMoveCommand(Prey prey, char direction) {
		this.prey = prey;
		this.direction 	= direction;
		this.old_hunger = prey.getHungryCount();
		this.old_state 	= prey.getCurrentState();
	}

	@Override
	public void doCommand() {
		prey.move(direction);
	}

	@Override
	public void undoCommand() {
		prey.move(prey.getInverseDirection(direction));
		prey.setHungryCount(old_hunger);
		prey.setCurrentState(old_state);
	}



}
