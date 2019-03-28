package model.helpers;

import java.util.Comparator;

public class AttackScoreComperator implements Comparator<AttackScore> {

    @Override
    public int compare(AttackScore left, AttackScore right) {
        return Integer.compare(right.getScore(), left.getScore());
    }
}
