package modele.entity.stationary.terrain.low;

public class Rock extends Low {
    public Rock(int x, int y) {
        super(x,y);
        this.representation = "R";
        this.displayName = "Rocher";
    }
}
