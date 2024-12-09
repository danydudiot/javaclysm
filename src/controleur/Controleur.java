package controleur;

import exception.InvalidActionException;
import exception.MoveInvalidException;
import modele.Board;
import modele.boardFactory.BoardFactoryGeneratorForest;
import modele.boardFactory.BoardFactoryGeneratorJungle;
import modele.boardFactory.BoardFactoryParser;
import modele.Inventory;
import modele.clock.Clock;
import modele.entity.Entity;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.stationary.terrain.Terrain;
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
                this.board = new BoardFactoryParser(clock, ihm.askFile()).parseBoard();
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
            char theme = ihm.askTheme();
            if (theme == 'F') {
                this.board = new BoardFactoryGeneratorForest(
                                clock,
                                ihm.askDimension("hauteur"),
                                ihm.askDimension("largeur")
                        ).generateBoard();
            } else {
                this.board = new BoardFactoryGeneratorJungle(
                        clock,
                        ihm.askDimension("hauteur"),
                        ihm.askDimension("largeur")
                ).generateBoard();
            }
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
        ihm.display(board.getBoardAsList(), board.getHeight(), board.getWidth(), board.peekAtLogs(3), playerCharacter.getPosition()[0], playerCharacter.getPosition()[1], playerCharacter.getOrientation(), inventory.getEquippedItemString());
        char action = ihm.askAction();
        try{
            if ("zqsd".indexOf(action) != -1){
                 if (playerCharacter.move(action, board)) {
                     clock.notifierObservateur(board);
                 }
            } else if ("oklm".indexOf(action) != -1) {
                playerCharacter.changeOrientation(action);
            } else if ("i".indexOf(action) != -1) {
                manageInventory();
            } else if ("e".indexOf(action) != -1) {
                manageInteraction();
            } else if ("j".indexOf(action) != -1) {
                inventory.dropItem(board);
                clock.notifierObservateur(board);
            } else {
                throw new InvalidActionException("Action inconnue.");
            }
        } catch (InvalidActionException | MoveInvalidException e) {
            ihm.displayError(e.getMessage());
            tour();
            return;
        }
    }


    private void manageInteraction(){
        int[] position = playerCharacter.getTarget();
        Terrain terrain = board.getAt(position[0],position[1]);
        if (terrain != null) {
            Entity entity = terrain.getEntityOnCase();
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
                    clock.notifierObservateur(board);
                }
            } else {
                board.logAction(Entity.ANSI_RED + "Pas d'interactions disponibles." + Entity.ANSI_RESET);
            }
        } else {
            board.logAction(Entity.ANSI_RED + "Pas d'interactions disponibles." + Entity.ANSI_RESET);
        }
    }

    private void manageInventory(){
        ihm.displayInventory(inventory.getItemsStrings(), inventory.getEquippedItemString(), inventory.getEquippedItemId());
        int numSelection = ihm.askInventory();
        if (numSelection == -1) {
            return;
        } else if (numSelection < inventory.getInventorySize()) {
            inventory.setEquippedItem(numSelection);
            manageInventory();
        }
    }
}
