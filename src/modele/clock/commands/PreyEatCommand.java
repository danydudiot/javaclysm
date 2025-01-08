package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;
import modele.entity.movable.character.npc.state.prey.FriendInInventoryState;
import modele.entity.stationary.food.Food;

/**
 * Commande représentant l'action d'une proie mangeant de la nourriture.
 */
public class PreyEatCommand implements Command {
    /**
     * La nourriture que la proie va manger.
     */
    private Food food;
    /**
     * L'ancien état de la proie.
     */
    private State old_state;
    /**
     * Le niveau d'amitié précédent de la proie.
     */
    private int old_friendlyLevel;
    /**
     * Le niveau de faim précédent de la proie.
     */
    private int old_hungryCount;
    /**
     * L'ancienne position X de la proie.
     */
    private int old_x;
    /**
     * L'ancienne position Y de la proie.
     */
    private int old_y;
    /**
     * Indique si le joueur est à proximité.
     */
    private boolean isPlayerNearby;
    /**
     * La proie qui mange.
     */
    private Prey prey;

    /**
     * Constructeur de la classe PreyEatCommand.
     * Initialise les attributs de la commande.
     *
     * @param prey La proie qui mange.
     * @param food La nourriture que la proie va manger.
     */
    public PreyEatCommand(Prey prey, Food food) {
        this.prey = prey;
        this.old_state = prey.getCurrentState();
        this.food = food;
        this.old_x = prey.getX();
        this.old_y = prey.getY();
        this.isPlayerNearby = food.isPlayerNearby();
        this.old_hungryCount = prey.getHungryCount();
        this.old_friendlyLevel = prey.getFriendLevel();
    }

    /**
     * Exécute la commande pour que la proie mange la nourriture.
     */
    @Override
    public void doCommand() {
        prey.eat(isPlayerNearby, food);
        Board.getInstance().clearCase(food.getX(), food.getY());
        if (!(prey.getCurrentState() instanceof FriendInInventoryState)) {
            Board.getInstance().moveTo(prey, food.getX(), food.getY());
        } else {
            prey.setPosition(food.getX(), food.getY());
        }
        prey.setHasMoved(true);
    }

    /**
     * Commande représentant l'action de déplacer un prédateur vers une nouvelle coordonnée.
     */
    @Override
    public void undoCommand() {
        prey.setFriendLevel(old_friendlyLevel);
        prey.setHungryCount(old_hungryCount);
        if (prey.getCurrentState() instanceof FriendInInventoryState) {
            Board.getInstance().setEntityOnCase(food.getX(), food.getY(), prey);
        }
        Board.getInstance().moveTo(prey, old_x, old_y);
        prey.setCurrentState(old_state);
        Board.getInstance().setEntityOnCase(food.getX(), food.getY(), food);
    }
}
