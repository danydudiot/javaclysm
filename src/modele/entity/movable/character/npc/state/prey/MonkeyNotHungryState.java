package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Monkey;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Rock;

public class MonkeyNotHungryState extends NotHungryState{
	public MonkeyNotHungryState(Prey prey) {
		super(prey);
	}

	@Override
	public void updateState(){
		if (prey.getHungryCount() <= 0){
			prey.setCurrentState(new MonkeyHungryState(prey));
		}
		((Monkey) prey).tryYelling();

	}

	@Override
	public void deplacement() {
		final boolean move = getDanger(false) || getDefault("zqsd") != 'a';
	}


	@Override
	public boolean canMove(char direction) {
		Terrain target = Board.getInstance().getToward(prey.getX(), prey.getY(), direction);
		return super.canMove(direction) || (target instanceof Rock && target.getEntityOnCase() instanceof Scorpio);
	}
}
