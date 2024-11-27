package vue;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Ihm {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	private final int displayHeight;
	private final int displayWidth;
	private final Scanner sc;
	public Ihm(int height, int width) {
		this.displayHeight = height;
		this.displayWidth = width;
		this.sc	= new Scanner(System.in);
	}
	public Ihm() {
		this(24, 80);
	}


	public boolean askBoard() {
		while (true) {
			System.out.println("Voulez vous charger une carte au format "+ ANSI_YELLOW+".txt"+ANSI_RESET +" ? (y/n)");
			//String answer = sc.next(".");
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

	public char askAction() {
		while (true) {
			char input = sc.next(".").charAt(0);
			if ("zqsdoklmei ".indexOf(Character.toLowerCase(input)) != -1) {
				return Character.toLowerCase(input);
			}
		}
	}
	public void displayError(String error) {
		System.out.println(ANSI_RED + error + ANSI_RESET);
	}
	public void display(String board, int boardHeight, int boardWidth, Queue<String> actionHistory, int playerX, int playerY, char playerDir, String equippedItem) {
		String croppedBoard = cropBoard(board, boardWidth, boardHeight, displayWidth, displayHeight,playerX,playerY, true);
		String ui = makeUi(displayWidth, actionHistory, playerX, playerY, playerDir, equippedItem);
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
	private String cropBoard(String sourceString, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight, int playerX, int playerY, boolean border) {
		StringBuilder output = new StringBuilder();

		if (border) {
			targetWidth -= 2;
			targetHeight -= 2;
			output.append("┌").append("─".repeat(targetWidth)).append("┐");
		}

		String[] lines = sourceString.split("\n");

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
			output.append(lines[lineStart], start, start + targetWidth);
			if (border) {
				output.append("│");
			}
		}
		if (border) {
			output.append("\n").append("├").append("─".repeat(22)).append("┬").append("─".repeat(targetWidth-23)).append("┘");
		}
		return output.toString();
	}
	private String makeUi(int targetWidth, Queue<String> actionHistory, int playerX, int playerY, char playerDir, String equippedItem) {
		Queue<String> actionHistoryCopy = new ArrayDeque<>(actionHistory);
		return 	"│ x: " + String.format("%-4S", playerX) +"y: " + String.format("%-4S", playerY) +"       │ " + actionHistoryCopy.remove() + '\n' +
				"│ Dir : (" + asArrow(playerDir) +")            │ "+ actionHistoryCopy.remove() + '\n' +
				"│ >> " + String.format("%-18s", equippedItem) + "│ " + actionHistoryCopy.remove() + '\n' +
				ANSI_WHITE_BACKGROUND + ANSI_BLACK + String.format(("%-"+(targetWidth-1)+"s"), " ZQSD : Move    OKLM : Look    I : Inventaire    E : Interagir") + ANSI_RESET;
//		| x: 14  y: 36   | Player move up
//		| Dir : (↓)      | Player look left
//		| >> Ecureil     | Monkey is now friend w/ Player
//		H : Help    ZQSD : Move    OKLM : Look    I : Inventaire    E : Interagir
	}
}
