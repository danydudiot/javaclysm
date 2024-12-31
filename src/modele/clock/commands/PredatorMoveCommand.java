package modele.clock.commands;

import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.State;

public class PredatorMoveCommand implements Command {
	Predator predator;
	char direction;
	State old_state;

	public PredatorMoveCommand(Predator predator, char direction) {
		this.predator = predator;
		this.direction 	= direction;
		this.old_state 	= predator.getCurrentState();
	}

	@Override
	public void doCommand() {
		predator.move(direction);
	}

	@Override
	public void undoCommand() {
		predator.setCurrentState(old_state);
		predator.move(predator.getInverseDirection(direction));
	}



}
