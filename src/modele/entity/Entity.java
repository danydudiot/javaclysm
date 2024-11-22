package modele.entity;

public abstract class Entity {
    protected int x;
    protected int y;
    protected String representation;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getPosition(){
        return new int[]{x,y};
    }

    @Override
    public String toString() {
        return representation;
    }
}
