package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Rock;

import java.util.ArrayList;
import java.util.List;

public class SquirrelJunkieState extends JunkieState {
    public SquirrelJunkieState(Prey prey) {
        super(prey);
    }

    @Override
    public void updateState() {
        prey.setHungryCount(prey.getHungryCount()-1);
        if (prey.getHungryCount() <= 0){
            prey.setCurrentState(new SquirrelHungryState(prey));
        }

    }

    @Override
    public void deplacement() {
        List<List<Terrain>> neighbours = Board.getInstance().getNearSorted(prey.getX(), prey.getY(),2);
        List<Terrain> casePossible = new ArrayList<>();

        for (int i = neighbours.size()-1; i >= 0; --i) {
            for (Terrain terrain : neighbours.get(i)) {
                if (canMove(terrain)) {
                    casePossible.add(terrain);
                }
            }
            if (!casePossible.isEmpty()){
                Terrain terrain = casePossible.get((int) (Math.random() * casePossible.size()));
                Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, terrain));
                return;
            }
        }

        Terrain terrain = Board.getInstance().getAt(prey.getX(), prey.getY());
        Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, terrain));
    }
}
