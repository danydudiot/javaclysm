package modele.entity.movable.character.npc.state;

import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe représentant l'état mort d'un personnage non-joueur (NPC).
 */
public class DeadState implements State {
    /**
     * Le personnage non-joueur associé à cet état.
     */
    protected NonPlayerCharacter npc;

    /**
     * Constructeur de la classe DeadState.
     *
     * @param npc Le personnage non-joueur associé à cet état.
     */
    public DeadState(NonPlayerCharacter npc) {
        this.npc = npc;
    }

    /**
     * Vérifie si le personnage peut se déplacer sur le terrain spécifié.
     *
     * @param terrain Le terrain à vérifier.
     * @return false car un personnage mort ne peut pas se déplacer.
     */
    @Override
    public boolean canMove(Terrain terrain) {
        return false;
    }

    /**
     * Met à jour l'état du personnage.
     * Cette méthode est vide car un personnage mort n'a pas d'état à mettre à jour.
     */
    @Override
    public void updateState() {

    }

    /**
     * Gère le déplacement du personnage.
     * Cette méthode est vide car un personnage mort ne peut pas se déplacer.
     */
    @Override
    public void deplacement() {

    }

    /**
     * Applique un modificateur de couleur à la représentation visuelle du personnage.
     *
     * @return Une chaîne de caractères indiquant que cet état ne devrait jamais être visible.
     */
    @Override
    public String applyColorModifier() {
        return "YOU SHOULD NEVER SEE THIS (DeadState)";
    }
}
