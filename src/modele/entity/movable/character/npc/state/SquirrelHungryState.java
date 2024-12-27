package modele.entity.movable.character.npc.state;

import modele.clock.Clock;
import modele.clock.commands.MoveNPCCommand;
import modele.entity.movable.character.npc.prey.Prey;

public class SquirrelHungryState extends HungryState {
    public SquirrelHungryState(Prey npc) {
        super(npc);
    }

    @Override
    public void deplacement() {
        String cas = "rien";
        char move = 'a';
        char food = getFood();
        if (food != 'a') {
            move = food;
            cas = "nourriture";
        }
        if (move == 'a'){
            char danger = getDanger(true);
            if (danger != 'a') {
                move = danger;
                cas = "danger";

            }
        }
        if (move == 'a'){
            char defaut = getDefault();
            if (defaut != 'a') {
                move = defaut;
                cas = "défaut";
            }
        }

        Clock.getInstance().addCommandToTurn(new MoveNPCCommand(npc, move));

    }
}
