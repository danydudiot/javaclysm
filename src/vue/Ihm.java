package vue;

import modele.Colors;

import java.util.*;


public class Ihm {

	/**
	 * Représente les deux dernières composantes (la carte en 0 et l'UI en 1) de la dernière frame affichée
	 */
	private String[] last_frame = new String[2];
	/**
	 * Représente la hauteur de la frame.
	 */
	private final int displayHeight;
	/**
	 * Représente la largeur de la frame
	 */
	private final int displayWidth;
	/**
	 * Permet de lire les inputs de l'utilisateur.
	 */
	private final Scanner sc;

	/**
	 * Constructeur par défaut.
	 * @param height hauteur de la frame
	 * @param width largeur de la frame
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
	 * @return une String de path finissant par `.txt`.
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
	 * @return la dimension
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
	 * @return le char représentant l'action ∈ {'z', 'q', 's', 'd', 'o', 'k', 'l', 'm', 'e', 'i', 'j', 'r', 'h', 'x'}
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
	 * @return -1: fermer le menu, 0-8: pour le numéro de l'action choisie (ré-indexée à 0 au lieu de 1).
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
	 * Affiche la liste des interactions formatté en une table.
	 * @param interactions une liste de taille 9 représentant les interactions
	 */
	public void displayInteractions(List<String> interactions) {
		String ui = makeListUi(interactions, -1, "Choisir l'interaction", 'E');
		System.out.println(Colors.RESET);
		System.out.println(last_frame[0]);
		System.out.print(ui);
		last_frame[1] = ui;
	}

	/**
	 * Lit le numéro de l'objet séléctionné pour être equipé dans l'inventaire.
	 * @return -1: fermer le menu, 0-8: numéro de l'objet choisi (ré-indéxée a 0 au lieu de 1).
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
		String ui = makeListUi(items, equippedItemId, "Selectionner l'objet à equiper", 'I');
		System.out.println(Colors.RESET);
		System.out.println(last_frame[0]);
		System.out.print(ui);
		last_frame[1] = ui;
	}

	/**
	 * Permet d'afficher les erreurs avant le début du jeu (quand on n'a pas encore d'ui).
	 * @param error le message d'erreur à afficher.
	 */
	public void displayError(String error) {
		System.out.println(last_frame[0].replace('├','└').replace('┬', '─'));
		System.out.println(Colors.RESET + Colors.RED + error + Colors.RESET);
	}

	/**
	 * Permet d'afficher une frame de jeu normal (pas inventaire ou interaction).
	 * @param board liste des lignes du plateau.
	 * @param boardHeight hauteur du plateau.
	 * @param boardWidth largeur du plateau.
	 * @param actionHistory liste des dernières actions/erreurs loggées.
	 * @param playerX position x du joueur.
	 * @param playerY position x du joueur.
	 * @param playerDir orientation du joueur.
	 * @param equippedItem représentation de l'objet équipé.
	 * @param turnNumber le numéro de tour.
	 */
	public void display(List<List<String>> board, int boardHeight, int boardWidth, List<String> actionHistory, int playerX, int playerY, char playerDir, String equippedItem, int turnNumber) {
		String croppedBoard = cropBoard(board, boardWidth, boardHeight, displayWidth, displayHeight-4,playerX,playerY);
		String ui = makeUi(actionHistory, playerX, playerY, playerDir, equippedItem, turnNumber);

		last_frame[0] = croppedBoard;
		last_frame[1] = ui;

		System.out.println(Colors.RESET);
		System.out.println(croppedBoard);
		System.out.print(ui);
	}

	/**
	 * Helper permettant de clamp un nombre entre deux bornes
	 * @param value valeur a clamper.
	 * @param low borne basse.
	 * @param high borne haute.
	 * @return x ∈ [low, high]
	 */
	private int clamp(int value, int low, int high) {
		return Math.min(Math.max(low, value), high);
	}

	/**
	 * Helper permettant de convertir une direction en fleche.
	 * @param dir 'z' | 'q' | 's' | 'd' | default
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
	 * Helper qui permet de tronquer une chaîne au format "Chaine trop long..."
	 * @param source chaîne source
	 * @param length taille maximale
	 * @return la chaine tronquée et suivie de point de suspension si elle est trop longue, la chaine source sinon.
	 */
	private String truncate(String source, int length) {
		if (getUncoloredLentgh(source) > length) {
			int difference = source.length() - getUncoloredLentgh(source);
			if (difference == 0) {
				return source.substring(0, (length -3)) + "...";
			} else {
				return source.substring(0, (source.length() - Colors.RESET.length())).substring(0, (length - 3) + difference - Colors.RESET.length()) + "..." + Colors.RESET;
			}
		} else {
			return source;
		}
	}

	/**
	 * Helper qui donne la taille d'une string sans compter les characteres de colorisations
	 * @param string la chaîne d'entrée
	 * @return le nombre de characteres non-colorisations
	 */
	private int getUncoloredLentgh(String string) {
		int j = 0;
		for (int i = 0; i < string.length(); ++i) {
			if (string.charAt(i) == '\u001b') {
				i += 4;
			} else {
				j++;
			}
		}
		return j;
	}

	/**
	 * Helper qui permet de recadrer et de centrer la carte sur la position du joueur.
	 * @param lines liste des lignes qui composent la carte.
	 * @param sourceWidth largeur initiale de la carte
	 * @param sourceHeight hauteur initiale de la carte
	 * @param targetWidth largeur souhaitée
	 * @param targetHeight hauteur souhaitée
	 * @param playerX position X du joueur
	 * @param playerY position Y du joueur
	 * @return une String à afficher qui représente la carte à la taille correcte.
	 */
	private String cropBoard(List<List<String>> lines, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight, int playerX, int playerY) {
		StringBuilder output = new StringBuilder();
		targetWidth -= 2;
		targetHeight -= 2;
		output.append("┌").append("─".repeat(targetWidth)).append("┐");


		int offsetY = clamp(playerY - (targetHeight / 2), 0, sourceHeight-targetHeight);
		if (offsetY < 0) {
			offsetY /= 2;
		}
		for (int i = 0; i < targetHeight; ++i) {
			if (! output.isEmpty()) {
				output.append('\n');
			}
			output.append("│");
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
			output.append("│");
		}
		return output.toString();
	}

	/**
	 * Helper qui permet de mettre en forme l'UI du jeu.
	 * @param actionHistory les dernières actions loggées
	 * @param playerX la position x du joueur
	 * @param playerY la position y du joueur
	 * @param playerDir la direction du joueur
	 * @param equippedItem la representation de l'objet équipé
	 * @param turnNumber le numéro de tour.
	 * @return une String à afficher qui représente l'UI de base du jeu.
	 */
	private String makeUi(List<String> actionHistory, int playerX, int playerY, char playerDir, String equippedItem, int turnNumber) {
		Queue<String> actionHistoryCopy = new ArrayDeque<>(actionHistory);
		int panelSize = Math.max(24, equippedItem.length() + 6);
		String hist1 = truncate(actionHistoryCopy.remove(), displayWidth - panelSize - 4);
		String hist2 = truncate(actionHistoryCopy.remove(), displayWidth - panelSize - 4);
		String hist3 = truncate(actionHistoryCopy.remove(), displayWidth - panelSize - 4);
		return 	"├" + "─".repeat(panelSize - 1) + "┬" + "─".repeat(displayWidth - panelSize - 2) + "┤\n" +
				"│ tour n°"+ String.format("%-"+ (panelSize - 9) + "S", String.format("%-3S", turnNumber))  + "│ " + Colors.LIGHT_WHITE + hist1 + Colors.RESET + " ".repeat(Math.abs(displayWidth - panelSize - getUncoloredLentgh(hist1) -4 )) + " │\n" +
				"│ [" + String.format("%03d", playerX) + ","+ String.format("%03d", playerY) +"]"+ " ".repeat(panelSize - 15) +"(" + asArrow(playerDir) +") │ " + Colors.WHITE + hist2 + Colors.RESET + " ".repeat(Math.abs(displayWidth - panelSize - getUncoloredLentgh(hist2) -4)) + " │\n" +
				"│ >> " + String.format("%-"+ (panelSize - 6) +"s", equippedItem) + " │ " + Colors.LIGHT_BLACK + hist3 + Colors.RESET + " ".repeat(Math.abs(displayWidth - panelSize - getUncoloredLentgh(hist3) -4)) + " │\n" +
				Colors.HIGHLIGHT + String.format(("%-"+(displayWidth)+"s"), " ZQSD : Bouger   OKLM : Regarder   I : Inventaire   E : Interagir   J : Jeter") + Colors.RESET;
	}

	/**
	 * Affiche une ui sous forme de liste pour les interactions et l'inventaire
	 * @param items la liste des choses à afficher
	 * @param selectedItem l'id de la chose à afficher
	 * @param tooltip le texte affiché en bas
	 * @param close la touche pour fermer le menu
	 * @return une String a afficher representant l'inventaire sous forme de liste.
	 */
	private String makeListUi(List<String> items, int selectedItem, String tooltip, char close) {
		StringBuilder out = new StringBuilder();
		out.append("├").append("─".repeat(displayWidth-2)).append("┤").append('\n');
		for (int i = 1; i <= 9; ++i) {
			if ((i%3 == 1)) {
				if (i!=1) {
					out.append('\n');
				}
				out.append("│ ");
			}
			if (i-1 == selectedItem) {
				out.append(Colors.HIGHLIGHT);
			}

			out.append("[").append(i).append("] : ");
			if (i-1 <= items.size()-1) {
				// 22 -> taille réservées pour les espaces, les nombres etc...
				out.append(String.format("%-"+ (displayWidth - 22)/3 +"s", truncate(items.get(i-1), (displayWidth - 22)/3)));
			} else {
				out.append(String.format("%-"+ (displayWidth - 22)/3 +"s", "..."));
			}

			if (i-1 == selectedItem) {
				out.append(Colors.RESET);
			}
			if ((i%3 == 0)) {
				out.append(" ".repeat((displayWidth - 21) % 3));
				out.append("│");
			}

		}
		out.append("\n")
				.append(Colors.HIGHLIGHT)
				.append(String.format("%-"+(displayWidth)+"s","1-"+ items.size() +" : " + tooltip +"    "+ close +" : fermer le menu"));
		return out.toString();
	}
	/**
	 * Affiche une page de correspondance entre les choses affichées a l'écran
	 * @param theme le theme choisi.
	 */
	public void printHelpPage(char theme) {
		String[] elements;
		// TODO : réorganiser dans un ordre plus logique.
		if (theme == 'F') {
			elements = new String[] {
					Colors.GREEN 			+ "A" + Colors.RESET + ": Arbre",
					Colors.GREEN 			+ "B" + Colors.RESET + ": Buisson",
					Colors.PURPLE 			+ "C" + Colors.RESET + ": Champignon Normal",
					Colors.LIGHT_WHITE 		+ "E" + Colors.RESET + ": Ecureil Rassasié",
					Colors.WHITE 			+ "E" + Colors.RESET + ": Ecureil Affamé",
					Colors.LIGHT_PURPLE		+ "E" + Colors.RESET + ": Ecureil Ami Rassasié",
					Colors.PURPLE	 		+ "E" + Colors.RESET + ": Ecureil Ami Affamé",
					Colors.RED		 		+ "E" + Colors.RESET + ": Ecureil Junkie",
					Colors.BLUE_BACKGROUND 	+ "E" + Colors.RESET + ": Ecureil Terrifié",
					Colors.YELLOW 			+ "G" + Colors.RESET + ": Gland",
					Colors.LIGHT_RED		+ "H" + Colors.RESET + ": Hibou en vol",
					Colors.LIGHT_BLACK		+ "H" + Colors.RESET + ": Hibou au sol",
					Colors.BLUE 			+ "M" + Colors.RESET + ": Champignon Vénéneux",
					Colors.LIGHT_RED		+ "R" + Colors.RESET + ": Renard en chasse",
					Colors.CYAN 			+ "2" + Colors.RESET + ": Pierre temporelle active (2 tours)",
					Colors.LIGHT_BLACK		+ "2" + Colors.RESET + ": Pierre temporelle inactive (2 tours)",
					Colors.BLUE 			+ "3" + Colors.RESET + ": Pierre temporelle active (3 tours)",
					Colors.LIGHT_BLACK		+ "3" + Colors.RESET + ": Pierre temporelle inactive (3 tours)",
					Colors.PLAYER			+ "@" + Colors.RESET + ": Joueur"
			};
		} else {
			elements = new String[] {
					Colors.YELLOW			+ "B" + Colors.RESET + ": Banane",
					Colors.PURPLE 			+ "C" + Colors.RESET + ": Champignon Normal",
					Colors.LIGHT_RED 		+ "E" + Colors.RESET + ": Serpent en chasse",
					Colors.RED 				+ "E" + Colors.RESET + ": Serpent en repos",
					Colors.BLUE 			+ "M" + Colors.RESET + ": Champignon Hallucinogène",
					Colors.LIGHT_RED 		+ "O" + Colors.RESET + ": Scorpion en déplacement",
					Colors.HIGHLIGHT 		+ "O" + Colors.RESET + ": Scorpion sous un rocher",
					Colors.CYAN				+ "P" + Colors.RESET + ": Palmier",
					Colors.WHITE			+ "R" + Colors.RESET + ": Rocher",
					Colors.LIGHT_WHITE 		+ "S" + Colors.RESET + ": Singe Rassasié",
					Colors.WHITE 			+ "S" + Colors.RESET + ": Singe Affamé",
					Colors.LIGHT_PURPLE		+ "S" + Colors.RESET + ": Singe Ami Rassasié",
					Colors.PURPLE	 		+ "S" + Colors.RESET + ": Singe Ami Affamé",
					Colors.RED		 		+ "S" + Colors.RESET + ": Singe Junkie",
					Colors.CYAN 			+ "2" + Colors.RESET + ": Pierre temporelle active (2 tours)",
					Colors.LIGHT_BLACK		+ "2" + Colors.RESET + ": Pierre temporelle inactive (2 tours)",
					Colors.BLUE 			+ "3" + Colors.RESET + ": Pierre temporelle active (3 tours)",
					Colors.LIGHT_BLACK		+ "3" + Colors.RESET + ": Pierre temporelle inactive (3 tours)",
					Colors.PLAYER			+ "@" + Colors.RESET + ": Joueur"

			};
		}
		System.out.println(Colors.RESET + "\n" + Colors.HIGHLIGHT + "           LEGENDE DES ICONES           " + Colors.RESET);
		for (String element : elements) {
			System.out.println(element);
		}
	}
}