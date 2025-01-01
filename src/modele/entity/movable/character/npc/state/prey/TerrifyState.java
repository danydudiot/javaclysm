package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.stationary.terrain.Terrain;

public class TerrifyState extends PreyState {
    private int fearLevel;
    public TerrifyState(Prey prey) {
        super(prey);
        this.fearLevel = 3;

    }

    @Override
    public boolean canMove(char direction) {
        return direction == 'a';
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return terrain.getEntityOnCase() == prey;
    }

    @Override
    public void updateState() {
        fearLevel--;
        if (fearLevel <= 0){

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
    }

    @Override
    public void deplacement() {
        // Clock.getInstance().addCommandToTurn(new PreyMoveCommand(prey, 'a'));
        Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, Board.getInstance().getAt(prey.getX(), prey.getY())));
    }

    @Override
    public String applyColorModifier() {
        return Colors.BLUE_BACKGROUND + prey.getRepresentation() + Colors.RESET;
    }
}
