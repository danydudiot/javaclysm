package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PredatorAttackCommand;
import modele.clock.commands.PredatorMoveCoordinateCommand;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Bush;

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
        for (Terrain terrain : around) {
            if (terrain.getEntityOnCase() instanceof Prey && !(terrain instanceof Bush)) {
                preyList.add((Prey) terrain.getEntityOnCase());
            }
        }

        if (!preyList.isEmpty()) {
            Clock.getInstance().addCommandToTurn(new PredatorAttackCommand(predator, preyList.get(0)));
        } else {

            List<List<Terrain>> neighbours = Board.getInstance().getNearSorted(predator.getX(), predator.getY(),2);
            List<Terrain> casePossible = new ArrayList<>();

            for (int i = neighbours.size()-1; i >= 0; --i) {
                for (Terrain terrain : neighbours.get(i)) {
                    if (canMove(terrain)) {
                        casePossible.add(terrain);
                    }
                }
                if (!casePossible.isEmpty()){
                    Terrain terrain = casePossible.get((int) (Math.random() * casePossible.size()));
                    Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, terrain));
                    return;
                }
            }
            Terrain terrain = Board.getInstance().getAt(predator.getX(), predator.getY());
            Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, terrain));
        }

    }


}
