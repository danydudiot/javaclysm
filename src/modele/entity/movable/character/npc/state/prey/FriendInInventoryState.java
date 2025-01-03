package modele.entity.movable.character.npc.state.prey;

import modele.clock.Clock;
import modele.clock.commands.FriendOutInventoryCommand;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;


public class FriendInInventoryState extends PreyState {
	private int timeInPocket;
	public FriendInInventoryState(Prey prey) {
		super(prey);
		this.timeInPocket = 3;
	}

	@Override
	public void updateState() {
		if (timeInPocket <= 0) {
			Clock.getInstance().addCommandToTurn(new FriendOutInventoryCommand(prey));
		}
		timeInPocket--;
	}

	@Override
	public void deplacement() {
	}

	@Override
	public String applyColorModifier() {
		return "YOU SHOULD NEVER SEE THIS (FriendInInventoryState)";
	}
}
