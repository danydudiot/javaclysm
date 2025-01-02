package vue;

import modele.Colors;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


public class Ihm {

	/**
	 * Représente les deux dernières composantes (la carte en 0 et l'UI en 1) de la dernière frame affichée.
	 */
	private String[] last_frame = new String[2];
	/**
	 * Représente la hauteur de la frame.
	 */
	private final int displayHeight;
	/**
	 * Représente la largeur de la frame.
	 */
	private final int displayWidth;
	/**
	 * Permet de lire les inputs de l'utilisateur.
	 */
	private final Scanner sc;

	/**
	 * Constructeur par défaut.
	 * @param height Hauteur de la frame.
	 * @param width Largeur de la frame.
	 */
	public Ihm(int height, int width) {
		this.displayHeight = height;
		this.displayWidth = width;
		this.sc	= new Scanner(System.in);
		last_frame = new String[]{"",""};
	}

	/**
	 * Constructeur sans argument.
	 * Permet d'obtenir une frame de la taille recommandée (21*80).
	 */
	public Ihm() {
		this(21, 80);
	}

	/**
	 * Demande à l'utilisateur s'il souhaite utiliser la carte stockée ou en générer une nouvelle.
	 * @return true si l'utilisateur souhaite charger la carte, false sinon.
	 */
	public boolean askBoard() {
		while (true) {
			System.out.println("Voulez vous charger la carte pré-enregistrée (carte.txt) ? (y/n)");
			String answer = sc.nextLine();
			if (answer.equals("y") || answer.equals("Y")) {
				return true;
			} else if (answer.equals("n") || answer.equals("N")) {
				return false;
			} else {
				System.out.println(Colors.RED + "caractère non reconnu." + Colors.RESET);
			}
		}
	}

	/**
	 * METHODE NON-UTILISÉE Permet de demander au joueur d'entrer le path vers une carte.
	 * @return Une String de path finissant par `.txt`.
	 */
	@Deprecated
	public String askFile() {
		while (true) {
			System.out.println("Veuillez entrer un path valide vers votre carte: ");
			String answer = sc.nextLine();
			if (answer.equals("c")) {
				return "carteIT2.txt";
			}
			if (answer.endsWith(".txt") || answer.endsWith(".TXT")) {
				return answer;
			} else {
				System.out.println(Colors.RED + "Merci d'entrer un fichier au format .txt" + Colors.RESET);
			}
		}
	}

	/**
	 * Demande à l'utilisateur quel thème il souhaite utiliser pour générer la carte.
	 * @return 'F': Forêt, 'J': Jungle.
	 */
	public char askTheme() {
		while (true) {
			System.out.println("Veuillez entrer le theme pour la partie :");
			System.out.println("F : Forêt, J : Jungle");
//			char input = Character.toUpperCase(sc.nextLine().charAt(0));
			String input = sc.nextLine().toUpperCase();
			if (input.equals("F")) {
				return 'F';
			} else if (input.equals("J")) {
				return 'J';
			} else {
				System.out.println(Colors.RED + "Merci d'entrer un theme valide" + Colors.RESET);
			}
		}
	}

	/**
	 * Demande une dimension à l'utilisateur.
	 * @param type "Hauteur" | "Largeur".
	 * @return La dimension.
	 */
	public int askDimension(String type) {
		while (true) {
			System.out.println("Veuillez entrer la "+ type + " souhaitée :");
			String scInput = sc.nextLine();
			try {
				int input = Integer.parseInt(scInput);
				if (input >= 10) {
					return input;
				} else {
					System.out.println(Colors.RED + "Merci d'entrer une taille >= 10" + Colors.RESET);
				}
			} catch (NumberFormatException e) {
				System.out.println(Colors.RED + "Merci d'entrer une valeur numérique." + Colors.RESET);
			}
		}
	}

	/**
	 * Lit l'input de l'utilisateur et vérifie qu'il est valide.
	 * @return Le char représentant l'action ∈ {'z', 'q', 's', 'd', 'o', 'k', 'l', 'm', 'e', 'i', 'j', 'r', 'h', 'x'} ou un chiffre.
	 */
	public char askAction() {
		while (true) {
			String scannerInput = sc.nextLine();
			if (! scannerInput.isEmpty()) {
				char input = scannerInput.toLowerCase().charAt(0);
				if (("zqsdoklmeijrhx".indexOf(input) != -1) || (Character.isDigit(input)) ) {
					return input;
				}
			}
		}
	}

	/**
	 * Lit le numéro de l'interaction (compris entre 1 et 9) choisie par le joueur.
	 * @return -1 : fermer le menu, 0-8 : pour le numéro de l'action choisie (ré-indexée à 0 au lieu de 1).
	 */
	public int askInteraction() {
		while (true) {
			String scannerInput = sc.nextLine();
			if (! scannerInput.isEmpty()) {
				char input = scannerInput.toLowerCase().charAt(0);
				if (input == 'e') { return -1; }
				else if (Character.isDigit(input)) { return Integer.parseInt(Character.toString(input))-1; }
			}
		}
	}

	/**
	 * Affiche la liste des interactions formatée en une table.
	 * @param interactions Une liste de taille 9 représentant les interactions.
	 */
	public void displayInteractions(List<String> interactions) {
		String ui = makeInteractionUi(interactions);
		System.out.println(Colors.RESET);
		System.out.println(last_frame[0].replace('├','└').replace('┬', '─'));
		System.out.print(ui);
		last_frame[1] = ui;
	}

	/**
	 * Lit le numéro de l'objet sélectionné pour être équipé dans l'inventaire.
	 * @return -1 : fermer le menu, 0-8 : numéro de l'objet choisi (ré-indexée à 0 au lieu de 1).
	 */
	public int askInventory() {
		while (true) {
			char input = sc.nextLine().charAt(0);
			if (input == 'i') { return -1; }
			else if (Character.isDigit(input)) { return Integer.parseInt(Character.toString(input))-1; }
		}
	}

	/**
	 * Permet d'afficher la frame d'inventaire.
	 * @param items la liste de taille 9 des représentations de l'inventaire
	 * @param equippedItemId l'indice de l'objet équipé ou -1 s'il n'y en a pas.
	 */
	public void displayInventory(List<String> items, int equippedItemId) {
		String ui = makeInventoryUi(items, equippedItemId);
		System.out.println(Colors.RESET);
		System.out.println(last_frame[0].replace('├','└').replace('┬', '─'));
		System.out.print(ui);
		last_frame[1] = ui;
	}

	/**
	 * Permet d'afficher les erreurs avant le début du jeu (quand on n'a pas encore d'ui).
	 * @param error Le message d'erreur à afficher.
	 */
	public void displayError(String error) {
		System.out.println(last_frame[0].replace('├','└').replace('┬', '─'));
		System.out.println(Colors.RESET + Colors.RED + error + Colors.RESET);
	}

	/**
	 * Permet d'afficher une frame de jeu normal (pas inventaire ou interaction).
	 * @param board Liste des lignes du plateau.
	 * @param boardHeight Hauteur du plateau.
	 * @param boardWidth Largeur du plateau.
	 * @param actionHistory Liste des dernières actions/erreurs loggées.
	 * @param playerX Position x du joueur.
	 * @param playerY Position x du joueur.
	 * @param playerDir Orientation du joueur.
	 * @param equippedItem Représentation de l'objet équipé.
	 * @param turnNumber Le numéro de tour.
	 */
	public void display(List<List<String>> board, int boardHeight, int boardWidth, List<String> actionHistory, int playerX, int playerY, char playerDir, String equippedItem, int turnNumber) {
		String croppedBoard = cropBoard(board, boardWidth, boardHeight, displayWidth, displayHeight-4,playerX,playerY, true);
		String ui = makeUi(displayWidth, actionHistory, playerX, playerY, playerDir, equippedItem, turnNumber);

		last_frame[0] = croppedBoard;
		last_frame[1] = ui;

		System.out.println(Colors.RESET);
		System.out.println(croppedBoard);
		System.out.print(ui);
	}

	/**
	 * Helper permettant de clamp un nombre entre deux bornes.
	 * @param value Valeur a clamper.
	 * @param low Borne basse.
	 * @param high Borne haute.
	 * @return x ∈ [low, high]
	 */
	private int clamp(int value, int low, int high) {
		return Math.min(Math.max(low, value), high);
	}

	/**
	 * Helper permettant de convertir une direction en fleche.
	 * @param dir 'z' | 'q' | 's' | 'd'
	 * @return '↑' | '↓' | '←' | '→' | '·'
	 */
	private char asArrow(char dir) {
		return switch (dir) {
			case 'z' -> '↑';
			case 's' -> '↓';
			case 'q' -> '←';
			case 'd' -> '→';
			default  -> '·';
		};
	}

	/**
	 * Helper qui permet de recadrer et de centrer la carte sur la position du joueur.
	 * @param lines Liste des lignes qui composent la carte.
	 * @param sourceWidth Largeur initiale de la carte.
	 * @param sourceHeight Hauteur initiale de la carte.
	 * @param targetWidth Largeur souhaitée.
	 * @param targetHeight Hauteur souhaitée.
	 * @param playerX Position X du joueur.
	 * @param playerY Position Y du joueur.
	 * @param border true : ajouter une bordure, false : ne pas en ajouter.
	 * @return Une String à afficher qui représente la carte à la taille correcte.
	 */
	private String cropBoard(List<List<String>> lines, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight, int playerX, int playerY, boolean border) {
		StringBuilder output = new StringBuilder();

		if (border) {
			targetWidth -= 2;
			targetHeight -= 2;
			output.append("┌").append("─".repeat(targetWidth)).append("┐");
		}

		int offsetY = clamp(playerY - (targetHeight / 2), 0, sourceHeight-targetHeight);
		if (offsetY < 0) {
			offsetY /= 2;
		}
		for (int i = 0; i < targetHeight; ++i) {
			if (! output.isEmpty()) {
				output.append('\n');
			}
			if (border) {
				output.append("│");
			}
			int lineStart = offsetY + i;
			int start = clamp(playerX - (targetWidth / 2), 0,sourceWidth-targetWidth);
			if (lineStart < 0 || lineStart >= lines.size()) {
				output.append(Colors.LIGHT_BLACK).append("#".repeat(targetWidth)).append(Colors.RESET);
			} else {
				if (start < 0) {
					start /= 2;
				}
				for (int c = start; c < start + targetWidth; ++c) {
					try {
						output.append(lines.get(lineStart).get(c));
					} catch (IndexOutOfBoundsException e) {
						output.append(Colors.LIGHT_BLACK + "#" + Colors.RESET);
					}
				}

			}
			if (border) {
				output.append("│");
			}
		}
		if (border) {
			output.append("\n").append("├").append("─".repeat(22)).append("┬").append("─".repeat(targetWidth-23)).append("┘");
		}
		return output.toString();
	}

	/**
	 * Helper qui permet de mettre en forme l'UI du jeu.
	 * @param targetWidth La largeur souhaitée.
	 * @param actionHistory Les dernières actions loggées.
	 * @param playerX La position x du joueur.
	 * @param playerY La position y du joueur.
	 * @param playerDir La direction du joueur.
	 * @param equippedItem La representation de l'objet équipé.
	 * @param turnNumber Le numéro de tour.
	 * @return Une String à afficher qui représente l'UI de base du jeu.
	 */
	private String makeUi(int targetWidth, List<String> actionHistory, int playerX, int playerY, char playerDir, String equippedItem, int turnNumber) {
		Queue<String> actionHistoryCopy = new ArrayDeque<>(actionHistory);
		return 	"│ tour n°"+ String.format("%-3S", turnNumber) +"           │ " + Colors.LIGHT_WHITE + actionHistoryCopy.remove() + Colors.RESET + '\n' +
				"│ [" + String.format("%03d", playerX) + ","+ String.format("%03d", playerY) +"]        (" + asArrow(playerDir) +") │ " + Colors.WHITE + actionHistoryCopy.remove() + Colors.RESET + '\n' +
				"│ >> " + String.format("%-18s", equippedItem) + "│ " + Colors.LIGHT_BLACK + actionHistoryCopy.remove() + Colors.RESET + '\n' +
				Colors.HIGHLIGHT + String.format(("%-"+(targetWidth-1)+"s"), " ZQSD : Bouger   OKLM : Regarder   I : Inventaire   E : Interagir   J : Jeter");
	}

	/**
	 * Helper qui permet d'afficher la liste des interactions sous forme de tableau.
	 * @param interactions la liste des interactions.
	 * @return Une String à afficher qui représente les interactions disponibles.
	 */
	private String makeInteractionUi(List<String> interactions) {
		StringBuilder out = new StringBuilder();
		for (int i = 1; i <= 9; ++i) {
			if (i!= 1 && (i%3 == 1)) { out.append('\n'); }
			out.append("[").append(i).append("] : ");
			if (i-1 <= interactions.size()-1) {
				out.append(String.format("%-19s", interactions.get(i-1)));
			}
			else { out.append(String.format("%-19s", "...")); }
		}
		out.append("\n")
				.append(Colors.HIGHLIGHT)
				.append(String.format("%-"+(displayWidth -1)+"s","1-"+ interactions.size() +" : Choisir l'interaction    E : Fermer le menu"));
		return out.toString();
	}

	/**
	 * Helper qui permet d'afficher la liste des objets dans l'inventaire et de mettre en valeur celui qui est équipé.
	 * @param items La liste des objets.
	 * @param equippedItem L'indice de l'objet à mettre en valeur.
	 * @return  Une String à afficher qui représente les objets dans l'inventaire.
	 */
	private String makeInventoryUi(List<String> items, int equippedItem) {
		StringBuilder out = new StringBuilder();
		for (int i = 1; i <= 9; ++i) {
			if (i!= 1 && (i%3 == 1)) {
				out.append('\n');
			}
			if (i-1 == equippedItem) {
				out.append(Colors.HIGHLIGHT);
			}

			out.append("[").append(i).append("] : ");
			if (i-1 <= items.size()-1) {
				out.append(String.format("%-19s", items.get(i-1)));
			} else {
				out.append(String.format("%-19s", "..."));
			}

			if (i-1 == equippedItem) {
				out.append(Colors.RESET);
			}
		}
		out.append("\n")
				.append(Colors.HIGHLIGHT)
				.append(String.format("%-"+(displayWidth -1)+"s","1-"+ items.size() +" : Sélectionner l'objet à équiper    I : fermer le menu"));
		return out.toString();
	}

	public void printHelpPage(char theme) {
		String[] elements;
		// TODO : réorganiser dans un ordre plus logique.
		if (theme == 'F') {
			elements = new String[] {
					Colors.GREEN 		+ "A" + Colors.RESET + ": Arbre",
					Colors.GREEN 		+ "B" + Colors.RESET + ": Buisson",
					Colors.PURPLE 		+ "C" + Colors.RESET + ": Champignon Normal",
					Colors.LIGHT_WHITE 	+ "E" + Colors.RESET + ": Écureuil Rassasié",
					Colors.WHITE 		+ "E" + Colors.RESET + ": Écureuil Affamé",
					Colors.LIGHT_PURPLE	+ "E" + Colors.RESET + ": Écureuil Ami Rassasié",
					Colors.PURPLE	 	+ "E" + Colors.RESET + ": Écureuil Ami Affamé",
					Colors.RED		 	+ "E" + Colors.RESET + ": Écureuil Junkie",
					Colors.BLUE_BACKGROUND + "E" + Colors.RESET + ": Écureuil Terrifié",
					Colors.YELLOW 		+ "G" + Colors.RESET + ": Gland",
					Colors.LIGHT_RED	+ "H" + Colors.RESET + ": Hibou en vol",
					Colors.LIGHT_BLACK	+ "H" + Colors.RESET + ": Hibou au sol",
					Colors.BLUE 		+ "M" + Colors.RESET + ": Champignon Vénéneux",
					Colors.LIGHT_RED	+ "R" + Colors.RESET + ": Renard en chasse",
					Colors.CYAN 		+ "2" + Colors.RESET + ": Pierre temporelle active (2 tours)",
					Colors.LIGHT_BLACK	+ "2" + Colors.RESET + ": Pierre temporelle inactive (2 tours)",
					Colors.BLUE 		+ "3" + Colors.RESET + ": Pierre temporelle active (3 tours)",
					Colors.LIGHT_BLACK	+ "3" + Colors.RESET + ": Pierre temporelle inactive (3 tours)",
					Colors.PLAYER		+ "@" + Colors.RESET + ": Joueur"
			};
		} else {
			elements = new String[] {
					Colors.YELLOW		+ "B" + Colors.RESET + ": Banane",
					Colors.PURPLE 		+ "C" + Colors.RESET + ": Champignon Normal",
					Colors.LIGHT_RED 	+ "E" + Colors.RESET + ": Serpent en chasse",
					Colors.RED 			+ "E" + Colors.RESET + ": Serpent en repos",
					Colors.BLUE 		+ "M" + Colors.RESET + ": Champignon Hallucinogène",
					Colors.LIGHT_RED 	+ "O" + Colors.RESET + ": Scorpion en déplacement",
					Colors.HIGHLIGHT 	+ "O" + Colors.RESET + ": Scorpion sous un rocher",
					Colors.CYAN			+ "P" + Colors.RESET + ": Palmier",
					Colors.WHITE		+ "R" + Colors.RESET + ": Rocher",
					Colors.LIGHT_WHITE 	+ "S" + Colors.RESET + ": Singe Rassasié",
					Colors.WHITE 		+ "S" + Colors.RESET + ": Singe Affamé",
					Colors.LIGHT_PURPLE	+ "S" + Colors.RESET + ": Singe Ami Rassasié",
					Colors.PURPLE	 	+ "S" + Colors.RESET + ": Singe Ami Affamé",
					Colors.RED		 	+ "S" + Colors.RESET + ": Singe Junkie",
					Colors.CYAN 		+ "2" + Colors.RESET + ": Pierre temporelle active (2 tours)",
					Colors.LIGHT_BLACK	+ "2" + Colors.RESET + ": Pierre temporelle inactive (2 tours)",
					Colors.BLUE 		+ "3" + Colors.RESET + ": Pierre temporelle active (3 tours)",
					Colors.LIGHT_BLACK	+ "3" + Colors.RESET + ": Pierre temporelle inactive (3 tours)",
					Colors.PLAYER		+ "@" + Colors.RESET + ": Joueur"

			};
		}
		System.out.println(Colors.RESET + "\n" + Colors.HIGHLIGHT + "           LÉGENDE DES ICÔNES           " + Colors.RESET);
		for (String element : elements) {
			System.out.println(element);
		}
	}
}
