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

/**
 * Classe permettant de générer les entités spécifiques à une forêt.
 */
public class GeneratorForest extends Generator {

    /**
     * Permet d'obtenir un terrain Arbre.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain Arbre.
     */
    @Override
    public Terrain generateHighTerrain(int x, int y) {
        return new Tree(x, y);
    }

    /**
     * Permet d'obtenir un terrain bas Buisson.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain bas Buisson.
     */
    @Override
    public Terrain generateLowTerrain(int x, int y) {
        return new Bush(x, y);
    }

    /**
     * Permet d'obtenir un terrain avec un Écureuil.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un Écureuil.
     */
    @Override
    public Terrain generatePrey(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        Squirrel prey = new Squirrel(x, y);
        Clock.getInstance().attacher(prey);
        empty.setEntityOnCase(prey);
        return empty;
    }

    /**
     * Permet d'obtenir un terrain avec un Renard.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un Renard.
     */
    @Override
    public Terrain generatePredator1(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        Fox predator = new Fox(x, y);
        Clock.getInstance().attacher(predator);
        empty.setEntityOnCase(predator);
        return empty;
    }

    /**
     * Permet d'obtenir un terrain avec un Hibou.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un Hibou.
     */
    @Override
    public Terrain generatePredator2(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        Owl predator = new Owl(x, y);
        Clock.getInstance().attacher(predator);
        empty.setEntityOnCase(predator);
        return empty;
    }

    /**
     * Permet d'obtenir un terrain avec un Gland.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un Gland.
     */
    @Override
    public Terrain generateFood(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        empty.setEntityOnCase(new Acorn(x, y));
        return empty;
    }

    /**
     * Permet d'obtenir un terrain avec un champignon vénéneux.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un champignon vénéneux.
     */
    @Override
    public Terrain generateBadFood(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        empty.setEntityOnCase(new PoisonousMushroom(x, y));
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
        } else if (content == 'E') {
            return generatePrey(x, y);
        } else if (content == 'A') {
            return generateHighTerrain(x, y);
        } else if (content == 'B') {
            return generateLowTerrain(x, y);
        } else if (content == 'G') {
            return generateFood(x, y);
        } else if (content == 'C') {
            return generateMushroom(x, y);
        } else if (content == ' ') {
            return generateEmpty(x, y);
        } else if (content == 'M') {
            return generateBadFood(x, y);
        } else if (content == 'R') {
            return generatePredator1(x, y);
        } else if (content == 'H') {
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
