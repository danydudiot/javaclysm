package modele.clock.commands;

import modele.Inventory;
import modele.entity.Entity;
import modele.interaction.Grab;

public class DropCommand implements Command {

	Inventory inventory;
	Entity what;

	public DropCommand(Inventory inventory, Entity what) {
		this.inventory = inventory;
		this.what = what;
	}

	@Override
	public void doCommand() {
		inventory.dropItem();
	}

	@Override
	public void undoCommand() {
		Grab grab = new Grab();
		grab.interact(inventory, what);
	}
}
