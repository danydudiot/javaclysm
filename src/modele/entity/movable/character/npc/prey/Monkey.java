package modele.entity.movable.character.npc.prey;

import exception.InvalidActionException;
import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PreyMoveCommand;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Snake;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.prey.MonkeyNotHungryState;
import modele.entity.movable.character.npc.state.prey.TerrifyState;
import modele.entity.stationary.food.Banana;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.High;

import java.util.Map;

public class Monkey extends Prey {

    public Monkey(int x, int y) {
        super(x, y, 3);
        this.representation = "S";
        this.displayName = "Singe";
        this.foodPreference = Banana.class;
        this.setCurrentState(new MonkeyNotHungryState(this));
    }

    @Override
    public boolean isFriendly () {
        return friendLevel >= 2;
    }


    @Override
    public boolean hit(Character aggressor) {
        if (aggressor instanceof PlayerCharacter){
            friendLevel = 0;
            return true;
        } else if (aggressor instanceof Predator) {
            Map<java.lang.Character, Terrain> neighbours = Board.getInstance().getNeighbours(x, y);
            String high = "";
            for (char direction: neighbours.keySet()){
                Terrain terrain = neighbours.get(direction);
                if (terrain instanceof High){
                    high += direction;
                }
            }


            if (!high.isEmpty()) {
                if (aggressor instanceof Snake){
                    if (high.contains("a")){
                        Clock.getInstance().addCommandToTurn(new PreyMoveCommand(this, 'a'));
                        setCurrentState(new TerrifyState(this));
                    } else {
                        Clock.getInstance().addCommandToTurn(new PreyMoveCommand(this, high.charAt(0)));
                        setCurrentState(new TerrifyState(this));
                    }
                    ((Predator) aggressor).afterHit(false);
                    return false;
                }
            } else {
                Board.getInstance().getAt(x,y).clearEntityOnCase();
                this.setCurrentState(new DeadState(this));
                ((Predator) aggressor).afterHit(true);
                return true;
            }

        } else {
            throw new InvalidActionException("Vous ne pouvez pas frapper l'animal");
        }
        return false;
    }

}
