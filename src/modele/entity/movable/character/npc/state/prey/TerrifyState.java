package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.stationary.terrain.Terrain;

public class TerrifyState extends PreyState {
    public TerrifyState(Prey prey) {
        super(prey);
        prey.setFearLevel(3);

    }

    @Override
    public boolean canMove(Terrain terrain) {
        return terrain.getEntityOnCase() == prey;
    }

    @Override
    public void updateState() {
        if (prey.getFearLevel() <= 0){

            if (prey.getHungryCount() <= 0){
                if (prey instanceof Squirrel){
                    prey.setCurrentState(new SquirrelHungryState(prey));
                } else {
                    prey.setCurrentState(new MonkeyHungryState(prey));
                }
            } else {
                if (prey instanceof Squirrel){
                    prey.setCurrentState(new SquirrelNotHungryState(prey));
                } else {
                    prey.setCurrentState(new MonkeyNotHungryState(prey));
                }
            }
        }
        prey.setFearLevel(prey.getFearLevel()-1);
    }

    @Override
    public void deplacement() {
        Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, Board.getInstance().getAt(prey.getX(), prey.getY())));
    }

    @Override
    public String applyColorModifier() {
        return Colors.BLUE_BACKGROUND + prey.getRepresentation() + Colors.RESET;
    }
}
