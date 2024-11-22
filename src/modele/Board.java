package modele;

import modele.entity.Entity;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {
    private Entity[][] board;
    private int height;
    private int width;
    private char theme;

    /**
     * Construction de génération
     */
    public Board() {
    }

    public Entity[][] getBoard() {
        return board;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public char getTheme() {
        return theme;
    }

    public Entity getAt(int x, int y) {
        return board[y][x];
    }

    /**
     * Construction de parseur
     * @param file chemin d'accès au fichier contenant la carte
     */
    public Board(String file) throws FileNotFoundException, IllegalArgumentException {
        File mapFile = new File(file);
        Scanner scanner = new Scanner(mapFile);
        this.theme = (char) scanner.nextLine();
        this.height = (int) scanner.nextLine();
        this.width = (int) scanner.nextLine();
        int y = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; i < line.length(); i++) {
                if (line[x] == '@') {
                    this.board[y][x] = new PlayerCharacter(x,y);
                } else if (line[x] == 'E') {
                    this.board[y][x] = new Squirrel(x,y);
                } else if (line[x] == 'A') {
                    this.board[y][x] = new Tree(x,y);
                } else if (line[x] == 'B') {
                    this.board[y][x] = new Bush(x,y);
                } else if (line[x] == 'G') {
                    this.board[y][x] = new Acorn(x,y);
                } else if (line[x] == 'C') {
                    this.board[y][x] = new Mushroom(x,y);
                } else if (line[x] == ' ') {
                    this.board[y][x] = new Empty(x,y);
                } else {
                    thow new IllegalArgumentException("Le fichier est invalide.");
                }
            }
            y++;
        }
    }

    //TODO foutre les parser là où il faut

    /**
     * @param file chemin d'accès au fichier contenant la carte
     * @throws FileNotFoundException si le fichier n'est pas trouvé
     * @throws IllegalArgumentException si le fichier contient des charactères non reconnus
     */
    public BoardJUNGLE(String file) throws FileNotFoundException, IllegalArgumentException {
        File mapFile = new File(file);
        Scanner scanner = new Scanner(mapFile);
        this.theme = (char) scanner.nextLine();
        this.height = (int) scanner.nextLine();
        this.width = (int) scanner.nextLine();
        int y = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; i < line.length(); i++) {
                if (line[x] == '@') {
                    this.board[y][x] = new PlayerCharacter(x,y);
                } else if (line[x] == 'S') {
                    this.board[y][x] = new Monkey(x,y);
                } else if (line[x] == 'P') {
                    this.board[y][x] = new PalmTree(x,y);
                } else if (line[x] == 'R') {
                    this.board[y][x] = new Rock(x,y);
                } else if (line[x] == 'B') {
                    this.board[y][x] = new Banana(x,y);
                } else if (line[x] == 'C') {
                    this.board[y][x] = new Mushroom(x,y);
                } else if (line[x] == ' ') {
                    this.board[y][x] = new Empty(x,y);
                } else {
                    thow new IllegalArgumentException("Le fichier est invalide.");
                }
            }
            y++;
        }
    }
}
