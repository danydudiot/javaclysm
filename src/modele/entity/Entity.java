package modele.entity;

public abstract class Entity {
    protected int x;
    protected int y;
    protected String representation;

    public int[] getPosition(){
        return new int[]{x,y};
    }

    @Override
    public String toString() {
        return representation;
    }
}
