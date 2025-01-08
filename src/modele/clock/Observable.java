package modele.clock;

/**
 * Méthode appelée pour mettre à jour les observateurs.
 */
public interface Observable {
    /**
     * Attache un observateur à l'objet observable.
     *
     * @param observateur L'observateur a attaché.
     */
    void attacher(Observateur observateur);

    /**
     * Détache un observateur de l'objet observable.
     *
     * @param observateur L'observateur à détaché.
     */
    void detacher(Observateur observateur);

    /**
     * Notifie tous les observateurs attachés de l'objet observable.
     */
    void notifierObservateur();
}
