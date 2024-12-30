package modele.entity.movable.character.npc.state.predator;


import modele.Board;
import modele.Colors;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;

public class UnderRockState extends PredatorState {

    public UnderRockState(Predator predator) {
        super(predator);
    }


    @Override
    public boolean canMove(char direction) {
        return false;
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
