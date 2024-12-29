package modele.entity.movable.character.npc.state.prey;

import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.MovePreyCommand;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;

public class JunkieState extends PreyState {
    public JunkieState(Prey prey) {
        super(prey);
        prey.setHungryCount(prey.hungryCountBase);
    }

    @Override
    public void updateState() {
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
        char move1 = getDefault("zqsd");
        Clock.getInstance().addCommandToTurn(new MovePreyCommand(prey, move1));
        char move2 = getDefault("zqsd".replaceAll(String.valueOf(move1), ""));
        Clock.getInstance().addCommandToTurn(new MovePreyCommand(prey, move2));
    }



    @Override
    public String applyColorModifier() {
        if (prey.isFriendly()) {
            // Light purple
            return "\u001b[95m" + prey.getRepresentation() + Colors.RESET;
        } else {
            // light white
            return Colors.RED + prey.getRepresentation() + Colors.RESET;
        }
    }
}
