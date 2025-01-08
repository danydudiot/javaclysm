package modele.boardFactory.generator;

import modele.clock.Clock;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.predator.Snake;
import modele.entity.movable.character.npc.prey.Monkey;
import modele.entity.stationary.food.Banana;
import modele.entity.stationary.food.HallucinogenicMushroom;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.PalmTree;
import modele.entity.stationary.terrain.low.Rock;

public class GeneratorJungle extends Generator{

    @Override
    public Terrain generateHighTerrain(int x, int y) {
        return new PalmTree(x,y);
    }

    @Override
    public Terrain generateLowTerrain(int x, int y) {
        return new Rock(x,y);
    }

    @Override
    public Terrain generatePrey(int x, int y) {
        Terrain empty = generateEmpty(x,y);
        Monkey prey = new Monkey(x,y);
        Clock.getInstance().attacher(prey);
        empty.setEntityOnCase(prey);
        return empty;
    }

    @Override
    public Terrain generatePredator1(int x, int y) {
        Terrain empty = generateEmpty(x,y);
        Snake predator = new Snake(x,y);
        Clock.getInstance().attacher(predator);
        empty.setEntityOnCase(predator);
        return empty;
    }

    @Override
    public Terrain generatePredator2(int x, int y) {
        Terrain empty = generateEmpty(x,y);
        Scorpio predator = new Scorpio(x,y);
        Clock.getInstance().attacher(predator);
        empty.setEntityOnCase(predator);
        return empty;
    }

    @Override
    public Terrain generateFood(int x, int y) {
        Terrain empty = generateEmpty(x,y);
        empty.setEntityOnCase(new Banana(x,y));
        return empty;
    }

    @Override
    public Terrain generateBadFood(int x, int y) {
        Terrain empty = generateEmpty(x,y);
        empty.setEntityOnCase(new HallucinogenicMushroom(x,y));
        return empty;
    }

    @Override
    public Terrain parse(int x, int y,char content) {
        if (content == '@') {
            return generatePlayer(x,y);
        } else if (content == 'S') {
            return generatePrey(x,y);
        } else if (content == 'P') {
            return generateHighTerrain(x,y);
        } else if (content == 'R') {
            return generateLowTerrain(x,y);
        } else if (content == 'B') {
            return generateFood(x,y);
        } else if (content == 'C') {
            return generateMushroom(x,y);
        } else if (content == ' ') {
            return generateEmpty(x,y);
        } else if (content == 'M') {
            return generateBadFood(x,y);
        } else if (content == 'E') { // Serpent, j'ai pris la 2ème lettre
            return generatePredator1(x,y);
        } else if (content == 'O') { // Scorpion, j'ai pris la 3ème lettre
            return generatePredator2(x,y);
        } else if (content == '2') {
            return generateTimeStone(x,y,2);
        } else if (content == '3') {
            return generateTimeStone(x,y,3);
        } else if (content == '#') {
            return generateWall(x,y);
        } else {
            throw new IllegalArgumentException("Le fichier est invalide. (Caractère inconnu)" + content);
        }
    }
}
