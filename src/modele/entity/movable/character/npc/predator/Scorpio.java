package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.ScorpioRaidState;
import modele.interaction.Interaction;

/**
 * Classe représentant un scorpion prédateur dans le jeu.
 */
public class Scorpio extends Predator {
    /**
     * Indique si le scorpion peut attaquer.
     */
    protected int canAttack;
    /**
     * Temps restant sous une roche.
     */
    protected int timeUnderRock;

    /**
     * Constructeur de la classe Scorpio.
     * Initialise la position du scorpion, son état initial, sa représentation visuelle, son nom d'affichage et ses capacités d'attaque.
     *
     * @param x La coordonnée X du scorpion.
     * @param y La coordonnée Y du scorpion.
     */
    public Scorpio(int x, int y) {
        super(x, y);
        this.currentState = new ScorpioRaidState(this);
        this.representation = "O";
        this.displayName = "Scorpion";
        this.canAttack = 0;
        this.interactionList = new Interaction[0];
    }

    /**
     * Méthode appelée après qu'un scorpion ait été attaqué.
     * Réinitialise la capacité d'attaque du scorpion.
     *
     * @param killed Indique si le scorpion a été tué.
     */
    @Override
    public void afterHit(boolean killed) {
        canAttack = 2;
    }

    /**
     * Vérifie si le scorpion peut attaquer.
     *
     * @return true si le scorpion peut attaquer, sinon false.
     */
    public boolean canAttack() {
        return canAttack <= 0;
    }

    /**
     * Obtient la capacité d'attaque actuelle du scorpion.
     *
     * @return La capacité d'attaque actuelle.
     */
    public int getCanAttack() {
        return canAttack;
    }

    /**
     * Définit la capacité d'attaque du scorpion.
     *
     * @param canAttack La nouvelle capacité d'attaque.
     */
    public void setCanAttack(int canAttack) {
        this.canAttack = canAttack;
    }

    /**
     * Obtient le temps restant sous une roche par le scorpion.
     *
     * @return Le temps restant sous une roche.
     */
    public int getTimeUnderRock() {
        return timeUnderRock;
    }

    /**
     * Définit le temps restant sous une roche par le scorpion.
     *
     * @param timeUnderRock Le nouveau temps restant sous une roche.
     */
    public void setTimeUnderRock(int timeUnderRock) {
        this.timeUnderRock = timeUnderRock;
    }

    /**
     * Met à jour l'état du scorpion en décrémentant sa capacité d'attaque et en appelant la méthode de mise à jour de l'état de la classe parente.
     */
    @Override
    public void mettreAJour() {
        canAttack--;
        super.mettreAJour();
    }
}