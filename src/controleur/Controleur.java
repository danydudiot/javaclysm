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


/**
 * Classe qui s'occupe de contrôler le déroulement du jeu.
 */
public class Controleur {
    /** Interface de l'IHM (Interface Homme-Machine) pour interagir avec l'utilisateur. */
    protected Ihm ihm;

    /** L'entité représentant le joueur sur la carte. */
    protected PlayerCharacter playerCharacter;


    /**
     * Constructeur de la classe Controleur.
     * Initialise l'interface utilisateur et l'inventaire.
     */
    public Controleur() {
        this.ihm = new Ihm();
        // Init of the inventory
        Inventory.getInstance();
    }

    /**
     * Démarre le jeu en initialisant le plateau et le joueur.
     * Gère les exceptions liées au chargement du plateau.
     */
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

    /**
     * Boucle principale du jeu.
     * Appelle la méthode tour() pour chaque tour de jeu.
     */
    private void game() {
        while (true){
            tour();
        }
    }

    /**
     * Gère un tour de jeu.
     * Affiche l'état actuel du jeu et demande une action au joueur.
     * Gère les exceptions liées aux actions invalides.
     */
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
			if ("zqsd".indexOf(action) != -1) { // Déplacement.
				if (playerCharacter.canMove(action)) {
					clock.addCommandToTurn(new PlayerMoveCommand(playerCharacter, action));
					clock.notifierObservateur();
				} else {
					Board.getInstance().logError("Déplacement impossible");
				}
			} else if ("oklm".indexOf(action) != -1) { // Changement d'orientation.
				playerCharacter.changeOrientation(action);
			} else if (action == 'i') { // Inventaire.
				manageInventory();
			} else if (action == 'e') { // Interaction.
				manageInteraction();
			} else if (action == 'j') { // Jeter.
				clock.addCommandToTurn(new PlayerDropCommand((Entity) Inventory.getInstance().getEquippedItem()));
				clock.notifierObservateur();
			} else if (action == 'h') { // Pour afficher l'aide.
				ihm.printHelpPage(board.getTheme());
			} else if (action == 'r') { // Retour dans le passé.
				clock.undoLastTurn();
			} else if (action == 'x') { // Pour passer un tour.
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
            Board.getInstance().logError(e.getMessage());
        }
    }

    /**
     * Gère les interactions du joueur avec les entités sur le terrain.
     * Affiche les interactions disponibles et exécute l'interaction choisie.
     */
    private void manageInteraction(){
        Clock clock = Clock.getInstance();
        Board board = Board.getInstance();
        Terrain terrain = board.getToward(playerCharacter.getX(),playerCharacter.getY(), playerCharacter.getOrientation());
        if (terrain != null && terrain.getEntityOnCase() instanceof Interactible interactible) {
            Entity entity = terrain.getEntityOnCase();
            Interaction[] interactions = interactible.getInteractions();
            List<String> interactions_string = new ArrayList<>();
            for (Interaction interaction : interactions) {
                interactions_string.add(interaction.getDisplayName());
            }
            ihm.displayInteractions(interactions_string); // Affichage des interactions possibles.

            int numInteraction = ihm.askInteraction(); // Demande de l'interaction.
            if (numInteraction >= 0 && numInteraction < interactions.length){
                if (interactions[numInteraction] instanceof Grab grab) {
                    clock.addCommandToTurn(new InteractionGrabCommand(entity,grab));
                } else if (interactions[numInteraction] instanceof Hit hit) {
                    clock.addCommandToTurn(new InteractionHitCommand((NonPlayerCharacter) entity, hit));
                }
                if (! (interactions[numInteraction] instanceof GrabTimeStone)) { // On passe un tour sauf si on a utilisé une pierre temporelle.
                    clock.notifierObservateur();
                }
            }
        } else {
            board.logError("Pas d'interactions disponibles.");
        }
    }

    /**
     * Gère l'inventaire du joueur.
     * Affiche l'inventaire et permet au joueur de sélectionner un objet.
     */
    private void manageInventory(){
        Inventory inventory = Inventory.getInstance();
		if (inventory.isEmpty()) {
			Board.getInstance().logError("Votre inventaire est vide...");
		} else {
			ihm.displayInventory(inventory.getItemsStrings(), inventory.getEquippedItemId());
			int numSelection = ihm.askInventory();
			if (numSelection > 0 && numSelection < inventory.getInventorySize()) {
                inventory.setEquippedItem(numSelection);
				manageInventory();
			}
		}
    }
}
