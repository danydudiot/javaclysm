package modele.clock.commands;

import modele.Inventory;
import modele.entity.Entity;
import modele.entity.movable.character.PlayerCharacter;
import modele.interaction.Grab;

public class InteractionGrabCommand implements Command {
	PlayerCharacter p;
	Entity what;
	Inventory inventory;
	Grab interaction;
	public InteractionGrabCommand(PlayerCharacter p, Entity what, Inventory inventory, Grab interaction) {
		this.p 			 = p;
		this.what 		 = what;
		this.inventory   = inventory;
		this.interaction = interaction;
	}

	@Override
	public void doCommand() {
		interaction.interact(inventory, what);
	}

	@Override
	public void undoCommand() {
		inventory.dropSpecificItem(what);
	}
}
