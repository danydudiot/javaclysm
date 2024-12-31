package modele.clock.commands;

import modele.Inventory;
import modele.entity.Entity;
import modele.interaction.Grab;

public class InteractionGrabCommand implements Command {
	Entity what;
	Grab interaction;
	public InteractionGrabCommand(Entity what,Grab interaction) {
		this.what 		 = what;
		this.interaction = interaction;
	}

	@Override
	public void doCommand() {
		interaction.interact(what);
	}

	@Override
	public void undoCommand() {
		Inventory.getInstance().dropSpecificItem(what);
	}
}
