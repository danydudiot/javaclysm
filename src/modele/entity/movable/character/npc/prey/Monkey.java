package modele.entity.movable.character.npc.prey;

import exception.InvalidActionException;
import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.FriendInInventoryCommand;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Snake;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.prey.MonkeyNotHungryState;
import modele.entity.movable.character.npc.state.prey.SquirrelJunkieState;
import modele.entity.movable.character.npc.state.prey.TerrifyState;
import modele.entity.stationary.food.BadFood;
import modele.entity.stationary.food.Banana;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.High;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Monkey extends Prey {

    private boolean seeSomePredators;

    public Monkey(int x, int y) {
        super(x, y, 3);
        this.representation = "S";
        this.displayName = "Singe";
        this.foodPreference = Banana.class;
        this.setCurrentState(new MonkeyNotHungryState(this));
        this.seeSomePredators = false;
    }

    @Override
    public boolean isFriendly () {
        return friendLevel >= 2;
    }

    public boolean isSeeSomePredators() {
        return seeSomePredators;
    }

    public void setSeeSomePredators(boolean seeSomePredators) {
        this.seeSomePredators = seeSomePredators;
    }

    @Override
    public void eat(boolean isPlayerNearby, Food food) {
        boolean wasFriendly = isFriendly();
        super.eat(isPlayerNearby, food);

        if (!wasFriendly && isFriendly()) {
            Clock.getInstance().addCommandToTurn(new FriendInInventoryCommand(this));
        } else if (food instanceof BadFood){
            setCurrentState(new SquirrelJunkieState(this));
        } else {
            currentState.updateState();
        }
    }

    @Override
    public boolean hit(Character aggressor) {
        if (aggressor instanceof PlayerCharacter){
            friendLevel = 0;
            return true;
        } else if (aggressor instanceof Predator) {
            Terrain currentPosition = Board.getInstance().getAt(getX(), getY());
            // Ici on utilise getNear plutôt que getNeighbours car getNear inclue la case actuelle.
            List<Terrain> neighbours = Board.getInstance().getNear(getX(), getY(), 1);
            List<Terrain> high = new ArrayList<>();

            for (Terrain terrain : neighbours){
                if (terrain instanceof High){
                    high.add(terrain);
                }
            }


            if (!high.isEmpty() && aggressor instanceof Snake snake) {
                return runAway(snake, currentPosition, high);
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
        return terrain instanceof High && predator instanceof Snake;
    }



    public void tryYelling() {
        List<Terrain> around = Board.getInstance().getNear(getX(), getY(), 4);
        List<Predator> danger = new ArrayList<>();
        for (Terrain terrain : around){
            if (terrain.getEntityOnCase() instanceof Predator){
                danger.add((Predator) terrain.getEntityOnCase());
            }
        }

        if (!danger.isEmpty() && isFriendly() && !(isSeeSomePredators())){
            setSeeSomePredators(true);
            Board.getInstance().logAction("(trad: je décele la présences de prédateurs.)");
            Board.getInstance().logError("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH");
        } else if (danger.isEmpty()) {
            setSeeSomePredators(false);
        }
    }

}