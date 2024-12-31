package modele.entity.movable.character.npc.prey;

import modele.Board;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.state.prey.JunkieState;
import modele.entity.movable.character.npc.state.prey.NotHungryState;
import modele.entity.stationary.food.BadFood;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.High;
import modele.entity.stationary.terrain.low.Low;

import java.util.Map;

public abstract class Prey extends NonPlayerCharacter {
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

        if (food instanceof BadFood){
            setCurrentState(new JunkieState(this));
        } else {
            currentState.updateState();
        }
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
        if (!hasMoved){
            currentState.deplacement();
            currentState.updateState();
        }
        hasMoved = false;
    }
}
