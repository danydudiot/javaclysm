package modele.entity.movable.character.npc.predator;

import modele.Board;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.predator.RestState;

public abstract class Predator extends NonPlayerCharacter {
    public Predator(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean hit(Character aggressor) {
        if (currentState instanceof RestState){
            System.out.println("prédator= " + this);
            Board.getInstance().getAt(x,y).clearEntityOnCase();
            setCurrentState(new DeadState(this));
            return true;
        }
        return false;
    }


    public abstract void afterHit(boolean killed);
}
