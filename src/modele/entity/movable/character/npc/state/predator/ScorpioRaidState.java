package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PredatorAttackCommand;
import modele.clock.commands.PredatorMoveCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.PalmTree;
import modele.entity.stationary.terrain.low.Rock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScorpioRaidState extends RaidState{
    public ScorpioRaidState(Predator predator) {
        super(predator);
    }

    @Override
    public void updateState() {
        if (Board.getInstance().getAt(predator.getX(), predator.getY()) instanceof Rock){
            predator.setCurrentState(new UnderRockState(predator));
        }
    }

    protected boolean isAllow(Terrain terrain) {
        return terrain instanceof Empty || terrain instanceof Rock;
    }


    @Override
    public String applyColorModifier() {
        if (((Scorpio) predator).canAttack()) {
            return Colors.RED + predator.getRepresentation() + Colors.RESET;
        } else {
            return Colors.LIGHT_BLACK + predator.getRepresentation() + Colors.RESET;
        }
    }
}
