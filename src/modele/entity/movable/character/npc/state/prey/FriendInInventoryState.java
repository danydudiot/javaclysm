package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.FriendOutInventoryCommand;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;


public class FriendInInventoryState extends PreyState {
	public FriendInInventoryState(Prey prey) {
		super(prey);
		prey.setTimeInPocket(3);
	}

	@Override
	public void updateState() {
		if (prey.getTimeInPocket() <= 0) {
			Clock.getInstance().addCommandToTurn(new FriendOutInventoryCommand(prey));
		}
		prey.setTimeInPocket(prey.getTimeInPocket()-1);
	}

	@Override
	public void deplacement() {
		Terrain current = Board.getInstance().getAt(prey.getX(), prey.getY());
		Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, current));
	}

	@Override
	public String applyColorModifier() {
		return "YOU SHOULD NEVER SEE THIS (FriendInInventoryState)";
	}
}
