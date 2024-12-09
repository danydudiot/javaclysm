package vue;

import java.util.*;

public class Ihm {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static final String ANSI_DARK_BLACK = "\u001B[30m";
	public static final String ANSI_LIGHT_BLACK = "\u001B[90m";

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
			System.out.println("Voulez vous charger une carte au format "+ ANSI_YELLOW+".txt"+ANSI_RESET +" ? (y/n)");
//			String answer = sc.next(".");
			String answer = sc.nextLine();
			if (answer.equals("y") || answer.equals("Y")) {
				return true;
			} else if (answer.equals("n") || answer.equals("N")) {
				return false;
			} else {
				System.out.println(ANSI_RED + "caractère non reconnu." + ANSI_RESET);
			}
		}
	}
	public String askFile() {
		while (true) {
			System.out.println("Veuillez entrer un path valide vers votre carte: ");
			String answer = sc.nextLine();
			if (answer.equals("c")) {
				return "carte.txt";
			}
			if (answer.endsWith(".txt") || answer.endsWith(".TXT")) {
				return answer;
			} else {
				System.out.println(ANSI_RED + "Merci d'entrer un fichier au format .txt" + ANSI_RESET);
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
				System.out.println(ANSI_RED + "Merci d'entrer un theme valide" + ANSI_RESET);
			}
		}
	}

	public int askDimension(String type) {
		while (true) {
			System.out.println("Veuillez entrer la "+ type + " souhaitée :");
			try {
				if (sc.hasNextLine()) {
					sc.nextLine();
				}
				int input = sc.nextInt();
				if (input >= 3) {
					return input;
				} else {
					System.out.println(ANSI_RED + "Merci d'entrer une taille >= 3" + ANSI_RESET);
				}
			} catch (InputMismatchException e) {
				System.out.println(ANSI_RED + "Merci d'entrer une valeur numérique." + ANSI_RESET);
			}
		}
	}

	public char askAction() {
		while (true) {
			String scannerInput = sc.nextLine();
			if (! scannerInput.isEmpty()) {
				char input = scannerInput.toLowerCase().charAt(0);
				if ("zqsdoklmeij ".indexOf(input) != -1) {
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
		System.out.println(ANSI_RESET);
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
		System.out.println(ANSI_RESET);
		System.out.println(last_frame[0].replace('├','└').replace('┬', '─'));
		System.out.print(ui);
		last_frame[1] = ui;
	}
	public void displayError(String error) {
		System.out.println(last_frame[0].replace('├','└').replace('┬', '─'));
		System.out.println(ANSI_RESET + ANSI_RED + error + ANSI_RESET);
	}
	public void display(List<List<String>> board, int boardHeight, int boardWidth, List<String> actionHistory, int playerX, int playerY, char playerDir, String equippedItem) {
		String croppedBoard = cropBoard(board, boardWidth, boardHeight, displayWidth, displayHeight-4,playerX,playerY, true);
		String ui = makeUi(displayWidth, actionHistory, playerX, playerY, playerDir, equippedItem);

		last_frame[0] = croppedBoard;
		last_frame[1] = ui;

		System.out.println(ANSI_RESET);
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
			for (int c = start; c < start + targetWidth; ++c) {
				try {
				output.append(lines.get(lineStart).get(c));

				} catch (Exception e) {
					System.out.println(lineStart + "; " + c);
					throw e;
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
	private String makeUi(int targetWidth, List<String> actionHistory, int playerX, int playerY, char playerDir, String equippedItem) {
		Queue<String> actionHistoryCopy = new ArrayDeque<>(actionHistory);
		return 	"│ x: " + String.format("%-4S", playerX) +"y: " + String.format("%-4S", playerY) +"       │ " + actionHistoryCopy.remove() + '\n' +
				"│ Dir : (" + asArrow(playerDir) +")            │ " + ANSI_LIGHT_BLACK + actionHistoryCopy.remove() + ANSI_RESET + '\n' +
				"│ >> " + String.format("%-18s", equippedItem) + "│ " + ANSI_DARK_BLACK + actionHistoryCopy.remove() + ANSI_RESET + '\n' +
				ANSI_WHITE_BACKGROUND + ANSI_BLACK + String.format(("%-"+(targetWidth-1)+"s"), " ZQSD : Bouger   OKLM : Regarder   I : Inventaire   E : Interagir   J : Jeter");
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
				.append(ANSI_WHITE_BACKGROUND)
				.append(ANSI_BLACK)
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
				out.append(ANSI_WHITE_BACKGROUND + ANSI_BLACK);
			}

			out.append("[").append(i).append("] : ");
			if (i-1 <= items.size()-1) {
				out.append(String.format("%-19s", items.get(i-1)));
			} else {
				out.append(String.format("%-19s", "..."));
			}

			if (i-1 == equippedItem) {
				out.append(ANSI_RESET);
			}
		}
		out.append("\n")
				.append(ANSI_WHITE_BACKGROUND)
				.append(ANSI_BLACK)
				.append(String.format("%-"+(displayWidth -1)+"s","1-"+ items.size() +" : Selectionner l'objet à equiper    I : fermer le menu"));
		return out.toString();
	}


}
