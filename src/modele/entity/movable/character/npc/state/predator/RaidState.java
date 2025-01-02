package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.Colors;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.stationary.terrain.Terrain;

public abstract class RaidState extends PredatorState {

    public RaidState(Predator predator) {
        super(predator);
    }

    @Override
    public void deplacement() {
        Terrain move = getPrey();
        if (move.equals(Board.getInstance().getAt(predator.getX(), predator.getY()))){
            getDefault(null);
        }
    }


    @Override
    public String applyColorModifier() {
        return Colors.LIGHT_RED + predator.getRepresentation() + Colors.RESET;
    }
}
