package modele.clock.commands;

import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.state.State;

public class MoveNPCCommand implements Command {
	NonPlayerCharacter npc;
	char direction;
	int old_hunger;
	State old_state;

	public MoveNPCCommand(NonPlayerCharacter e, char direction) {
		this.npc 		= e;
		this.direction 	= direction;
		this.old_hunger = e.getHungryCount();
		this.old_state 	= e.getCurrentState();
	}

	@Override
	public void doCommand() {
		npc.move(direction);
	}

	@Override
	public void undoCommand() {
		npc.move(npc.getInverseDirection(direction));
		npc.setHungryCount(old_hunger);
		npc.setCurrentState(old_state);
	}



}
