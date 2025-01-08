package modele.entity.movable.character.npc.prey;

import modele.Board;
import modele.InventoryItem;
import modele.clock.Clock;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.prey.TerrifyState;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;

import java.util.List;

/**
 * Classe abstraite représentant une proie dans le jeu.
 */
public abstract class Prey extends NonPlayerCharacter implements InventoryItem {
    /**
     * Le nombre de base de tours avant que la proie ait faim.
     */
    public final int hungryCountBase;
    /**
     * Le nombre de tours restants avant que la proie ait faim.
     */
    protected int hungryCount;
    /**
     * La classe de nourriture préférée de la proie.
     */
    protected Class<? extends Food> foodPreference;
    /**
     * Le niveau d'amitié de la proie avec le joueur.
     */
    protected int friendLevel;
    /**
     * Indique si la proie a bougé pendant ce tour.
     */
    protected boolean hasMoved;
    /**
     * Le niveau de peur de la proie.
     */
    protected int fearLevel;
    /**
     * Le temps passé dans la poche du joueur.
     */
    protected int timeInPocket;


    /**
     * Constructeur de la classe Prey.
     * Initialise la position de la proie et son compteur de faim.
     *
     * @param x               La coordonnée X de la proie.
     * @param y               La coordonnée Y de la proie.
     * @param hungryCountBase Le nombre de base de tours avant que la proie ait faim.
     */
    public Prey(int x, int y, int hungryCountBase) {
        super(x, y);
        this.hungryCountBase = hungryCountBase;
        this.hungryCount = hungryCountBase;
        this.hasMoved = false;
    }

    /**
     * Vérifie si la proie a bougé pendant ce tour.
     *
     * @return true si la proie a bougé, sinon false.
     */
    public boolean isHasMoved() {
        return hasMoved;
    }

    /**
     * Définit si la proie a bougé pendant ce tour.
     *
     * @param hasMoved true si la proie a bougé, sinon false.
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Vérifie si la proie est amicale avec le joueur.
     *
     * @return true si la proie est amicale, sinon false.
     */
    public boolean isFriendly() {
        return friendLevel >= 1;
    }

    /**
     * Obtient le niveau d'amitié de la proie avec le joueur.
     *
     * @return Le niveau d'amitié.
     */
    public int getFriendLevel() {
        return friendLevel;
    }

    /**
     * Définit le niveau d'amitié de la proie avec le joueur.
     *
     * @param value Le nouveau niveau d'amitié.
     */
    public void setFriendLevel(int value) {
        friendLevel = value;
    }

    /**
     * Obtient le niveau de peur de la proie.
     *
     * @return Le niveau de peur.
     */
    public int getFearLevel() {
        return fearLevel;
    }

    /**
     * Définit le niveau de peur de la proie.
     *
     * @param fearLevel Le nouveau niveau de peur.
     */
    public void setFearLevel(int fearLevel) {
        this.fearLevel = fearLevel;
    }

    /**
     * Obtient le temps passé dans la poche du joueur par la proie.
     *
     * @return Le temps passé dans la poche.
     */
    public int getTimeInPocket() {
        return timeInPocket;
    }

    /**
     * Définit le temps passé dans la poche du joueur par la proie.
     *
     * @param timeInPocket Le nouveau temps passé dans la poche.
     */
    public void setTimeInPocket(int timeInPocket) {
        this.timeInPocket = timeInPocket;
    }

    /**
     * Obtient le nombre de tours restants avant que la proie ait faim.
     *
     * @return Le nombre de tours restants.
     */
    public int getHungryCount() {
        return hungryCount;
    }

    /**
     * Définit le nombre de tours restants avant que la proie ait faim.
     *
     * @param hungryCount Le nouveau nombre de tours restants.
     */
    public void setHungryCount(int hungryCount) {
        this.hungryCount = hungryCount;
    }

    /**
     * Obtient la classe de nourriture préférée de la proie.
     *
     * @return La classe de nourriture préférée.
     */
    public Class<? extends Food> getFoodPreference() {
        return foodPreference;
    }

    /**
     * Gère l'alimentation de la proie.
     * Si un joueur est à proximité, le niveau d'amitié augmente.
     *
     * @param isPlayerNearby Indique si un joueur est à proximité.
     * @param food           La nourriture consommée.
     */
    public void eat(boolean isPlayerNearby, Food food) {
        if (isPlayerNearby) {
            if (!isFriendly()) {
                friendLevel++;
                if (isFriendly()) {
                    Board.getInstance().logAction(displayName + " est maintenant un ami");
                }
            }
        }
        hungryCount = hungryCountBase;
    }

    /**
     * Obtient le nom d'affichage de la proie.
     * Si la proie est amicale, ajoute "(Ami)" au nom d'affichage.
     *
     * @return Le nom d'affichage.
     */
    @Override
    public String getDisplayName() {
        if (isFriendly()) {
            return displayName + " (Ami)";
        } else {
            return displayName;
        }
    }

    /**
     * Met à jour l'état de la proie.
     * Décrémente le compteur de faim, déplace la proie si elle n'a pas bougé, et met à jour son état.
     */
    @Override
    public void mettreAJour() {
        if (hungryCount >= 0) {
            --hungryCount; // pour éviter les nombres infiniment négatifs.
        }
        if (!hasMoved) {
            currentState.deplacement();
        }
        currentState.updateState();
        hasMoved = false;
    }

    /**
     * Gère la fuite de la proie lorsqu'elle est attaquée par un prédateur.
     * Déplace la proie vers un terrain sûr et change son état en état de terreur.
     *
     * @param aggressor       Le prédateur attaquant.
     * @param currentPosition La position actuelle de la proie.
     * @param terrainList     La liste des terrains sûrs.
     * @return false si la proie a réussi à fuir, sinon true.
     */
    protected boolean runAway(Predator aggressor, Terrain currentPosition, List<Terrain> terrainList) {
        if (terrainList.contains(currentPosition)) {
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(this, currentPosition));
        } else {
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(this, terrainList.get(0)));
        }
        setCurrentState(new TerrifyState(this));
        aggressor.afterHit(false);
        return false;
    }

    /**
     * Méthode abstraite vérifiant si la proie est protégée par le terrain contre un prédateur.
     *
     * @param terrain  Le terrain actuel.
     * @param predator Le prédateur attaquant.
     * @return true si la proie est protégée, sinon false.
     */
    public abstract boolean isProtected(Terrain terrain, Predator predator);
}
