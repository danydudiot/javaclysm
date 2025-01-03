package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.prey.Prey;
import modele.Board;
import modele.entity.movable.character.npc.state.DeadState;

public class SquirrelNotHungryState extends NotHungryState {
	public SquirrelNotHungryState(Prey prey) {
		super(prey);
	}
	@Override
	public void updateState(){
		if (prey.getHungryCount() <= 0){
			prey.setCurrentState(new SquirrelHungryState(prey));
		}
	}

	@Override
	public void deplacement() {
		boolean result = getDanger(true);
		if (!result){
			getDefault(null);
		}
	}
}
