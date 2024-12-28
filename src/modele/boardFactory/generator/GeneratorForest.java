package modele.boardFactory.generator;

import modele.clock.Clock;
import modele.entity.movable.character.npc.predator.Fox;
import modele.entity.movable.character.npc.predator.Owl;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.stationary.food.Acorn;
import modele.entity.stationary.food.PoisonousMushroom;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.Tree;
import modele.entity.stationary.terrain.low.Bush;

public class GeneratorForest extends Generator{

    @Override
    public Terrain generateHighTerrain(int x, int y) {
        return new Tree(x,y);
    }

    @Override
    public Terrain generateLowTerrain(int x, int y) {
        return new Bush(x,y);
    }

    @Override
    public Terrain generatePrey(int x, int y) {
        Terrain empty = generateEmpty(x,y);
        Squirrel prey = new Squirrel(x,y);
        Clock.getInstance().attacher(prey);
        empty.setEntityOnCase(prey);
        return empty;
    }

    @Override
    public Terrain generatePredator1(int x, int y) {
        Terrain empty = generateEmpty(x,y);
        Fox predator = new Fox(x,y);
        Clock.getInstance().attacher(predator);
        empty.setEntityOnCase(predator);
        return empty;
    }

    @Override
    public Terrain generatePredator2(int x, int y) {
        Terrain empty = generateEmpty(x,y);
        Owl predator = new Owl(x,y);
        Clock.getInstance().attacher(predator);
        empty.setEntityOnCase(predator);
        return empty;
    }

    @Override
    public Terrain generateFood(int x, int y) {
        Terrain empty = generateEmpty(x,y);
        empty.setEntityOnCase(new Acorn(x,y));
        return empty;
    }

    @Override
    public Terrain generateBadFood(int x, int y) {
        Terrain empty = generateEmpty(x,y);
        empty.setEntityOnCase(new PoisonousMushroom(x,y));
        return empty;
    }

    @Override
    public Terrain parse(int x, int y,char content) {
        if (content == '@') {
            return generatePlayer(x,y);
        } else if (content == 'E') {
            return generatePrey(x,y);
        } else if (content == 'A') {
            return generateHighTerrain(x,y);
        } else if (content == 'B') {
            return generateLowTerrain(x,y);
        } else if (content == 'G') {
            return generateFood(x,y);
        } else if (content == 'C') {
            return generateMushroom(x,y);
        } else if (content == ' ') {
            return generateEmpty(x,y);
        } else if (content == 'M') {
            return generateBadFood(x,y);
        } else if (content == 'R') {
            return generatePredator1(x,y);
        } else if (content == 'H') {
            return generatePredator2(x,y);
        } else {
            throw new IllegalArgumentException("Le fichier est invalide. (Caractère inconnu)");
        }
    }
}
