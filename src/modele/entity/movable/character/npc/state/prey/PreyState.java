package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.Inventory;
import modele.clock.Clock;
import modele.clock.commands.PreyEatCommand;
import modele.clock.commands.FriendInInventoryCommand;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.Entity;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.High;
import modele.entity.stationary.terrain.low.Low;
import modele.entity.stationary.terrain.low.Rock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe abstraite représentant l'état d'une proie.
 */
public abstract class PreyState implements State {
    /**
     * La proie.
     */
    protected Prey prey;

    /**
     * Constructeur de la classe PreyState.
     *
     * @param prey Le personnage proie associé à cet état.
     */
    public PreyState(Prey prey) {
        this.prey = prey;
    }

    /**
     * Vérifie si le personnage proie peut se déplacer sur le terrain spécifié.
     *
     * @param terrain Le terrain à vérifier.
     * @return true si le personnage proie peut se déplacer sur le terrain, sinon false.
     */
    @Override
    public boolean canMove(Terrain terrain) {
        return terrain != null && terrain.isEmpty();
    }

    /**
     * Obtient un terrain par défaut pour le déplacement, en évitant un terrain interdit.
     *
     * @param forbidden Le terrain interdit.
     * @return Le terrain actuel.
     */
    protected Terrain getDefault(Terrain forbidden) {
        List<Terrain> neighbours = Board.getInstance().getNeighbours(prey.getX(), prey.getY());
        List<Terrain> casePossible = new ArrayList<>();


        for (Terrain terrain : neighbours) { // Identification des Terrains possibles.
            if (canMove(terrain) && (forbidden == null || !forbidden.equals(terrain))) {
                casePossible.add(terrain);
            }
        }
        Terrain current = Board.getInstance().getAt(prey.getX(), prey.getY());

        if (casePossible.isEmpty()) {
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, current));
        } else {
            Terrain terrain = casePossible.get((int) (Math.random() * casePossible.size()));
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, terrain));
        }
        return current;
    }

    /**
     * Vérifie s'il y a de la nourriture à proximité et ajoute une commande pour manger si c'est le cas.
     *
     * @return true s'il y a de la nourriture à proximité, sinon false.
     */
    protected boolean getFood() {
        List<Terrain> neighbours = Board.getInstance().getNeighbours(prey.getX(), prey.getY());
        List<Terrain> casePossible = new ArrayList<>();

        for (Terrain terrain : neighbours) { // Cherche de la nourriture.
            Entity entity = terrain.getEntityOnCase();
            if (entity instanceof Food) {
                if (entity.getClass() == prey.getFoodPreference()) {
                    casePossible.add(0, terrain);
                } else {
                    casePossible.add(terrain);
                }
            }
        }
        if (casePossible.isEmpty()) {
            return false;
        } else {
            Food foodEntity = (Food) casePossible.get(0).getEntityOnCase();
            Clock.getInstance().addCommandToTurnBefore(new PreyEatCommand(prey, foodEntity));
            return true;
        }
    }

    /**
     * Vérifie s'il y a un danger à proximité et ajoute une commande pour se déplacer en conséquence.
     *
     * @param playerAllow Indique si le joueur est autorisé à interagir.
     * @return true s'il y a un danger à proximité, sinon false.
     */
    protected boolean getDanger(boolean playerAllow) {
        List<Terrain> around = Board.getInstance().getNear(prey.getX(), prey.getY(), 4);
        List<Predator> danger = new ArrayList<>();
        for (Terrain terrain : around) { // Détection du danger.
            if (terrain.getEntityOnCase() instanceof Predator predator && !(terrain instanceof Rock && predator instanceof Scorpio)) {
                danger.add(predator);
            }
        }

        if (danger.isEmpty()) {
            return false;
        }

        // Ici, on utilise getNear, car on souhaite avoir la case actuelle.
        List<Terrain> neighbours = Board.getInstance().getNear(prey.getX(), prey.getY(), 1);
        PlayerCharacter player = null;
        List<Terrain> high = new ArrayList<>();
        List<Terrain> low = new ArrayList<>();
        List<Terrain> free = new ArrayList<>();


        for (Terrain terrain : neighbours) { // Identification des Terrains voisins.
            if (terrain.getEntityOnCase() instanceof PlayerCharacter playerCharacter) {
                player = playerCharacter;
            } else if (terrain instanceof High && (terrain.getEntityOnCase() == null || terrain.getEntityOnCase() == prey)) {
                high.add(terrain);
            } else if (terrain instanceof Low && (terrain.getEntityOnCase() == null || terrain.getEntityOnCase() == prey)) {
                low.add(terrain);
            } else if (terrain.getEntityOnCase() == null || terrain.getEntityOnCase() == prey) {
                free.add(terrain);
            }
        }


        if (playerAllow && player != null && prey.isFriendly() && !Inventory.getInstance().isFull()) { // Choix d'action
            Clock.getInstance().addCommandToTurn(new FriendInInventoryCommand(prey));
        } else if (!high.isEmpty()) {
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, high.get(0)));
        } else if (!low.isEmpty()) {
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, low.get(0)));
        } else {
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, getDirectionFromDanger(danger, free)));
        }
        return true;
    }


    /**
     * Obtient la direction pour s'éloigner du danger.
     *
     * @param danger La liste des prédateurs dangereux.
     * @param allow  La liste des terrains autorisés pour le déplacement.
     * @return Le terrain vers lequel se déplacer pour s'éloigner du danger.
     */
    protected Terrain getDirectionFromDanger(List<Predator> danger, List<Terrain> allow) {
        float somme_x = 0;
        float somme_y = 0;
        for (Entity entity : danger) { // Somme des coordonnées des prédateurs.
            somme_x += entity.getX();
            somme_y += entity.getY();
        }

        float moyenne_x = somme_x / danger.size();
        float moyenne_y = somme_y / danger.size();

        Map<Terrain, Double> distance = new HashMap<>();

        for (Terrain terrain : allow) { // Calcule de la distance des Terrains possibles au danger.
            distance.put(terrain,
                    Math.sqrt(
                            Math.pow(
                                    terrain.getX() - moyenne_x, 2
                            ) + Math.pow(
                                    terrain.getY() - moyenne_y, 2)
                    )
            );
        }

        Terrain maxi = allow.get(0);
        for (Terrain terrain : distance.keySet()) { // Choix du Terrain le plus loin.
            if (distance.get(maxi) < distance.get(terrain)) {
                maxi = terrain;
            }
        }
        return maxi;
    }
}