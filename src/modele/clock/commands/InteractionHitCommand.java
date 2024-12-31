package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;
import modele.interaction.Hit;

public class InteractionHitCommand implements Command {
	NonPlayerCharacter target;
	State old_state;
	int old_friendLevel;
	Hit interaction;


	public InteractionHitCommand(NonPlayerCharacter target, Hit interaction) {
		this.target			= target;
		this.interaction 	= interaction;
		this.old_state		= target.getCurrentState();
		if (target instanceof Prey) {
			this.old_friendLevel = ((Prey) target).getFriendLevel();
		}
	}

	@Override
	public void doCommand() {
		this.interaction.interact(target);
	}

	@Override
	public void undoCommand() {
		if (target instanceof Prey) {
			((Prey) target).setFriendLevel(old_friendLevel);
		} else {
			Board.getInstance().getAt(target.getX(), target.getY()).setEntityOnCase(target);
			target.setCurrentState(old_state);
		}
	}
}
