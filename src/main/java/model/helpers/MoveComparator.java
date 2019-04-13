package model.helpers;

import java.util.Comparator;

public class MoveComparator implements Comparator<MoveCountry> {

    @Override
    public int compare(MoveCountry own, MoveCountry neighbor) {
        return Integer.compare(neighbor.getNumberOfNeighbors(), own.getNumberOfNeighbors());
    }

}
