package modele.clock.commands;

import modele.Inventory;
import modele.entity.Entity;
import modele.interaction.Grab;

public class PlayerDropCommand implements Command {

	private Entity what;

	public PlayerDropCommand(Entity what) {
		this.what = what;
	}

	@Override
	public void doCommand() {
		Inventory.getInstance().dropItem();
	}

	@Override
	public void undoCommand() {
		Grab grab = new Grab();
		grab.interact(what);
	}
}
