package modele.entity.movable.character.npc.state.prey;

import modele.Colors;
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
        char move2 = getDefault("zqsd".replaceAll(String.valueOf(prey.getInverseDirection(move1)), ""));
    }



    @Override
    public String applyColorModifier() {
        return Colors.RED + prey.getRepresentation() + Colors.RESET;
    }
}
