package modele.entity.movable.character.npc.state.prey;

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
		final boolean move = getDanger(false) || getDefault(null) != null;
	}

	@Override
	public boolean canMove(Terrain terrain) {
		return super.canMove(terrain) || (terrain instanceof Rock && terrain.getEntityOnCase() instanceof Scorpio && ((Scorpio)terrain.getEntityOnCase()).canAttack());
	}
}
