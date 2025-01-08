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

/**
 * Classe permettant de générer les entités spécifiques à une jungle.
 */
public class GeneratorJungle extends Generator {

    /**
     * Permet d'obtenir un terrain haut Cocotier.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain haut Cocotier.
     */
    @Override
    public Terrain generateHighTerrain(int x, int y) {
        return new PalmTree(x, y);
    }

    /**
     * Permet d'obtenir un terrain bas Rocher.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain bas Rocher.
     */
    @Override
    public Terrain generateLowTerrain(int x, int y) {
        return new Rock(x, y);
    }

    /**
     * Permet d'obtenir un terrain avec un Singe.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un Singe.
     */
    @Override
    public Terrain generatePrey(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        Monkey prey = new Monkey(x, y);
        Clock.getInstance().attacher(prey);
        empty.setEntityOnCase(prey);
        return empty;
    }

    /**
     * Permet d'obtenir un terrain avec un Serpent.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un Serpent.
     */
    @Override
    public Terrain generatePredator1(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        Snake predator = new Snake(x, y);
        Clock.getInstance().attacher(predator);
        empty.setEntityOnCase(predator);
        return empty;
    }

    /**
     * Permet d'obtenir un terrain avec un Scorpion.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un Scorpion.
     */
    @Override
    public Terrain generatePredator2(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        Scorpio predator = new Scorpio(x, y);
        Clock.getInstance().attacher(predator);
        empty.setEntityOnCase(predator);
        return empty;
    }

    /**
     * Permet d'obtenir un terrain avec une Banane.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec une Banane.
     */
    @Override
    public Terrain generateFood(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        empty.setEntityOnCase(new Banana(x, y));
        return empty;
    }

    /**
     * Permet d'obtenir un terrain avec un champignon hallucinogène.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un champignon hallucinogène.
     */
    @Override
    public Terrain generateBadFood(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        empty.setEntityOnCase(new HallucinogenicMushroom(x, y));
        return empty;
    }

    /**
     * Permet de parser un caractère pour obtenir le terrain correspondant.
     *
     * @param x       La coordonnée x.
     * @param y       La coordonnée y.
     * @param content Le caractère représentant le type de terrain.
     * @return Un terrain correspondant au caractère.
     */
    @Override
    public Terrain parse(int x, int y, char content) {
        if (content == '@') {
            return generatePlayer(x, y);
        } else if (content == 'S') {
            return generatePrey(x, y);
        } else if (content == 'P') {
            return generateHighTerrain(x, y);
        } else if (content == 'R') {
            return generateLowTerrain(x, y);
        } else if (content == 'B') {
            return generateFood(x, y);
        } else if (content == 'C') {
            return generateMushroom(x, y);
        } else if (content == ' ') {
            return generateEmpty(x, y);
        } else if (content == 'M') {
            return generateBadFood(x, y);
        } else if (content == 'E') { // Serpent, j'ai pris la 2ème lettre
            return generatePredator1(x, y);
        } else if (content == 'O') { // Scorpion, j'ai pris la 3ème lettre
            return generatePredator2(x, y);
        } else if (content == '2') {
            return generateTimeStone(x, y, 2);
        } else if (content == '3') {
            return generateTimeStone(x, y, 3);
        } else if (content == '#') {
            return generateWall(x, y);
        } else {
            throw new IllegalArgumentException("Le fichier est invalide. (Caractère inconnu)" + content);
        }
    }
}
