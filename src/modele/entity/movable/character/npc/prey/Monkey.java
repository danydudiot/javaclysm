package modele.entity.movable.character.npc.prey;

import exception.InvalidActionException;
import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.FriendInInventoryCommand;
import modele.clock.commands.PreyMoveCommand;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Fox;
import modele.entity.movable.character.npc.predator.Owl;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Snake;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.prey.MonkeyNotHungryState;
import modele.entity.movable.character.npc.state.prey.TerrifyState;
import modele.entity.stationary.food.Banana;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.High;
import modele.entity.stationary.terrain.low.Low;

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
        }
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


            if (!high.isEmpty() && aggressor instanceof Snake) {
                if (high.contains("a")){
                    Clock.getInstance().addCommandToTurn(new PreyMoveCommand(this, 'a'));
                    setCurrentState(new TerrifyState(this));
                } else {
                    Clock.getInstance().addCommandToTurn(new PreyMoveCommand(this, high.charAt(0)));
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
        return terrain instanceof High && predator instanceof Snake;
    }



    public void yell() {
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
