package modele.clock.commands;

import modele.Inventory;
import modele.entity.Entity;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.interaction.Grab;

public class InteractionGrabCommand implements Command {
	PlayerCharacter p;
	Entity what;
	Inventory inventory;
	Grab interaction;
	Character author;
	public InteractionGrabCommand(PlayerCharacter p, Entity what, Inventory inventory, Grab interaction, Character author) {
		this.p 			 = p;
		this.what 		 = what;
		this.inventory   = inventory;
		this.interaction = interaction;
		this.author = author;
	}

	@Override
	public void doCommand() {
		interaction.interact(inventory, what, author);
	}

	@Override
	public void undoCommand() {
		inventory.dropSpecificItem(what);
	}
}
