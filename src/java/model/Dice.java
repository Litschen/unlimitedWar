package model;

import java.util.Collections;
import java.util.List;

public class Dice {

    //region static variables
    public static final int MAX_VALUE = 6;
    public static final int MIN_VALUE = 1;
    public static final int MAX_THROWS = 10;
    //endregion

    /**
     *
     * @param amountOfDice how many dice should be thrown
     * @return int array of results (range 1-6) sorted by by size descending
     */
    public List<Integer> roll(int amountOfDice) {

       //return  wert = (int) (Math.random() * 6 + 1);
       // return new int[]{-1};
       // @schrema8 List isch eifacher zum sortiere
        return null;
    }

    public Integer getHighestRoll(List<Integer> rolls){
        Collections.sort(rolls);
        Collections.reverse(rolls);
        return rolls.get(0);
    }
}
