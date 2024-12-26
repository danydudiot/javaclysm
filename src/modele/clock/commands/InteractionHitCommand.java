package modele.clock.commands;

import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.interaction.Hit;

public class InteractionHitCommand implements Command {
	PlayerCharacter p;
	NonPlayerCharacter target;
	int old_friendLevel;
	Hit interaction;

	public InteractionHitCommand(PlayerCharacter p, NonPlayerCharacter target, Hit interaction) {
		this.p 				 = p;
		this.target 	 	 = target;
		this.interaction 	 = interaction;
		this.old_friendLevel = target.getFriendLevel();
	}

	@Override
	public void doCommand() {
		this.interaction.interact(null, target);
	}

	@Override
	public void undoCommand() {
		target.setFriendLevel(old_friendLevel);
	}
}
