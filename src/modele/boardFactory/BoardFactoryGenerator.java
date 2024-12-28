package modele.boardFactory;

import modele.Board;
import modele.boardFactory.generator.Generator;
import modele.clock.Clock;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

public abstract class BoardFactoryGenerator extends BoardFactory {

	int height;
	int width;
	Terrain[][] board;
	Generator generator;

	public BoardFactoryGenerator(int height, int width) {
		this.height = height;
		this.width = width;
	}

	protected int distance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	public void makeBoard() {
		generateBoard();
	}

	public void generateBoard() {
		board = new Terrain[height][width];
		int clusterAmount = Math.max(((height * width) / 400), 3);

		int[][] clusters = new int[clusterAmount][2];
		for (int i = 0; i < clusterAmount; ++i) {
			clusters[i][0] = (int) (Math.random() * (height - 2) + 1);
			clusters[i][1] = (int) (Math.random() * (width - 2) + 1);
		}

		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// Create a wall of tree around the map
				if (y == 0 || x == 0 || y == height - 1 || x == width - 1) {
					if (Math.random() <= .95) {
						board[y][x] = generator.generateHighTerrain(x,y);
					} else {
						board[y][x] = generator.generateLowTerrain(x,y);
					}
				} else {
					int closestCluster = 0;
					int closestDistance = distance(y, x, clusters[0][0], clusters[0][1]);
					for (int i = 1; i < clusterAmount; ++i) {
						int distance = distance(y, x, clusters[i][0], clusters[i][1]);
						if (distance < closestDistance) {
							closestDistance = distance;
							closestCluster = i;
						}
					}
					closestDistance = 15 - closestDistance;
					if (Math.random() * 20 <= closestDistance) {
						// Generating something
						if (closestCluster % 2 == 0) {
							if (closestCluster == 0) {
								// Spawn Cluster
								board[y][x] = generator.generateEmpty(x, y);
							} else {
								// Food Cluster
								if (Math.random() <= .65) {
									board[y][x] = generator.generateMushroom(x, y);
								} else {
									board[y][x] = generator.generateFood(x, y);
								}
							}
						} else {
							// Tree Cluster
							if (Math.random() <= .65) {
								board[y][x] = generator.generateHighTerrain(x, y);
							} else {
								board[y][x] = generator.generateLowTerrain(x, y);
							}
						}
					} else {
						// Generating an empty space
						if (Math.random() <= .03) {
							board[y][x] = generator.generatePrey(x, y);
						} else {
							board[y][x] = generator.generateEmpty(x, y);
						}
					}
				}
			}
		}
		PlayerCharacter player = new PlayerCharacter(clusters[0][1], clusters[0][0]);
		board[clusters[0][0]][clusters[0][1]].setEntityOnCase(player);
		Board.buildBoard(getTheme(), height, width, board, player);
	}

	/*public abstract Terrain generateHighTerrain(int x, int y);

	public abstract Terrain generateLowTerrain(int x, int y);

	public Terrain generateEmpty(int x, int y) {
		return new Empty(x,y);
	}

	public abstract Terrain generateAnimal(int x, int y);

	public abstract Terrain generateFood(int x, int y, boolean isMushroom);*/

	public abstract char getTheme();


}
