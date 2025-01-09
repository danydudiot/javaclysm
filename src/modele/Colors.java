package modele;

/**
 * Classe utilitaire contenant des constantes pour les codes de couleur ANSI.
 */
public final class Colors {
    /** Code de réinitialisation des couleurs. */
    public static final String RESET = "\u001B[0m";
    /** Code de couleur pour le noir. */
    public static final String BLACK = "\u001B[30m";
    /** Code de couleur pour le rouge. */
    public static final String RED = "\u001B[31m";
    /** Code de couleur pour le vert. */
    public static final String GREEN = "\u001B[32m";
    /** Code de couleur pour le jaune. */
    public static final String YELLOW = "\u001B[33m";
    /** Code de couleur pour le bleu. */
    public static final String BLUE = "\u001B[34m";
    /** Code de couleur pour le violet. */
    public static final String PURPLE = "\u001B[35m";
    /** Code de couleur pour le cyan. */
    public static final String CYAN = "\u001B[36m";
    /** Code de couleur pour le blanc. */
    public static final String WHITE = "\u001B[37m";
    /** Code de couleur de fond pour le noir. */
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    /** Code de couleur de fond pour le rouge. */
    public static final String RED_BACKGROUND = "\u001B[41m";
    /** Code de couleur de fond pour le vert. */
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    /** Code de couleur de fond pour le jaune. */
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    /** Code de couleur de fond pour le bleu. */
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    /** Code de couleur de fond pour le violet. */
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    /** Code de couleur de fond pour le cyan. */
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    /** Code de couleur de fond pour le blanc. */
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    /** Code de couleur pour le noir clair. */
    public static final String LIGHT_BLACK = "\u001B[90m";
    /** Code de couleur pour le rouge clair. */
    public static final String LIGHT_RED = "\u001B[91m";
    /** Code de couleur pour le bleu clair. */
    public static final String LIGHT_BLUE = "\u001B[95m";
    /** Code de couleur pour le violet clair. */
    public static final String LIGHT_PURPLE = "\u001b[95m";
    /** Code de couleur pour le blanc clair. */
    public static final String LIGHT_WHITE = "\u001b[97m";

    /** Code de couleur pour le joueur (fond jaune et texte noir). */
    public static final String PLAYER = YELLOW_BACKGROUND + BLACK;
    /** Code de couleur pour la mise en évidence (fond blanc et texte noir). */
    public static final String HIGHLIGHT = WHITE_BACKGROUND + BLACK;
}