package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.MoveCoordinatesPredatorCommand;
import modele.clock.commands.MovePredatorCommand;
import modele.entity.Entity;
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
        List<Prey> prey = new ArrayList<>();
        for (Terrain entity : around) {
            if (entity.getEntityOnCase() instanceof Prey) {
                prey.add((Prey) entity.getEntityOnCase());
            }
        }

        if (prey.isEmpty()) {
            char move1 = getDefault("zqsd");
            Clock.getInstance().addCommandToTurn(new MovePredatorCommand(predator, move1));
            char move2 = getDefault("zqsd".replaceAll(String.valueOf(move1), ""));
            Clock.getInstance().addCommandToTurn(new MovePredatorCommand(predator, move2));
        } else {
            int[] position = prey.get(0).getPosition();
            Clock.getInstance().addCommandToTurn(new MoveCoordinatesPredatorCommand(predator, position[0], position[1]));
        }

    }


}
