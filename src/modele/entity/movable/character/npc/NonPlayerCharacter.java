package modele.entity.movable.character.npc;

import modele.Board;
import modele.clock.Observateur;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.npc.state.NotHungryState;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.food.Food;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

public abstract class NonPlayerCharacter extends Character implements Interactible, Observateur {
    public final int hungryCountBase;
    private int hungryCount;
    protected Class<? extends Food> foodPreference;
    protected State curentState;

    public NonPlayerCharacter(int x, int y, int hungryCountBase) {
        super(x, y);
        this.curentState = new NotHungryState(this);
        this.hungryCountBase = hungryCountBase;
    }

    public Interaction[] getInteraction(){
        return null;
    }

    public void mettreAJour(Object object){

        if (object instanceof Board){
            Board board = (Board) object;
            curentState.updateState();
            curentState.deplacement(board);
        }
    }

    public void setCurentState(State curentState) {
        this.curentState = curentState;
    }

    public int getHungryCount() {
        return hungryCount;
    }

    public void setHungryCount(int hungryCount) {
        this.hungryCount = hungryCount;
    }
}
