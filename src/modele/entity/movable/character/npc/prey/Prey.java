package modele.entity.movable.character.npc.prey;

import modele.Board;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.state.prey.JunkieState;
import modele.entity.movable.character.npc.state.prey.NotHungryState;
import modele.entity.stationary.food.BadFood;
import modele.entity.stationary.food.Food;

public class Prey extends NonPlayerCharacter {
    public final int hungryCountBase;
    protected int hungryCount;
    protected Class<? extends Food> foodPreference;
    protected int friendLevel;


    public Prey(int x, int y, int hungryCountBase) {
        super(x, y);
        this.currentState = new NotHungryState(this);
        this.hungryCountBase = hungryCountBase;
        this.hungryCount = hungryCountBase;
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
    public void hit() {
        friendLevel = 0;
    }
}
