package modele.boardFactory;

import modele.Board;
import modele.boardFactory.generator.Generator;
import modele.boardFactory.generator.GeneratorForest;
import modele.boardFactory.generator.GeneratorJungle;
import modele.clock.Clock;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.prey.Monkey;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.prey.Squirrel;
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
		Generator generator;
		if (theme == 'F'){
			generator = new GeneratorForest();
		} else {
			generator = new GeneratorJungle();
		}

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.isEmpty()) { // TODO : Ca peut êtes améliorer en réglant le problème du scanner fantôme
				line = scanner.nextLine();
			}
			for (int x = 0; x < width; x++) {
				if (x >= line.length()) {
					board[y][x] = new Empty(x, y);
				} else {
					board[y][x] = generator.parse(x,y,line.charAt(x));
					if (board[y][x].getEntityOnCase() instanceof PlayerCharacter){
						player = (PlayerCharacter) board[y][x].getEntityOnCase();
					}
				}
			}
			y++;
		}
		Board.buildBoard(theme, height, width, board, player);
	}
}
