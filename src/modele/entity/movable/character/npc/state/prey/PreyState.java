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

public abstract class PreyState implements State {
    protected Prey prey;

    public PreyState(Prey prey) {
        this.prey = prey;
    }

    @Override
    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(prey.getX(), prey.getY(), direction);
        return target != null && target.isEmpty();
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return terrain != null && terrain.isEmpty();
    }

    protected Terrain getDefault(Terrain forbidden){
        List<Terrain> neighbours = Board.getInstance().getNeighbours(prey.getX(), prey.getY());
        List<Terrain> casePossible = new ArrayList<>();


        for (Terrain terrain : neighbours) {
            if (canMove(terrain) && !terrain.equals(forbidden)) {
                casePossible.add(terrain);
            }
        }
        if (casePossible.isEmpty()){
            Terrain terrain = Board.getInstance().getAt(prey.getX(), prey.getY());
            System.out.println("getDefaultPrey1");
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, terrain));
            return terrain;
        } else {
            Terrain terrain = casePossible.get((int) (Math.random() * casePossible.size()));
            System.out.println("getDefaultPrey2");
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, terrain));
            return terrain;
        }
    }

    protected boolean getFood(){
        List<Terrain> neighbours = Board.getInstance().getNeighbours(prey.getX(), prey.getY());
        List<Terrain> casePossible = new ArrayList<>();

        for (Terrain terrain : neighbours) {
            Entity entity = terrain.getEntityOnCase();
            if (entity instanceof Food) {
                if (entity.getClass() == prey.getFoodPreference()) {
                    casePossible.add(0,terrain);
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


    protected boolean getDanger(boolean playerAllow){
        List<Terrain> around = Board.getInstance().getNear(prey.getX(), prey.getY(), 4);
        List<Predator> danger = new ArrayList<>();
        for (Terrain terrain : around){
            if (terrain.getEntityOnCase() instanceof Predator && !(terrain instanceof Rock && terrain.getEntityOnCase() instanceof Scorpio)){
                danger.add((Predator) terrain.getEntityOnCase());
            }
        }

        if (danger.isEmpty()){
            return false;
        }

        // Ici on utilise getNear, car on souhaite avoir la case actuelle.
        List<Terrain> neighbours = Board.getInstance().getNear(prey.getX(), prey.getY(), 1);
        PlayerCharacter player = null;
        List<Terrain> high = new ArrayList<>();
        List<Terrain> low = new ArrayList<>();
        List<Terrain> free = new ArrayList<>();


        for (Terrain terrain : neighbours){
            if (terrain.getEntityOnCase() instanceof PlayerCharacter){
                player = (PlayerCharacter) terrain.getEntityOnCase();
            } else if (terrain instanceof High && (terrain.getEntityOnCase() == null || terrain.getEntityOnCase() == prey)){
                high.add(terrain);
            } else if (terrain instanceof Low && (terrain.getEntityOnCase() == null || terrain.getEntityOnCase() == prey)){
                low.add(terrain);
            } else if (terrain.getEntityOnCase() == null || terrain.getEntityOnCase() == prey) {
                free.add(terrain);
            }
        }


        if (playerAllow && player != null && prey.isFriendly() && !Inventory.getInstance().isFull()){
            Clock.getInstance().addCommandToTurn(new FriendInInventoryCommand(prey));
        } else if (!high.isEmpty()) {
            System.out.println("getDangerPrey1");
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, high.get(0)));
        } else if (!low.isEmpty()) {
            System.out.println("getDangerPrey2");
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, low.get(0)));
        } else {
            System.out.println("getDangerPrey3");
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, getDirectionFromDanger(danger, free)));
        }
        return true;
    }



    protected Terrain getDirectionFromDanger(List<Predator> danger, List<Terrain> allow){
        float somme_x = 0;
        float somme_y = 0;
        for (Entity entity : danger){
            int[] position = entity.getPosition();
            somme_x += position[0];
            somme_y += position[1];
        }

        float moyenne_x = somme_x / danger.size();
        float moyenne_y = somme_y / danger.size();




        Map<Terrain, Double> distance = new HashMap<>();

        for (Terrain terrain : allow){
            distance.put(terrain ,
                    Math.sqrt(
                            Math.pow(
                                    terrain.getX() - moyenne_x,2
                            ) + Math.pow(
                                    terrain.getY() - moyenne_y,2)
                    )
            );
        }

        Terrain maxi = allow.get(0);
        for (Terrain terrain : distance.keySet()) {
            if (distance.get(maxi) < distance.get(terrain)) {
                maxi = terrain;
            }
        }
        return maxi;
    }
}