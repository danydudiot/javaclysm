package modele.clock.commands;

import modele.Board;
import modele.Inventory;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.movable.character.npc.state.State;
import modele.entity.movable.character.npc.state.prey.MonkeyHungryState;
import modele.entity.movable.character.npc.state.prey.MonkeyNotHungryState;
import modele.entity.movable.character.npc.state.prey.SquirrelHungryState;
import modele.entity.movable.character.npc.state.prey.SquirrelNotHungryState;
import modele.entity.stationary.terrain.Terrain;

import java.util.List;

public class FriendOutInventoryCommand implements Command {
	private Prey prey;
	private State old_State;
	private boolean hasDrop;
	public FriendOutInventoryCommand(Prey prey) {
		this.prey = prey;
		this.old_State 	= prey.getCurrentState();
		this.hasDrop 	= false;
	}

	@Override
	public void doCommand() {
		PlayerCharacter playerCharacter = Board.getInstance().getPlayer();
		int rayon = 1;
		while (!hasDrop){
			List<Terrain> around = Board.getInstance().getNear(playerCharacter.getX(), playerCharacter.getY(), rayon);
			for (Terrain terrain : around) {
				if (terrain.isEmpty()){
					Inventory.getInstance().remove(prey);
					terrain.setEntityOnCase(prey);
					hasDrop = true;
					break;
				}
			}
			++rayon;
		}
		if (prey instanceof Squirrel) {
			if (prey.getHungryCount() <= 0) {
				prey.setCurrentState(new SquirrelHungryState(prey));
			} else {
				prey.setCurrentState(new SquirrelNotHungryState(prey));
			}
		} else {
			if (prey.getHungryCount() <= 0) {
				prey.setCurrentState(new MonkeyHungryState(prey));
			} else {
				prey.setCurrentState(new MonkeyNotHungryState(prey));
			}
		}
		prey.setHasMoved(true);
	}

	@Override
	public void undoCommand() {
		try	{
			Inventory.getInstance().add(prey);
			prey.setCurrentState(old_State);
			Board.getInstance().getAt(prey.getX(), prey.getY()).clearEntityOnCase();
		} catch (Exception e) {
			Board.getInstance().logError(e.getMessage());
		}
	}
}
