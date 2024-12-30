package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.MovePreyCommand;
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
    public void updateState() {
        fearLevel--;
        prey.setHungryCount(prey.getHungryCount()-1);
        if (fearLevel <= 0){

            if (prey.getHungryCount() <= 0){
                if (prey instanceof Squirrel){
                    prey.setCurrentState(new SquirrelHungryState(prey));
                } else {
                    prey.setCurrentState(new MonkeyHungryState(prey));
                }
            } else {
                prey.setCurrentState(new NotHungryState(prey));
            }
        }
    }

    @Override
    public void deplacement() {
        Clock.getInstance().addCommandToTurn(new MovePreyCommand(prey, 'a'));
    }

    @Override
    public String applyColorModifier() {
        return Colors.LIGHT_BLACK + prey.getRepresentation() + Colors.RESET;
    }
}
