package modele.clock.commands;

import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.State;

public class MovePredatorCommand implements Command {
	Predator predator;
	char direction;
	State old_state;

	public MovePredatorCommand(Predator predator, char direction) {
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
		predator.move(predator.getInverseDirection(direction));
		predator.setCurrentState(old_state);
	}



}
