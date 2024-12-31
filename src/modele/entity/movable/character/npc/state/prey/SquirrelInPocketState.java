package modele.entity.movable.character.npc.state.prey;

import modele.clock.Clock;
import modele.clock.commands.PlayerDropCommand;
import modele.clock.commands.SquirrellOutPocketCommand;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;


public class SquirrelInPocketState extends PreyState {
	private int timeInPocket;
	public SquirrelInPocketState(Prey prey) {
		super(prey);
		this.timeInPocket = 3;
		// Off by one error, l'écureil reste 3 tours tmtc
	}

	@Override
	public void updateState() {
		if (timeInPocket <= 0) {
			Clock.getInstance().addCommandToTurn(new SquirrellOutPocketCommand((Squirrel) prey));
		}
		timeInPocket--;
	}

	@Override
	public void deplacement() {
	}

	@Override
	public String applyColorModifier() {
		return "YOU SHOULD NEVER SEE THIS (SquirrelInPocketState)";
	}
}
