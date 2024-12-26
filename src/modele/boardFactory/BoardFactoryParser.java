package modele.boardFactory;

import modele.Board;
import modele.clock.Clock;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.Monkey;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.Squirrel;
import modele.entity.stationary.food.Acorn;
import modele.entity.stationary.food.Banana;
import modele.entity.stationary.food.Mushroom;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.PalmTree;
import modele.entity.stationary.terrain.high.Tree;
import modele.entity.stationary.terrain.low.Bush;
import modele.entity.stationary.terrain.low.Rock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class BoardFactoryParser extends BoardFactory {
	File mapFile;
	public BoardFactoryParser()  {
		mapFile = new File("carte.txt");
	}

	@Override
	public void makeBoard() throws FileNotFoundException {
		parseBoard();
	}

	public void parseBoard() throws FileNotFoundException {
		Clock clock = Clock.getInstance();
		char theme;
		int height;
		int width;
		PlayerCharacter player = null;

		Scanner scanner = new Scanner(mapFile);
		theme = scanner.nextLine().charAt(0);
		height = scanner.nextInt();
		width = scanner.nextInt();
		Terrain[][] board = new Terrain[height][width];
		int y = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.isEmpty()) { // TODO : Ca peut êtes améliorer en réglant le problème du scanner fantôme
				line = scanner.nextLine();
			}
			for (int x = 0; x < width; x++) {
				if (x >= line.length()) {
					board[y][x] = new Empty(x, y);
				} else if (line.charAt(x) == '@') {
					if (player == null) {
						Terrain e = new Empty(x, y);
						player = new PlayerCharacter(x, y);
						e.setEntityOnCase(player);
						board[y][x] = e;
					} else {
						throw new IllegalArgumentException("Le fichier est invalide. (2 joueurs)");
					}
				} else if (line.charAt(x) == 'E') {
					Terrain e = new Empty(x, y);
					NonPlayerCharacter npc = new Squirrel(x, y);
					clock.attacher(npc);
					e.setEntityOnCase(npc);
					board[y][x] = e;
				} else if (line.charAt(x) == 'A') {
					board[y][x] = new Tree(x, y);
				} else if (line.charAt(x) == 'B') {
					if (theme == 'F') {
						board[y][x] = new Bush(x, y);
					} else {
						Terrain e = new Empty(x, y);
						e.setEntityOnCase(new Banana(x, y));
						board[y][x] = e;
					}
				} else if (line.charAt(x) == 'G') {
					Terrain e = new Empty(x, y);
					e.setEntityOnCase(new Acorn(x, y));
					board[y][x] = e;
				} else if (line.charAt(x) == 'S') {
					Terrain e = new Empty(x, y);
					e.setEntityOnCase(new Monkey(x, y));
					board[y][x] = e;
				} else if (line.charAt(x) == 'P') {
					board[y][x] = new PalmTree(x, y);
				} else if (line.charAt(x) == 'R') {
					board[y][x] = new Rock(x, y);
				} else if (line.charAt(x) == 'C') {
					Terrain e = new Empty(x, y);
					e.setEntityOnCase(new Mushroom(x, y));
					board[y][x] = e;
				} else if (line.charAt(x) == ' ') {
					board[y][x] = new Empty(x, y);
				} else {
					throw new IllegalArgumentException("Le fichier est invalide. (Caractère inconnu)");
				}
			}
			y++;
		}
		Board.buildBoard(theme, height, width, board, player);
	}
}
