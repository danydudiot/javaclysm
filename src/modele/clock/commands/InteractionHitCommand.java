package modele.clock.commands;

import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.prey.Prey;
import modele.interaction.Hit;

public class InteractionHitCommand implements Command {
	PlayerCharacter p;
	Prey target;
	int old_friendLevel;
	Hit interaction;
	Character author;

	public InteractionHitCommand(PlayerCharacter p, Prey target, Hit interaction, Character author) {
		this.p 				 = p;
		this.target 	 	 = target;
		this.interaction 	 = interaction;
		this.old_friendLevel = target.getFriendLevel();
		this.author = author;
	}

	@Override
	public void doCommand() {
		this.interaction.interact(null, target, author);
	}

	@Override
	public void undoCommand() {
		target.setFriendLevel(old_friendLevel);
	}
}
