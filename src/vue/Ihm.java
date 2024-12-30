package vue;

import modele.Colors;

import java.util.*;

public class Ihm {


	private String[] last_frame = new String[2];
	private final int displayHeight;
	private final int displayWidth;
	private final Scanner sc;
	public Ihm(int height, int width) {
		this.displayHeight = height;
		this.displayWidth = width;
		this.sc	= new Scanner(System.in);
		last_frame = new String[]{"",""};
	}
	public Ihm() {
		this(21, 80);
	}


	public boolean askBoard() {
		while (true) {
			System.out.println("Voulez vous charger une carte au format "+ Colors.YELLOW +".txt"+ Colors.RESET +" ? (y/n)");
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

	public char askAction() {
		while (true) {
			String scannerInput = sc.nextLine();
			if (! scannerInput.isEmpty()) {
				char input = scannerInput.toLowerCase().charAt(0);
				if ("zqsdoklmeijrh ".indexOf(input) != -1) {
					return input;
				}
			}
		}
	}
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
	public void displayInteractions(List<String> interactions) {
		String ui = makeInteractionUi(interactions);
		System.out.println(Colors.RESET);
		System.out.println(last_frame[0].replace('├','└').replace('┬', '─'));
		System.out.print(ui);
		last_frame[1] = ui;
	}
	public int askInventory() {
		while (true) {
			char input = sc.nextLine().charAt(0);
			if (input == 'i') { return -1; }
			else if (Character.isDigit(input)) { return Integer.parseInt(Character.toString(input))-1; }
		}
	}
	public void displayInventory(List<String> items, String equippedItem, int equippedItemId) {
		String ui = makeInventoryUi(items, equippedItemId);
		System.out.println(Colors.RESET);
		System.out.println(last_frame[0].replace('├','└').replace('┬', '─'));
		System.out.print(ui);
		last_frame[1] = ui;
	}
	public void displayError(String error) {
		System.out.println(last_frame[0].replace('├','└').replace('┬', '─'));
		System.out.println(Colors.RESET + Colors.RED + error + Colors.RESET);
	}
	public void display(List<List<String>> board, int boardHeight, int boardWidth, List<String> actionHistory, int playerX, int playerY, char playerDir, String equippedItem, int turnNumber) {
		String croppedBoard = cropBoard(board, boardWidth, boardHeight, displayWidth, displayHeight-4,playerX,playerY, true);
		String ui = makeUi(displayWidth, actionHistory, playerX, playerY, playerDir, equippedItem, turnNumber);

		last_frame[0] = croppedBoard;
		last_frame[1] = ui;

		System.out.println(Colors.RESET);
		System.out.println(croppedBoard);
		System.out.print(ui);
	}
	private int clamp(int value, int low, int high) {
		return Math.min(Math.max(low, value), high);
	}
	private char asArrow(char dir) {
		return switch (dir) {
			case 'z' -> '↑';
			case 's' -> '↓';
			case 'q' -> '←';
			case 'd' -> '→';
			default  -> '·';
		};
	}
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
//			output.append(lines[lineStart], start, start + targetWidth);
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
	private String makeUi(int targetWidth, List<String> actionHistory, int playerX, int playerY, char playerDir, String equippedItem, int turnNumber) {
		Queue<String> actionHistoryCopy = new ArrayDeque<>(actionHistory);
		return 	"│ tour n°"+ String.format("%-3S", turnNumber) +"           │ " + Colors.LIGHT_WHITE + actionHistoryCopy.remove() + Colors.RESET + '\n' +
				"│ [" + String.format("%03d", playerX) + ","+ String.format("%03d", playerY) +"]        (" + asArrow(playerDir) +") │ " + Colors.WHITE + actionHistoryCopy.remove() + Colors.RESET + '\n' +
				"│ >> " + String.format("%-18s", equippedItem) + "│ " + Colors.LIGHT_BLACK + actionHistoryCopy.remove() + Colors.RESET + '\n' +
				Colors.HIGHLIGHT + String.format(("%-"+(targetWidth-1)+"s"), " ZQSD : Bouger   OKLM : Regarder   I : Inventaire   E : Interagir   J : Jeter");
//		| x: 14  y: 36   | Player move up
//		| Dir : (↓)      | Player look left
//		| >> Ecureil     | Monkey is now friend w/ Player
//		H : Help    ZQSD : Move    OKLM : Look    I : Inventaire    E : Interagir
	}
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
				.append(String.format("%-"+(displayWidth -1)+"s","1-"+ items.size() +" : Selectionner l'objet à equiper    I : fermer le menu"));
		return out.toString();
	}

	public void printHelpPage(char theme) {
		String[] elements;

		if (theme == 'F') {
			elements = new String[] {
					Colors.GREEN 		+ "A" + Colors.RESET + ": Arbre",
					Colors.GREEN 		+ "B" + Colors.RESET + ": Buisson",
					Colors.PURPLE 		+ "C" + Colors.RESET + ": Champignon Normal",
					Colors.LIGHT_WHITE 	+ "E" + Colors.RESET + ": Ecureil Rassasié",
					Colors.WHITE 		+ "E" + Colors.RESET + ": Ecureil Affamé",
					Colors.LIGHT_PURPLE	+ "E" + Colors.RESET + ": Ecureil Ami Rassasié",
					Colors.PURPLE	 	+ "E" + Colors.RESET + ": Ecureil Ami Affamé",
					Colors.RED		 	+ "E" + Colors.RESET + ": Ecureil Junkie",
					Colors.YELLOW 		+ "G" + Colors.RESET + ": Gland",
					Colors.LIGHT_RED	+ "H" + Colors.RESET + ": Hibou en vol",
					Colors.RED			+ "H" + Colors.RESET + ": Hibou au sol",
					Colors.BLUE 		+ "M" + Colors.RESET + ": Champignon Vénéneux",
					Colors.LIGHT_RED	+ "R" + Colors.RESET + ": Renard en chasse",
					Colors.RED			+ "R" + Colors.RESET + ": Renard au repos",
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
		System.out.println(Colors.RESET + "\n" + Colors.HIGHLIGHT + "           LEGENDE DES ICONES           " + Colors.RESET);
		for (String element : elements) {
			System.out.println(element);
		}
	}

}
