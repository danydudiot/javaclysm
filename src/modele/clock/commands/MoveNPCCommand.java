package modele.clock.commands;

import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;

public class MoveNPCCommand implements Command {
	Prey npc;
	char direction;
	int old_hunger;
	State old_state;

	public MoveNPCCommand(Prey npc, char direction) {
		this.npc 		= npc;
		this.direction 	= direction;
		this.old_hunger = npc.getHungryCount();
		this.old_state 	= npc.getCurrentState();
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
