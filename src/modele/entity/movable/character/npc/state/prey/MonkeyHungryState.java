package modele.entity.movable.character.npc.state.prey;

import modele.Board;
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
        final boolean move = getDanger(false) || getFood() || getDefault("zqsd") != 'a';
    }

    public void updateState(){
        if (prey.getHungryCount() > 0){
            prey.setCurrentState(new MonkeyNotHungryState(prey));
        }
        ((Monkey) prey).tryYelling();
    }

    @Override
    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(prey.getX(), prey.getY(), direction);
        return super.canMove(direction) || (target instanceof Rock && target.getEntityOnCase() instanceof Scorpio);
    }
}
