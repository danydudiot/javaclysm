package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Monkey;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Rock;

public class MonkeyHungryState extends HungryState {
    public MonkeyHungryState(Prey prey) {
        super(prey);
    }

    @Override
    public void deplacement() {
        final boolean move = getDanger(false) || getFood() || getDefault(null) != null;
    }

    public void updateState(){
        if (prey.getHungryCount() > 0){
            prey.setCurrentState(new MonkeyNotHungryState(prey));
        }
        ((Monkey) prey).tryYelling();
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return super.canMove(terrain) || (terrain instanceof Rock && terrain.getEntityOnCase() instanceof Scorpio && ((Scorpio)terrain.getEntityOnCase()).canAttack());
    }
}
