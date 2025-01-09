package modele.interaction;

/**
 * Interface représentant un objet interactif.
 */
public interface Interactible {

    /**
     * Obtient les interactions disponibles pour cet objet.
     *
     * @return Un tableau d'interactions disponibles.
     */
    Interaction[] getInteractions();
}
