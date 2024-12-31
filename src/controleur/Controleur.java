package controleur;

import exception.InvalidActionException;
import exception.MoveInvalidException;
import modele.Board;
import modele.boardFactory.BoardFactoryGenerator;
import modele.boardFactory.BoardFactoryParser;
import modele.Inventory;
import modele.clock.Clock;
import modele.clock.commands.PlayerDropCommand;
import modele.clock.commands.InteractionGrabCommand;
import modele.clock.commands.InteractionHitCommand;
import modele.clock.commands.PlayerMoveCommand;
import modele.entity.Entity;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.stationary.terrain.Terrain;
import modele.interaction.*;
import vue.Ihm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Controleur {
    protected Ihm ihm;
    protected PlayerCharacter playerCharacter;

    public Controleur() {
        this.ihm = new Ihm();
        // Init of the inventory
        Inventory.getInstance();
    }

    public void startGame(){
        Clock.getInstance().reset();
        if (ihm.askBoard()){
            try{
                new BoardFactoryParser().parseBoard();
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
            new BoardFactoryGenerator(
                    ihm.askDimension("hauteur"),
                    ihm.askDimension("largeur"),
                    theme
            ).generateBoard();
        }
        playerCharacter = Board.getInstance().getPlayer();
        game();
    }


    private void game() {
        while (true){
            tour();
        }
    }


    private void tour(){
        Clock clock = Clock.getInstance();
        Board board = Board.getInstance();
        ihm.display(
                board.getBoardAsList(),
                board.getHeight(),
                board.getWidth(),
                board.peekAtLogs(3),
                playerCharacter.getX(),
                playerCharacter.getY(),
                playerCharacter.getOrientation(),
                Inventory.getInstance().getEquippedItemString(),
                clock.getNbTour()
        );
        char action = ihm.askAction();
        try {
			if ("zqsd".indexOf(action) != -1) {
				if (playerCharacter.canMove(action)) {
					clock.addCommandToTurn(new PlayerMoveCommand(playerCharacter, action));
					clock.notifierObservateur();
				} else {
					Board.getInstance().logError("Déplacement impossible");
				}
			} else if ("oklm".indexOf(action) != -1) {
				playerCharacter.changeOrientation(action);
			} else if (action == 'i') {
				manageInventory();
			} else if (action == 'e') {
				manageInteraction();
			} else if (action == 'j') {
				clock.addCommandToTurn(new PlayerDropCommand((Entity) Inventory.getInstance().getEquippedItem()));
				clock.notifierObservateur();
			} else if (action == 'r') {
				clock.undoLastTurn();
			} else if (action == 'h') {
				ihm.printHelpPage(board.getTheme());
				tour();
			} else if (action == 'x') {
				Board.getInstance().logAction("Passage de tour");
				clock.notifierObservateur();
			} else if (Character.isDigit(action)) {
				int amount = Integer.parseInt(String.valueOf(action));
				for (int i = 0; i < amount; i++) {
				    clock.notifierObservateur();
			    }
			    Board.getInstance().logAction("Passage de " + amount + " tour(s)");
            } else {
                    throw new InvalidActionException("Action inconnue.");
                }
        } catch (InvalidActionException | MoveInvalidException e) {
            ihm.displayError(e.getMessage());
            tour();
        }
    }


    private void manageInteraction(){
        Clock clock = Clock.getInstance();
        Board board = Board.getInstance();
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
                    if (interactions[numInteraction] instanceof Grab) {

                        clock.addCommandToTurn(new InteractionGrabCommand((Entity) interactible,(Grab) interactions[numInteraction]));
                    } else if (interactions[numInteraction] instanceof Hit) {
                        clock.addCommandToTurn(new InteractionHitCommand((NonPlayerCharacter) entity, (Hit) interactions[numInteraction]));
                    }
                    if (! (interactions[numInteraction] instanceof GrabTimeStone)) {
                        clock.notifierObservateur();
                    }
                }
            } else {
                board.logError("Pas d'interactions disponibles.");
            }
        } else {
            board.logError("Pas d'interactions disponibles.");
        }
    }

    private void manageInventory(){
        ihm.displayInventory(Inventory.getInstance().getItemsStrings(), Inventory.getInstance().getEquippedItemString(), Inventory.getInstance().getEquippedItemId());
        int numSelection = ihm.askInventory();
        if (numSelection == -1) {
            return;
        } else if (numSelection < Inventory.getInstance().getInventorySize()) {
            Inventory.getInstance().setEquippedItem(numSelection);
            manageInventory();
        }
    }
}
