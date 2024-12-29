package modele.entity.movable.character.npc.state.predator;


import modele.Colors;
import modele.entity.movable.character.npc.predator.Predator;

public class UnderRockState extends PredatorState {

    public UnderRockState(Predator predator) {
        super(predator);
    }

    @Override
    public void updateState() {

    }

    @Override
    public void deplacement() {

    }

    @Override
    public String applyColorModifier() {
        return Colors.WHITE_BACKGROUND + predator.getRepresentation() + Colors.RESET;
    }
}
