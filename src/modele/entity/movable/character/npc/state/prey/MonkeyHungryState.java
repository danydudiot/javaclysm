package modele.entity.movable.character.npc.state.prey;

import modele.clock.Clock;
import modele.clock.commands.MovePreyCommand;
import modele.entity.movable.character.npc.prey.Prey;

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
            prey.setCurrentState(new MonkeyHungryState(prey));
        }
    }
}
