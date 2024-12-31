package modele.entity.movable.character.npc.state.predator;

import modele.Colors;
import modele.entity.movable.character.npc.predator.Predator;

public abstract class RaidState extends PredatorState {

    public RaidState(Predator predator) {
        super(predator);
    }

    @Override
    public void deplacement() {
        char move = getPrey();
        if (move == 'a'){
            getDefault("zqsd");
        }
    }


    @Override
    public String applyColorModifier() {
        return Colors.LIGHT_RED + predator.getRepresentation() + Colors.RESET;
    }
}
