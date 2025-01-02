package modele.entity.movable.character.npc.prey;

import modele.Board;
import modele.InventoryItem;
import modele.clock.Clock;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.prey.JunkieState;
import modele.entity.movable.character.npc.state.prey.TerrifyState;
import modele.entity.stationary.food.BadFood;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;

import java.util.List;

public abstract class Prey extends NonPlayerCharacter implements InventoryItem {
    public final int hungryCountBase;
    protected int hungryCount;
    protected Class<? extends Food> foodPreference;
    protected int friendLevel;

    protected boolean hasMoved;


    public Prey(int x, int y, int hungryCountBase) {
        super(x, y);
        this.hungryCountBase = hungryCountBase;
        this.hungryCount = hungryCountBase;
        this.hasMoved = false;
    }


    public boolean isHasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    public boolean isFriendly () {
        return friendLevel >= 1;
    }
    public int getFriendLevel() {
        return friendLevel;
    }
    public void setFriendLevel(int value) {
        friendLevel = value;
    }

    public int getHungryCount() {
        return hungryCount;
    }
    public void setHungryCount(int hungryCount) {
        this.hungryCount = hungryCount;
    }
    public Class<? extends Food> getFoodPreference() {
        return foodPreference;
    }


    public void eat(boolean isPlayerNearby, Food food) {
        if (isPlayerNearby) {
            if (! isFriendly()) {
                friendLevel++;
                if (isFriendly()) {
                    Board.getInstance().logAction(displayName + " est maintenant un ami");
                }
            }
        }
        hungryCount = hungryCountBase;
    }

    @Override
    public String getDisplayName() {
        if (isFriendly()) {
            return displayName + " (Ami)";
        } else {
            return displayName;
        }
    }

    @Override
    public void mettreAJour(){
        if (hungryCount >= 0) {
            --hungryCount; // pour éviter les nombres infiniment négatifs.
        }
        if (!hasMoved) {
            currentState.deplacement();
            currentState.updateState();
        }
        System.out.println(currentState);
        hasMoved = false;
    }

    protected boolean runAway(Predator aggressor, Terrain currentPosition, List<Terrain> terrainList) {
        if (terrainList.contains(currentPosition)){
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(this, currentPosition));
            setCurrentState(new TerrifyState(this));
        } else {
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(this, terrainList.get(0)));
            setCurrentState(new TerrifyState(this));
        }
        aggressor.afterHit(false);
        return false;
    }


    public abstract boolean isProtected(Terrain terrain, Predator predator);
}
