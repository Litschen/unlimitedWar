package model.helpers;

import java.util.Comparator;

public class MoveComperator implements Comparator<MoveCountry> {


    @Override
    public int compare(MoveCountry own, MoveCountry neighbor) {
        return Integer.compare(neighbor.getNumberOfNeighbors(), own.getNumberOfNeighbors());
    }
}
