package modele.boardFactory.generator;

import modele.entity.movable.character.PlayerCharacter;
import modele.entity.stationary.food.Mushroom;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

public abstract class Generator {
    private boolean isPlayerCreated = false;


    public Terrain generateEmpty(int x, int y) {
        return new Empty(x,y);
    }

    public Terrain generatePlayer(int x, int y) {
        if (!isPlayerCreated) {
            Terrain terrain = new Empty(x, y);
            PlayerCharacter player = new PlayerCharacter(x, y);
            terrain.setEntityOnCase(player);
            isPlayerCreated = true;
            return terrain;
        } else {
            throw new IllegalArgumentException("Le fichier est invalide. (2 joueurs)");
        }
    }

    public abstract Terrain generateHighTerrain(int x, int y);

    public abstract Terrain generateLowTerrain(int x, int y);

    public abstract Terrain generatePrey(int x, int y);

    public abstract Terrain generatePredator1(int x, int y);

    public abstract Terrain generatePredator2(int x, int y);

    public abstract Terrain generateFood(int x, int y);

    public abstract Terrain generateBadFood(int x, int y);

    public Terrain generateMushroom(int x, int y){
        Terrain empty = generateEmpty(x,y);
        empty.setEntityOnCase(new Mushroom(x,y));
        return empty;
    }

    public abstract Terrain parse(int x, int y,char content);
}
