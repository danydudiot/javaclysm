package modele.entity.movable.character.npc.prey;

import exception.InvalidActionException;
import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.MovePreyCommand;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Fox;
import modele.entity.movable.character.npc.predator.Owl;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.stationary.food.Acorn;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.High;
import modele.entity.stationary.terrain.low.Low;

import java.util.Map;

public class Squirrel extends Prey {
    public Squirrel(int x, int y) {
        super(x, y, 5);
        this.representation = "E";
        this.displayName = "Écureuil";
        this.foodPreference = Acorn.class;
    }

    @Override
    public void hit(Character aggressor) {
        if (aggressor instanceof PlayerCharacter){
            friendLevel = 0;
        } else if (aggressor instanceof Predator) {
            Map<java.lang.Character, Terrain> neighbours = Board.getInstance().getNeighbours(x, y);
            neighbours.put('a', Board.getInstance().getAt(x,y));
            char player = ' ';
            String high = "";
            String low = "";
            for (char direction: neighbours.keySet()){
                Terrain terrain = neighbours.get(direction);
                if (terrain.getEntityOnCase() instanceof PlayerCharacter){
                    player = direction;
                } else if (terrain instanceof High){
                    high += direction;
                } else if (terrain instanceof Low){
                    low += direction;
                }
            }


            if (player != ' ' && isFriendly()){
                Clock.getInstance().addCommandToTurn(new MovePreyCommand(this, 'p'));
            } else if (!high.isEmpty() && aggressor instanceof Fox) {
                if (high.contains("a")){
                    Clock.getInstance().addCommandToTurn(new MovePreyCommand(this, 'a'));
                } else {
                    Clock.getInstance().addCommandToTurn(new MovePreyCommand(this, high.charAt(0)));
                }

            } else if (!low.isEmpty() && aggressor instanceof Owl) {
                    if (low.contains("a")){
                        Clock.getInstance().addCommandToTurn(new MovePreyCommand(this, 'a'));
                    } else {
                        Clock.getInstance().addCommandToTurn(new MovePreyCommand(this, low.charAt(0)));
                    }
            } else {
                Board.getInstance().getAt(x,y).clearEntityOnCase();
                this.setCurrentState(new DeadState(this));
                ((Predator) aggressor).afterHit();
            }

        } else {
            throw new InvalidActionException("Vous ne pouvez pas frapper l'animal");
        }
    }

}
