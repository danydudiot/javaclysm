package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;


public class FoxRaidState extends RaidState {
    public FoxRaidState(Predator predator) {
        super(predator);
    }

    @Override
    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(predator.getX(),predator.getY(),direction);
        // target != null est déjà testé dans instanceof.
        return target instanceof Empty && target.isEmpty();
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return terrain instanceof Empty && terrain.isEmpty();
    }

    @Override
    public void updateState() {}
}
