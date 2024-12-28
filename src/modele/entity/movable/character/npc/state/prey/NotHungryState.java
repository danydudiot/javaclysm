package modele.entity.movable.character.npc.state.prey;

import modele.clock.Clock;
import modele.clock.commands.MovePreyCommand;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;

public class NotHungryState extends PreyState {

    public NotHungryState(Prey prey) {
        super(prey);
        prey.setHungryCount(prey.hungryCountBase);
    }

    public void updateState(){
        prey.setHungryCount(prey.getHungryCount()-1);
        if (prey.getHungryCount() <= 0){
            if (prey instanceof Squirrel){
                prey.setCurrentState(new SquirrelHungryState(prey));
            } else {
                prey.setCurrentState(new MonkeyHungryState(prey));
            }
        }
    }

    @Override
    public void deplacement() {
        char move = 'a';
        char danger = getDanger(true);
        if (danger != 'a') {
            move = danger;
        }
        if (move == 'a'){
            char defaut = getDefault("zqsd");
            if (defaut != 'a') {
                move = defaut;
            }
        }

        Clock.getInstance().addCommandToTurn(new MovePreyCommand(prey, move));
    }

    @Override
    public String applyColorModifier() {
        if (prey.isFriendly()) {
            // Light purple
            return "\u001b[95m" + prey.getRepresentation() + "\u001b[0m";
        } else {
            // light white
            return "\u001b[97m" + prey.getRepresentation() + "\u001b[0m";
        }
    }
}
