package modele.entity.movable.character.npc.state.predator;


import exception.InvalidActionException;
import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PredatorMoveCoordinateCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.stationary.terrain.Terrain;

public class UnderRockState extends PredatorState {
    private Scorpio scorpio;
    public UnderRockState(Predator predator) {
        super(predator);
        if (predator instanceof Scorpio scorpio){
            this.scorpio = scorpio;
            scorpio.setTimeUnderRock(5);
        } else {
            throw new InvalidActionException("Ce n'est pas un scorpion qui va sous le rocher.");
        }
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return terrain.getEntityOnCase() == predator;
    }

    @Override
    public void updateState() {
        scorpio.setTimeUnderRock(scorpio.getTimeUnderRock()-1);
        if (scorpio.getTimeUnderRock() <= 0){
            predator.setCurrentState(new ScorpioRaidState(predator));
        }
    }

    @Override
    public void deplacement() {
        Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, Board.getInstance().getAt(predator.getX(), predator.getY())));
    }

    @Override
    public String applyColorModifier() {
        if (((Scorpio) predator).canAttack()) {
            return Colors.WHITE_BACKGROUND + Colors.RED + predator.getRepresentation() + Colors.RESET;
        } else {
            return Colors.WHITE_BACKGROUND + Colors.LIGHT_BLACK + predator.getRepresentation() + Colors.RESET;
        }
    }
}
