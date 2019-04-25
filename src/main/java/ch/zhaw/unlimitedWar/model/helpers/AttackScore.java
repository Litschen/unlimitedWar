package ch.zhaw.unlimitedWar.model.helpers;

import ch.zhaw.unlimitedWar.model.Country;

public class AttackScore {
    //region data fields
    private final int score;
    private final Country attacker;
    private final Country defender;
    //endregion

    public AttackScore(int score, Country attacker, Country defender) {
        this.score = score;
        this.attacker = attacker;
        this.defender = defender;
    }

    public int getScore() {
        return score;
    }

    public Country getAttacker() {
        return attacker;
    }

    public Country getDefender() {
        return defender;
    }
}
