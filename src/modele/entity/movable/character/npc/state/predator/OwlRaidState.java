package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PredatorAttackCommand;
import modele.clock.commands.PredatorMoveCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

public class OwlRaidState extends RaidState {
    public OwlRaidState(Predator predator) {
        super(predator);
    }

    @Override
    public void updateState() {

    }

    @Override
    public void deplacement() {
        List<Terrain> around = Board.getInstance().getNear(predator.getX(), predator.getY(), 3);
        List<Prey> preyList = new ArrayList<>();
        for (Terrain entity : around) {
            if (entity.getEntityOnCase() instanceof Prey) {
                preyList.add((Prey) entity.getEntityOnCase());
            }
        }

        if (preyList.isEmpty()) {
            char move1 = getDefault("zqsd");
            Clock.getInstance().addCommandToTurn(new PredatorMoveCommand(predator, move1));
            char move2 = getDefault("zqsd".replaceAll(String.valueOf(move1), ""));
            Clock.getInstance().addCommandToTurn(new PredatorMoveCommand(predator, move2));
        } else {
            Clock.getInstance().addCommandToTurn(new PredatorAttackCommand(predator, preyList.get(0)));
        }

    }


}
