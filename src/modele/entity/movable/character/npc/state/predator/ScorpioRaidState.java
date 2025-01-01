package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.Colors;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Rock;



public class ScorpioRaidState extends RaidState{
    public ScorpioRaidState(Predator predator) {
        super(predator);
    }

    @Override
    public void updateState() {
        if (Board.getInstance().getAt(predator.getX(), predator.getY()) instanceof Rock){
            predator.setCurrentState(new UnderRockState(predator));
        }
    }

    @Override
    public boolean canMove(char direction) {
        Terrain terrain = Board.getInstance().getToward(predator.getX(), predator.getY(), direction);
        return terrain instanceof Empty || terrain instanceof Rock;
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return terrain instanceof Empty || terrain instanceof Rock;
    }

    protected boolean isAllow(Terrain terrain) {
        return terrain instanceof Empty || terrain instanceof Rock;
    }


    @Override
    public String applyColorModifier() {
        if (((Scorpio) predator).canAttack()) {
            return Colors.RED + predator.getRepresentation() + Colors.RESET;
        } else {
            return Colors.LIGHT_BLACK + predator.getRepresentation() + Colors.RESET;
        }
    }
}
