package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.prey.Prey;

public class MonkeyNotHungryState extends NotHungryState{
	public MonkeyNotHungryState(Prey prey) {
		super(prey);
	}

	@Override
	public void updateState(){
		prey.setHungryCount(prey.getHungryCount()-1);
		if (prey.getHungryCount() <= 0){
			prey.setCurrentState(new MonkeyHungryState(prey));
		}
	}

	@Override
	public void deplacement() {
		final boolean move = getDanger(false) || getDefault("zqsd") != 'a';
	}
}
