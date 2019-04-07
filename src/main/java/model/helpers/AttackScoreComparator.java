package model.helpers;

import java.util.Comparator;

public class AttackScoreComparator implements Comparator<AttackScore> {

    @Override
    public int compare(AttackScore left, AttackScore right) {
        return Integer.compare(right.getScore(), left.getScore());
    }
}
