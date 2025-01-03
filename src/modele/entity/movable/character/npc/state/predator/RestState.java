package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PredatorMoveCoordinateCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.stationary.terrain.Terrain;


public abstract class RestState extends PredatorState {
    protected int restLevel;
    public RestState(Predator predator, int restLevel) {
        super(predator);
        this.restLevel = restLevel;
    }


    @Override
    public boolean canMove(char direction) {
        return false;
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return false;
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
        System.out.println("restState");
        Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, Board.getInstance().getAt(predator.getX(), predator.getY())));
    }

    @Override
    public String applyColorModifier() {
        return Colors.LIGHT_BLACK + predator.getRepresentation() + Colors.RESET;
    }

    protected abstract PredatorState getNextState();
}
