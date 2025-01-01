package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PredatorMoveCommand;
import modele.clock.commands.PredatorAttackCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.Tree;
import modele.entity.stationary.terrain.low.Bush;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
