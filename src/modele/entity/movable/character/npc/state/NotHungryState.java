package modele.entity.movable.character.npc.state;

import modele.Board;
import modele.entity.movable.character.npc.NonPlayerCharacter;

public class NotHungryState implements State {

    private NonPlayerCharacter npc;
    public NotHungryState(NonPlayerCharacter npc) {
        this.npc = npc;
        npc.setHungryCount(npc.hungryCountBase);
    }

    public void updateState(){
        npc.setHungryCount(npc.getHungryCount()-1);
        if (npc.getHungryCount() <= 0){
            npc.setCurentState(new HungryState(npc));
        }
    }

    @Override
    public char deplacement(Board board) {
        int[] postion = npc.getPosition();
        int[] z = new int[]{postion[0]+1,postion[1]};
        int[] q = new int[]{postion[0],postion[1]-1};
        int[] s = new int[]{postion[0]-1,postion[1]};
        int[] d = new int[]{postion[0],postion[1]+1};



        return 'a';
    }
}
