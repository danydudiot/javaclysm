package modele.entity.movable.character.npc.prey;

import exception.InvalidActionException;
import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.FriendInInventoryCommand;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Snake;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.prey.MonkeyJunkieState;
import modele.entity.movable.character.npc.state.prey.MonkeyNotHungryState;
import modele.entity.stationary.food.BadFood;
import modele.entity.stationary.food.Banana;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.High;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un singe dans le jeu.
 */
public class Monkey extends Prey {
    /**
     * Indique si le singe à vue des prédateurs.
     */
    private boolean seeSomePredators;

    /**
     * Constructeur de la classe Monkey.
     * Initialise la position du singe, son état initial, sa représentation visuelle, son nom d'affichage et sa préférence alimentaire.
     *
     * @param x La coordonnée X du singe.
     * @param y La coordonnée Y du singe.
     */
    public Monkey(int x, int y) {
        super(x, y, 3);
        this.representation = "S";
        this.displayName = "Singe";
        this.foodPreference = Banana.class;
        this.setCurrentState(new MonkeyNotHungryState(this));
        this.seeSomePredators = false;
    }

    /**
     * Vérifie si le singe est amical avec le joueur.
     *
     * @return true si le singe est amical, sinon false.
     */
    @Override
    public boolean isFriendly() {
        return friendLevel >= 2;
    }

    /**
     * Est-ce que le singe à vue un prédateur.
     *
     * @return true si le singe à vue des prédateurs, sinon false.
     */
    public boolean isSeeSomePredators() {
        return seeSomePredators;
    }

    /**
     * Définit si le singe à vue des prédateurs.
     *
     * @param seeSomePredators true si le singe à vue des prédateurs, sinon false.
     */
    public void setSeeSomePredators(boolean seeSomePredators) {
        this.seeSomePredators = seeSomePredators;
    }

    /**
     * Gère l'alimentation de l'écureuil.
     * Si le singe mange de la mauvaise nourriture, son état change.
     *
     * @param isPlayerNearby Indique si un joueur est à proximité.
     * @param food           La nourriture consommée.
     */
    @Override
    public void eat(boolean isPlayerNearby, Food food) {
        boolean wasFriendly = isFriendly();
        super.eat(isPlayerNearby, food);

        if (!wasFriendly && isFriendly()) {
            Clock.getInstance().addCommandToTurn(new FriendInInventoryCommand(this));
        } else if (food instanceof BadFood) {
            setCurrentState(new MonkeyJunkieState(this));
        }
    }

    /**
     * Gère l'attaque d'un personnage sur le singe.
     * Si l'agresseur est un personnage joueur, le niveau d'amitié est réinitialisé.
     * Si l'agresseur est un prédateur, le singe tente de s'enfuir ou est tué.
     *
     * @param aggressor Le personnage attaquant.
     * @return true si l'attaque est réussie, sinon false.
     * @throws InvalidActionException si l'attaque est invalide.
     */
    @Override
    public boolean hit(Character aggressor) {
        if (aggressor instanceof PlayerCharacter) {
            friendLevel = 0;
            return true;
        } else if (aggressor instanceof Predator predator) {
            Terrain currentPosition = Board.getInstance().getAt(getX(), getY());
            // Ici on utilise getNear plutôt que getNeighbours car getNear inclue la case actuelle.
            List<Terrain> neighbours = Board.getInstance().getNear(getX(), getY(), 1);
            List<Terrain> high = new ArrayList<>();

            for (Terrain terrain : neighbours) {
                if (terrain instanceof High) {
                    high.add(terrain);
                }
            }


            if (!high.isEmpty() && predator instanceof Snake snake) {
                return runAway(snake, currentPosition, high);
            } else {
                Board.getInstance().getAt(x, y).clearEntityOnCase();
                this.setCurrentState(new DeadState(this));
                predator.afterHit(true);
                return true;
            }

        } else {
            throw new InvalidActionException("Vous ne pouvez pas frapper l'animal");
        }
    }

    /**
     * Vérifie si le singe est protégé par le terrain contre un prédateur.
     *
     * @param terrain  Le terrain actuel.
     * @param predator Le prédateur attaquant.
     * @return true si le singe est protégé, sinon false.
     */
    public boolean isProtected(Terrain terrain, Predator predator) {
        return terrain instanceof High && predator instanceof Snake;
    }

    /**
     * Tente de crier pour alerter de la présence de prédateurs.
     * Parcourt les terrains autour du singe pour détecter des prédateurs.
     * Si des prédateurs sont détectés et que le singe est amical et n'a pas encore vu de prédateurs,
     * il crie pour alerter. Sinon, il réinitialise l'état de détection des prédateurs.
     */
    public void tryYelling() {
        List<Terrain> around = Board.getInstance().getNear(getX(), getY(), 4);
        List<Predator> danger = new ArrayList<>();
        for (Terrain terrain : around) {
            if (terrain.getEntityOnCase() instanceof Predator predator) {
                danger.add(predator);
            }
        }

        if (!danger.isEmpty() && isFriendly() && !(isSeeSomePredators())) {
            setSeeSomePredators(true);
            Board.getInstance().logAction("(trad: je décele la présences de prédateurs.)");
            Board.getInstance().logError("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH");
        } else if (danger.isEmpty()) {
            setSeeSomePredators(false);
        }
    }

}