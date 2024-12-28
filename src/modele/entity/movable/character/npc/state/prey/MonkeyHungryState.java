package modele.entity.movable.character.npc.state.prey;

import modele.clock.Clock;
import modele.clock.commands.MoveNPCCommand;
import modele.entity.movable.character.npc.prey.Prey;

public class MonkeyHungryState extends HungryState {
    public MonkeyHungryState(Prey prey) {
        super(prey);
    }

    @Override
    public void deplacement() {
        char move = 'a';
        char danger = getDanger(false);
        if (danger != 'a') {
            move = danger;
        }
        if (move == 'a'){
            char food = getFood();
            if (food != 'a') {
                move = food;
            }
        }
        if (move == 'a'){
            char defaut = getDefault("zqsd");
            if (defaut != 'a') {
                move = defaut;
            }
        }

        Clock.getInstance().addCommandToTurn(new MoveNPCCommand(prey, move));

    }
}
