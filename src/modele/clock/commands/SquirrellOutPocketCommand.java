package modele.clock.commands;

import modele.Board;
import modele.Inventory;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.movable.character.npc.state.State;
import modele.entity.movable.character.npc.state.prey.SquirrelHungryState;
import modele.entity.movable.character.npc.state.prey.SquirrelInPocketState;
import modele.entity.movable.character.npc.state.prey.SquirrelNotHungryState;
import modele.entity.stationary.terrain.Terrain;

import java.util.List;

public class SquirrellOutPocketCommand implements Command {
	private Squirrel squirrel;
	private State old_State;
	private boolean hasDrop;
	public SquirrellOutPocketCommand(Squirrel squirrel) {
		this.squirrel 	= squirrel;
		this.old_State 	= squirrel.getCurrentState();
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
					Inventory.getInstance().remove(squirrel);
					terrain.setEntityOnCase(squirrel);
					hasDrop = true;
					break;
				}
			}
			++rayon;
		}
		if (squirrel.getHungryCount() <= 0) {
			squirrel.setCurrentState(new SquirrelHungryState(squirrel));
		} else {
			squirrel.setCurrentState(new SquirrelNotHungryState(squirrel));
		}


	}

	@Override
	public void undoCommand() {
		try	{
			Inventory.getInstance().add(squirrel);
			squirrel.setCurrentState(old_State);
			Board.getInstance().getAt(squirrel.getX(), squirrel.getY()).clearEntityOnCase();
		} catch (Exception e) {
			Board.getInstance().logError(e.getMessage());
		}
	}
}
