package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

public class SquirrelJunkieState extends JunkieState {
    public SquirrelJunkieState(Prey prey) {
        super(prey);
    }

    @Override
    public void updateState() {
        if (prey.getHungryCount() <= 0){
            prey.setCurrentState(new SquirrelHungryState(prey));
        }
    }

    @Override
    public void deplacement() {
        Terrain move = getDefault(null);
        getDefault(move);
    }
}
