package modele.clock.commands;

import modele.Board;
import modele.Inventory;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.movable.character.npc.state.State;
import modele.entity.movable.character.npc.state.prey.FriendInInventoryState;

public class FriendInInventoryCommand implements Command {
	private Prey prey;
	private State old_State;
	private boolean hasGrabbed;
	private String where;
	public FriendInInventoryCommand(Prey prey) {
		this.prey = prey;
		this.old_State = prey.getCurrentState();
		if (prey instanceof Squirrel) {
			this.where = "dans la poche";
		} else {
			this.where = "sur l'épaule";
		}
	}

	@Override
	public void doCommand() {
		try{
			Inventory.getInstance().add(prey);
			prey.setCurrentState(new FriendInInventoryState(prey));
			Board.getInstance().getAt(prey.getX(), prey.getY()).clearEntityOnCase();
			hasGrabbed = true;
			Board.getInstance().logAction(prey.getDisplayName() + " est " + where);
			prey.setHasMoved(true);
		} catch (Exception exception){
			hasGrabbed = false;
			Board.getInstance().logError(prey.getDisplayName() + " tente d'aller " + where + ", mais l'inventaire est plein...");
		}
	}

	@Override
	public void undoCommand() {
		if (hasGrabbed){
			Inventory.getInstance().dropSpecificItem(prey);
			Board.getInstance().getAt(prey.getX(), prey.getY()).setEntityOnCase(prey);
			prey.setCurrentState(old_State);
		}
	}
}
