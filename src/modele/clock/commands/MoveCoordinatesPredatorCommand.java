package modele.clock.commands;

import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.State;

public class MoveCoordinatesPredatorCommand implements Command {
	Predator predator;
	int x;
	int y;
	int new_x;
	int new_y;
	State old_state;

	public MoveCoordinatesPredatorCommand(Predator predator, int new_x, int new_y) {
		this.predator 	= predator;
		this.x 			= predator.getX();
		this.y 			= predator.getY();
		this.new_x		= new_x;
		this.new_y 	  	= new_y;
		this.old_state 	= predator.getCurrentState();
	}

	@Override
	public void doCommand() {
		predator.move(new_x, new_y);
	}

	@Override
	public void undoCommand() {
		predator.move(x,y);
		predator.setCurrentState(old_state);
	}



}
