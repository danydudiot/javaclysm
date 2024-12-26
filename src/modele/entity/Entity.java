package modele.entity;

public abstract class Entity {
    protected int x;
    protected int y;
    protected String representation;
    protected String displayName;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getPosition(){
        return new int[]{x,y};
    }

    public void setPosition(int x,int y){
        this.x = x;
        this.y = y;
    }

    public String getRepresentation() {
        return representation;
    }

    @Override
    public String toString() {
        return representation;
    }

    public String getDisplayName() {
        return displayName;
    }
}
