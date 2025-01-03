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
		if (!(prey.getCurrentState() instanceof DeadState) && Board.getInstance().getAt(prey.getX(), prey.getY()).getEntityOnCase() == null){
			System.out.println("problème3 " + prey.id);
		}
		boolean result = getDanger(true);
		if (!(prey.getCurrentState() instanceof DeadState) && Board.getInstance().getAt(prey.getX(), prey.getY()).getEntityOnCase() == null){
			System.out.println("problème4 " + prey.id);
		}
		if (!result){

			getDefault(null);
		}
	}
}
