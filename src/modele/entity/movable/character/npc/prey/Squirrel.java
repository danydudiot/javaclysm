package modele.entity.movable.character.npc.prey;

import exception.InvalidActionException;
import modele.Board;
import modele.Inventory;
import modele.clock.Clock;
import modele.clock.commands.FriendInInventoryCommand;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Fox;
import modele.entity.movable.character.npc.predator.Owl;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.prey.SquirrelJunkieState;
import modele.entity.movable.character.npc.state.prey.SquirrelNotHungryState;
import modele.entity.stationary.food.Acorn;
import modele.entity.stationary.food.BadFood;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.High;
import modele.entity.stationary.terrain.low.Low;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un écureuil dans le jeu.
 */
public class Squirrel extends Prey {
    /**
     * Constructeur de la classe Squirrel.
     * Initialise la position de l'écureuil, son état initial, sa représentation visuelle, son nom d'affichage et sa préférence alimentaire.
     *
     * @param x La coordonnée X de l'écureuil.
     * @param y La coordonnée Y de l'écureuil.
     */
    public Squirrel(int x, int y) {
        super(x, y, 5);
        this.representation = "E";
        this.displayName = "Écureuil";
        this.foodPreference = Acorn.class;
        this.setCurrentState(new SquirrelNotHungryState(this));
    }

    /**
     * Gère l'attaque d'un personnage sur l'écureuil.
     * Si l'agresseur est un personnage joueur, le niveau d'amitié est réinitialisé.
     * Si l'agresseur est un prédateur, l'écureuil tente de s'enfuir ou est tué.
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
            // Ici on utilise getNear car on a besoin de la case actuelle.
            List<Terrain> neighbours = Board.getInstance().getNear(getX(), getY(), 1);
            PlayerCharacter player = null;
            List<Terrain> high = new ArrayList<>();
            List<Terrain> low = new ArrayList<>();

            for (Terrain terrain : neighbours) {
                if (terrain.getEntityOnCase() instanceof PlayerCharacter) {
                    player = (PlayerCharacter) terrain.getEntityOnCase();
                } else if (terrain instanceof High) {
                    high.add(terrain);
                } else if (terrain instanceof Low) {
                    low.add(terrain);
                }
            }

            if (player != null && isFriendly() && !Inventory.getInstance().isFull()) {
                Clock.getInstance().addCommandToTurn(new FriendInInventoryCommand(this));
                return false;
            } else if (!high.isEmpty() && predator instanceof Fox fox) {
                return runAway(fox, currentPosition, high);

            } else if (!low.isEmpty() && predator instanceof Owl owl) {
                return runAway(owl, currentPosition, low);

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
     * Vérifie si l'écureuil est protégé par le terrain contre un prédateur.
     *
     * @param terrain  Le terrain actuel.
     * @param predator Le prédateur attaquant.
     * @return true si l'écureuil est protégé, sinon false.
     */
    public boolean isProtected(Terrain terrain, Predator predator) {
        return ((terrain instanceof High && predator instanceof Fox) ||
                (terrain instanceof Low && predator instanceof Owl));
    }

    /**
     * Gère l'alimentation de l'écureuil.
     * Si l'écureuil mange de la mauvaise nourriture, son état change.
     *
     * @param isPlayerNearby Indique si un joueur est à proximité.
     * @param food           La nourriture consommée.
     */
    @Override
    public void eat(boolean isPlayerNearby, Food food) {
        super.eat(isPlayerNearby, food);
        if (food instanceof BadFood) {
            setCurrentState(new SquirrelJunkieState(this));
        }
    }
}