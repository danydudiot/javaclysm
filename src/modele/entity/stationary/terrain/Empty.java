package modele.entity.stationary.terrain;

public class Empty extends Terrain {

    public Empty(int x, int y) {
        super(x, y);
        this.representation = " ";
    }
}
