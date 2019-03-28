package model.helpers;

import model.Country;

public class AttackScore {
    private int score;
    private Country attacker;
    private Country defender;

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
