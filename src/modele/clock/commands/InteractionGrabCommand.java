package modele.clock.commands;

import modele.Board;
import modele.Inventory;
import modele.InventoryItem;
import modele.entity.Entity;
import modele.interaction.Grab;
import modele.interaction.Interactible;

public class InteractionGrabCommand implements Command {
	private Entity what;
	private int old_x;
	private int old_y;
	private Grab interaction;
	public InteractionGrabCommand(Entity what,Grab interaction) {
		this.what 		 = what;
		this.old_x = what.getX();
		this.old_y = what.getY();
		this.interaction = interaction;
	}

	@Override
	public void doCommand() {
		interaction.interact(what);
	}

	@Override
	public void undoCommand() {
		Inventory.getInstance().remove((InventoryItem) what);
		Board.getInstance().setEntityOnCase(old_x,old_y,what);
	}
}
