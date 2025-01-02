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
        List<Terrain> neighbours = Board.getInstance().getNear(prey.getX(), prey.getY(), 1);
        List<Terrain> casePossible = new ArrayList<>();


        for (Terrain terrain : neighbours) {
            if (canMove(terrain) && !terrain.equals(forbidden)) {
                casePossible.add(terrain);
            }
        }
        if (casePossible.isEmpty()){
            Terrain terrain = Board.getInstance().getAt(prey.getX(), prey.getY());
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, terrain));
            return terrain;
        } else {
            Terrain terrain = casePossible.get((int) (Math.random() * casePossible.size()));
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, terrain));
            return terrain;
        }
    }

    protected boolean getFood(){
        List<Terrain> neighbours = Board.getInstance().getNear(prey.getX(), prey.getY(), 1);
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
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, high.get(0)));
        } else if (!low.isEmpty()) {
            Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, low.get(0)));
        } else {
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




/*



protected char getDefault(String allow){
    int[] position = prey.getPosition();
    Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
    String directionPossible = "";
    for (char direction : neighbours.keySet()) {
        if (canMove(direction) && allow.contains(String.valueOf(direction))) {
            directionPossible += direction;
        }
    }
    if (directionPossible.isEmpty()){
        Clock.getInstance().addCommandToTurn(new PreyMoveCommand(prey, 'a'));
        return 'a';
    } else {
        char move = directionPossible.charAt((int) (Math.random() * directionPossible.length()));
        Clock.getInstance().addCommandToTurn(new PreyMoveCommand(prey, move));
        return move;
    }

}

protected boolean getFood(){
    int[] position = prey.getPosition();
    Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
    String food = "";
    for (char a : neighbours.keySet()) {
        Entity entity = neighbours.get(a).getEntityOnCase();
        if (entity instanceof Food) {
            if (entity.getClass() == prey.getFoodPreference()) {
                food = a + food;
            } else {
                food += a;
            }
        }
    }
    if (food.isEmpty()) {
        return false;
    } else {
        Food foodEntity = (Food) neighbours.get(food.charAt(0)).getEntityOnCase();
        Clock.getInstance().addCommandToTurnBefore(new PreyEatCommand(prey, foodEntity));
        return true;
    }
}


protected boolean getDanger(boolean playerAllow){
    int[] position = prey.getPosition();
    List<Terrain> around = Board.getInstance().getNear(position[0], position[1], 4);
    List<Predator> danger = new ArrayList<>();
    for (Terrain terrain : around){
        if (terrain.getEntityOnCase() instanceof Predator && !(terrain instanceof Rock && terrain.getEntityOnCase() instanceof Scorpio)){
            danger.add((Predator) terrain.getEntityOnCase());
        }
    }

    if (danger.isEmpty()){
        return false;
    }

    Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(prey.getX(), prey.getY());
    char player = ' ';
    String high = "";
    String low = "";
    String free = "";
    for (char direction: neighbours.keySet()){
        Terrain terrain = neighbours.get(direction);
        if (terrain.getEntityOnCase() instanceof PlayerCharacter){
            player = direction;
        } else if (terrain instanceof High && terrain.getEntityOnCase() == null){
            high += direction;
        } else if (terrain instanceof Low && terrain.getEntityOnCase() == null){
            low += direction;
        } else if (terrain.getEntityOnCase() == null) {
            free += direction;
        }
    }


    if (playerAllow && player != ' ' && prey.isFriendly() && !Inventory.getInstance().isFull()){
        Clock.getInstance().addCommandToTurn(new FriendInInventoryCommand((Squirrel) prey));
    } else if (!high.isEmpty()) {
        Clock.getInstance().addCommandToTurn(new PreyMoveCommand(prey, high.charAt(0)));
    } else if (!low.isEmpty()) {
        Clock.getInstance().addCommandToTurn(new PreyMoveCommand(prey, low.charAt(0)));
    } else {
        Clock.getInstance().addCommandToTurn(new PreyMoveCommand(prey, getDirectionFromDanger(danger, free)));
    }
    return true;
}



protected char getDirectionFromDanger(List<Predator> danger, String allow){
    if (allow.isEmpty()){
        return 'a';
    }
    float somme_x = 0;
    float somme_y = 0;
    for (Entity entity : danger){
        int[] position = entity.getPosition();
        somme_x += position[0];
        somme_y += position[1];
    }

    float moyenne_x = somme_x / danger.size();
    float moyenne_y = somme_y / danger.size();

    Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(prey.getX(), prey.getY());
    Map<Character, Double> distance = new HashMap<>();

    distance.put('z' ,
            Math.sqrt(
                    Math.pow(
                            neighbours.get('z').getX() - moyenne_x,2
                    ) + Math.pow(
                            neighbours.get('z').getY() - moyenne_y,2)
            )
    );
    distance.put('q' ,
            Math.sqrt(
                    Math.pow(
                            neighbours.get('q').getX() - moyenne_x,2
                    ) + Math.pow(
                            neighbours.get('q').getY() - moyenne_y,2)
            )
    );
    distance.put('s' ,
            Math.sqrt(
                    Math.pow(
                            neighbours.get('s').getX() - moyenne_x,2
                    ) + Math.pow(
                            neighbours.get('s').getY() - moyenne_y,2)
            )
    );
    distance.put('d' ,
            Math.sqrt(
                    Math.pow(
                            neighbours.get('d').getX() - moyenne_x,2
                    ) + Math.pow(
                            neighbours.get('d').getY() - moyenne_y,2)
            )
    );

    char maxi = allow.charAt(0);
    for (char direction : distance.keySet()) {
        if (distance.get(maxi) < distance.get(direction) && allow.contains(String.valueOf(direction))) {
            maxi = direction;
        }
    }
    return maxi;
}

 */