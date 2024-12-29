package modele.entity.movable.character.npc.state.predator;

import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.MovePredatorCommand;
import modele.entity.movable.character.npc.predator.Predator;


public abstract class RestState extends PredatorState {
    protected int restLevel;
    public RestState(Predator predator, int restLevel) {
        super(predator);
        this.restLevel = restLevel;
    }


    @Override
    public void updateState() {
        restLevel--;

        if (restLevel <= 0){
            predator.setCurrentState(getNextState());
        }
    }

    @Override
    public void deplacement() {
        Clock.getInstance().addCommandToTurn(new MovePredatorCommand(predator, 'a'));
    }

    @Override
    public String applyColorModifier() {
        return Colors.RED + predator.getRepresentation() + Colors.RESET;
    }

    protected abstract PredatorState getNextState();
}
