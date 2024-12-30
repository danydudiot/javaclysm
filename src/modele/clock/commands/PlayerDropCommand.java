package modele.clock.commands;

import modele.Inventory;
import modele.entity.Entity;
import modele.entity.movable.character.Character;
import modele.interaction.Grab;

public class PlayerDropCommand implements Command {

	Inventory inventory;
	Entity what;
	Character author;

	public PlayerDropCommand(Inventory inventory, Entity what, Character author) {
		this.inventory = inventory;
		this.what = what;
		this.author = author;
	}

	@Override
	public void doCommand() {
		inventory.dropItem();
	}

	@Override
	public void undoCommand() {
		Grab grab = new Grab();
		grab.interact(inventory, what, author);
	}
}
