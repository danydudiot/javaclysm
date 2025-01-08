package modele.entity.movable.character.npc.state;

import modele.entity.stationary.terrain.Terrain;

/**
 * Interface représentant l'état d'un personnage non-joueur (NPC).
 */
public interface State {
    /**
     * Met à jour l'état du personnage.
     */
    void updateState();

    /**
     * Gère le déplacement du personnage.
     */
    void deplacement();

    /**
     * Vérifie si le personnage peut se déplacer sur le terrain spécifié.
     * @param terrain Le terrain à vérifier.
     * @return true si le personnage peut se déplacer sur le terrain, sinon false.
     */
    boolean canMove(Terrain terrain);

    /**
     * Applique un modificateur de couleur à la représentation visuelle du personnage.
     * @return Le modificateur de couleur sous forme de chaîne de caractères.
     */
    String applyColorModifier();
}