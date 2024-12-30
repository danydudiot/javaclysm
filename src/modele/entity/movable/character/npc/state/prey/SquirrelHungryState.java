package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.prey.Prey;

public class SquirrelHungryState extends HungryState {
    public SquirrelHungryState(Prey npc) {
        super(npc);
    }

    @Override
    public void deplacement() {
        final boolean move = getFood() || getDanger(true) || getDefault("zqsd") != 'a';
    }

    public void updateState(){
        if (prey.getHungryCount() > 0){
            prey.setCurrentState(new SquirrelNotHungryState(prey));
        }
    }
}
