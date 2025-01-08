package modele.boardFactory.generator;

import modele.entity.movable.character.PlayerCharacter;
import modele.entity.stationary.TimeStone;
import modele.entity.stationary.Wall;
import modele.entity.stationary.food.Mushroom;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe abstraite permettant d'obtenir toutes les entités pour une board.
 */
public abstract class Generator {
    /**
     * Si le PlayerCharacter a été créé.
     */
    private boolean isPlayerCreated = false;

    /**
     * Permet d'obtenir un terrain vide.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain vide.
     */
    public Terrain generateEmpty(int x, int y) {
        return new Empty(x, y);
    }

    /**
     * Permet d'obtenir un terrain avec un PlayerCharacter.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un PlayerCharacter.
     * @throws IllegalArgumentException si un PlayerCharacter a déjà été créé.
     */
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

    /**
     * Permet d'obtenir un terrain haut.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain haut.
     */
    public abstract Terrain generateHighTerrain(int x, int y);

    /**
     * Permet d'obtenir un terrain bas.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain bas.
     */
    public abstract Terrain generateLowTerrain(int x, int y);

    /**
     * Permet d'obtenir un terrain avec une proie.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec une proie.
     */
    public abstract Terrain generatePrey(int x, int y);

    /**
     * Permet d'obtenir un terrain avec un premier type de prédateur.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un premier type de prédateur.
     */
    public abstract Terrain generatePredator1(int x, int y);

    /**
     * Permet d'obtenir un terrain avec un deuxième type de prédateur.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un deuxième type de prédateur.
     */
    public abstract Terrain generatePredator2(int x, int y);

    /**
     * Permet d'obtenir un terrain avec de la nourriture.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec de la nourriture.
     */
    public abstract Terrain generateFood(int x, int y);

    /**
     * Permet d'obtenir un terrain avec de la mauvaise nourriture.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec de la mauvaise nourriture.
     */
    public abstract Terrain generateBadFood(int x, int y);

    /**
     * Permet d'obtenir un terrain avec un champignon.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un champignon.
     */
    public Terrain generateMushroom(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        empty.setEntityOnCase(new Mushroom(x, y));
        return empty;
    }

    /**
     * Permet d'obtenir un terrain avec une pierre de temps.
     *
     * @param x     La coordonnée x.
     * @param y     La coordonnée y.
     * @param turns Le nombre de tours que la pierre annule.
     * @return Un terrain avec une pierre de temps.
     */
    public Terrain generateTimeStone(int x, int y, int turns) {
        Terrain empty = generateEmpty(x, y);
        empty.setEntityOnCase(new TimeStone(x, y, turns));
        return empty;
    }

    /**
     * Permet d'obtenir un terrain avec un mur.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @return Un terrain avec un mur.
     */
    public Terrain generateWall(int x, int y) {
        Terrain empty = generateEmpty(x, y);
        empty.setEntityOnCase(new Wall(x, y));
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
    public abstract Terrain parse(int x, int y, char content);
}
