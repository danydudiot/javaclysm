package modele.clock.commands;

import modele.Board;
import modele.Inventory;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.movable.character.npc.state.State;
import modele.entity.movable.character.npc.state.prey.SquirrelInPocketState;

public class SquirrelInPocketCommand implements Command {
	private Squirrel squirrel;
	private State old_State;
	private boolean hasGrabbed;
	public SquirrelInPocketCommand(Squirrel squirrel) {
		this.squirrel = squirrel;
		this.old_State = squirrel.getCurrentState();
	}

	@Override
	public void doCommand() {
		try{
			Inventory.getInstance().add(squirrel);
			squirrel.setCurrentState(new SquirrelInPocketState(squirrel));
			Board.getInstance().getAt(squirrel.getX(), squirrel.getY()).clearEntityOnCase();
			hasGrabbed = true;
			Board.getInstance().logAction(squirrel.getDisplayName() + " est rentré dans la poche");
		} catch (Exception exception){
			hasGrabbed = false;
			Board.getInstance().logError(squirrel.getDisplayName() + " tente de rentrer dans la poche, mais l'inventaire est plein");
		}
	}

	@Override
	public void undoCommand() {
		if (hasGrabbed){
			Inventory.getInstance().dropSpecificItem(squirrel);
			Board.getInstance().getAt(squirrel.getX(), squirrel.getY()).setEntityOnCase(squirrel);
			squirrel.setCurrentState(old_State);
		}
	}
}
