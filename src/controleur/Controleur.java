package controleur;

import exception.InvalidActionException;
import exception.MoveInvalidException;
import modele.Board;
import modele.BoardFactory;
import modele.Inventory;
import modele.clock.Clock;
import modele.entity.Entity;
import modele.entity.movable.character.PlayerCharacter;
import modele.interaction.Interactible;
import modele.interaction.Interaction;
import vue.Ihm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Controleur {
    protected Ihm ihm;
    protected Board board;
    protected PlayerCharacter playerCharacter;

    public void setPlayerCharacter(PlayerCharacter playerCharacter) {
        System.out.println("HERE");
        this.playerCharacter = playerCharacter;
    }

    protected Clock clock;
    protected Inventory inventory;

    public Controleur() {
        this.ihm = new Ihm();
        this.clock = new Clock();
        this.inventory = new Inventory();
    }

    public void startGame(){

        if (ihm.askBoard()){
            try{
                this.board = new BoardFactory(ihm.askFile(), clock).makeBoard();
            } catch (FileNotFoundException exception) {
                ihm.displayError("Le fichier indiqué n'a pas été trouver.");
                startGame();
                return;
            } catch (IllegalArgumentException exception) {
                ihm.displayError("Le fichier indiqué comporte des erreurs.");
                startGame();
                return;
            }
        } else {
            this.board = new Board(clock);
        }
        playerCharacter = board.getPlayer();
        game();
    }


    private void game() {
        while (true){
            tour();
        }
    }


    private void tour(){
        ihm.display(board.getBoardAsList(), board.getHeight(), board.getWidth(), board.peekAtLogs(3), playerCharacter.getPosition()[0], playerCharacter.getPosition()[1], playerCharacter.getOrientation(), inventory.getEquippedItem());
        char action = ihm.askAction();
        try{
            if ("zqsd".indexOf(action) != -1){
                 playerCharacter.move(action, board);
            } else if ("oklm".indexOf(action) != -1) {
                playerCharacter.changeOrientation(action);
            } else if ("i".indexOf(action) != -1) {
                System.out.println(inventory);
            } else if ("e".indexOf(action) != -1) {
                interation();
            } else if ("j".indexOf(action) != -1) {
                inventory.dropItem(board);
            } else {
                throw new InvalidActionException("Action inconnue.");
            }
        } catch (InvalidActionException | MoveInvalidException e) {
            ihm.displayError(e.getMessage());
            tour();
            return;
        }
        clock.notifierObservateur(board);
    }


    private void interation(){
        int[] position = playerCharacter.getTarget();
        Entity entity = board.getAt(position[0],position[1]).getEntityOnCase();
        if (entity instanceof Interactible interactible){
            // Transtypage par pattern variable.
            Interaction[] interactions = interactible.getInteractions();
            List<String> interactions_string = new ArrayList<>();
			for (Interaction interaction : interactions) {
                interactions_string.add(interaction.getDisplayName());
			}
            ihm.displayInteractions(interactions_string);
            int numInteraction = ihm.askInteraction();
            if (numInteraction == -1) {
                return;
            } else if (numInteraction < interactions.length){
                interactions[numInteraction].interact(inventory, board, (Entity) interactible);
            }
        } else {
            board.logAction(Entity.ANSI_RED_BACKGROUND + "Pas d'interactions disponible." + Entity.ANSI_RESET);
        }

    }
}
