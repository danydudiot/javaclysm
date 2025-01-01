package modele.entity.movable.character.npc.prey;

import exception.InvalidActionException;
import modele.Board;
import modele.Inventory;
import modele.clock.Clock;
import modele.clock.commands.PreyMoveCommand;
import modele.clock.commands.FriendInInventoryCommand;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Fox;
import modele.entity.movable.character.npc.predator.Owl;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.prey.SquirrelNotHungryState;
import modele.entity.movable.character.npc.state.prey.TerrifyState;
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
        this.setCurrentState(new SquirrelNotHungryState(this));
    }

    @Override
    public boolean hit(Character aggressor) {
        if (aggressor instanceof PlayerCharacter){
            friendLevel = 0;
            return true;
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

//            TODO : Retirer si interdit
            if (player != ' ' && isFriendly() && !Inventory.getInstance().isFull()){
                Clock.getInstance().addCommandToTurn(new FriendInInventoryCommand(this));
                return false;
            } else if (!high.isEmpty() && aggressor instanceof Fox) {
                if (high.contains("a")){
                    Clock.getInstance().addCommandToTurn(new PreyMoveCommand(this, 'a'));
                    setCurrentState(new TerrifyState(this));
                } else {
                    Clock.getInstance().addCommandToTurn(new PreyMoveCommand(this, high.charAt(0)));
                    setCurrentState(new TerrifyState(this));
                }
                ((Predator) aggressor).afterHit(false);
                return false;

            } else if (!low.isEmpty() && aggressor instanceof Owl) {
                if (low.contains("a")){
                    Clock.getInstance().addCommandToTurn(new PreyMoveCommand(this, 'a'));
                    setCurrentState(new TerrifyState(this));
                } else {
                    Clock.getInstance().addCommandToTurn(new PreyMoveCommand(this, low.charAt(0)));
                    setCurrentState(new TerrifyState(this));
                }
                ((Predator) aggressor).afterHit(false);
                return false;

            } else {
                Board.getInstance().getAt(x,y).clearEntityOnCase();
                this.setCurrentState(new DeadState(this));
                ((Predator) aggressor).afterHit(true);
                return true;
            }

        } else {
            throw new InvalidActionException("Vous ne pouvez pas frapper l'animal");
        }
    }

    public boolean isProtected(Terrain terrain, Predator predator){
        return ((terrain instanceof High && predator instanceof Fox) ||
                (terrain instanceof Low && predator instanceof Owl));
    }

}
