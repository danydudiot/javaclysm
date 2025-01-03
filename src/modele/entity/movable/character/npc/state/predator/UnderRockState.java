package modele.entity.movable.character.npc.state.predator;


import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PredatorMoveCoordinateCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.stationary.terrain.Terrain;

public class UnderRockState extends PredatorState {
    protected int time;
    public UnderRockState(Predator predator) {
        super(predator);
        this.time = 5;
    }


    @Override
    public boolean canMove(char direction) {
        return direction == 'a';
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return terrain.getEntityOnCase() == predator;
    }

    @Override
    public void updateState() {
        time--;
        if (time <= 0){
            predator.setCurrentState(new ScorpioRaidState(predator));
        }
    }

    @Override
    public void deplacement() {
        System.out.println("underRock");
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
