package modele.clock.commands;

import modele.Board;
import modele.Inventory;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.movable.character.npc.state.State;
import modele.entity.movable.character.npc.state.prey.FriendInInventoryState;

public class FriendInInventoryCommand implements Command {
	private Prey prey;
	private int old_x;
	private int old_y;
	private State old_State;
	private boolean hasGrabbed;
	private String where;

	public FriendInInventoryCommand(Prey prey) {
		this.prey = prey;
		this.old_x = prey.getX();
		this.old_y = prey.getY();
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
			Board.getInstance().clearCase(prey.getX(), prey.getY());
			Board.getInstance().logAction(prey.getDisplayName() + " est " + where);
			hasGrabbed = true;
			prey.setHasMoved(true);
		} catch (Exception exception){
			hasGrabbed = false;
			Board.getInstance().logError(prey.getDisplayName() + " tente d'aller " + where + ", mais l'inventaire est plein...");
		}
	}

	@Override
	public void undoCommand() {
		if (hasGrabbed){
			Inventory.getInstance().remove(prey);
			Board.getInstance().setEntityOnCase(old_x,old_y,prey);
			prey.setCurrentState(old_State);
		}
	}
}
