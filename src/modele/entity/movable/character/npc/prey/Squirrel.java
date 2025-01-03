package modele.entity.movable.character.npc.prey;

import exception.InvalidActionException;
import modele.Board;
import modele.Inventory;
import modele.clock.Clock;
import modele.clock.commands.FriendInInventoryCommand;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Fox;
import modele.entity.movable.character.npc.predator.Owl;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.prey.SquirrelJunkieState;
import modele.entity.movable.character.npc.state.prey.SquirrelNotHungryState;
import modele.entity.movable.character.npc.state.prey.TerrifyState;
import modele.entity.stationary.food.Acorn;
import modele.entity.stationary.food.BadFood;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.High;
import modele.entity.stationary.terrain.low.Low;

import java.util.ArrayList;
import java.util.List;
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
        } else if (aggressor instanceof Predator predator) {

            Terrain currentPosition = Board.getInstance().getAt(getX(), getY());
            // Ici on utilise getNear car on a besoin de la case actuelle.
            List<Terrain> neighbours = Board.getInstance().getNear(getX(), getY(), 1);
            PlayerCharacter player = null;
            List<Terrain> high = new ArrayList<>();
            List<Terrain> low = new ArrayList<>();

            for (Terrain terrain : neighbours){
                if (terrain.getEntityOnCase() instanceof PlayerCharacter){
                    player = (PlayerCharacter) terrain.getEntityOnCase();
                } else if (terrain instanceof High){
                    high.add(terrain);
                } else if (terrain instanceof Low){
                    low.add(terrain);
                }
            }

//            TODO : Retirer si interdit
            if (player != null && isFriendly() && !Inventory.getInstance().isFull()){
                Clock.getInstance().addCommandToTurn(new FriendInInventoryCommand(this));
                currentState.updateState();
                return false;
            } else if (!high.isEmpty() && predator instanceof Fox fox) {
                return runAway(fox, currentPosition, high);

            } else if (!low.isEmpty() && predator instanceof Owl owl) {
                return runAway(owl, currentPosition, low);

            } else {
                Board.getInstance().getAt(x,y).clearEntityOnCase();
                this.setCurrentState(new DeadState(this));
                (predator).afterHit(true);
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


    @Override
    public void eat(boolean isPlayerNearby, Food food) {
        super.eat(isPlayerNearby, food);
        if (food instanceof BadFood){
            setCurrentState(new SquirrelJunkieState(this));
        }
    }
}