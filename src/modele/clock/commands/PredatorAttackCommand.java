package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;

public class PredatorAttackCommand implements Command{

	Predator predator;
	State old_predatorState;
	int old_predatorX;
	int old_predatorY;
	Prey prey;
	State old_preyState;
	int old_preyX;
	int old_preyY;
	boolean wasKilled;

	public PredatorAttackCommand(Predator predator, Prey prey) {
		this.predator 			= predator;
		this.old_predatorState 	= predator.getCurrentState();
		this.old_predatorX 		= predator.getX();
		this.old_predatorY 		= predator.getY();
		this.prey 				= prey;
		this.old_preyState 		= prey.getCurrentState();
		this.old_preyX 			= prey.getX();
		this.old_preyY 			= prey.getY();
	}

	@Override
	public void doCommand() {
		this.wasKilled = prey.hit(predator);
	}

	@Override
	public void undoCommand() {
		predator.setCurrentState(old_predatorState);
		Board.getInstance().moveTo(predator, old_predatorX, old_predatorY);
		if (wasKilled) {
			Board.getInstance().getAt(old_preyX, old_preyY).setEntityOnCase(prey);
		} else {
			Board.getInstance().moveTo(prey, old_preyX, old_preyY);
		}
		prey.setCurrentState(old_preyState);
	}
}
