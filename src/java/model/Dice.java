package model;

import java.util.ArrayList;
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
     * @return int arraylist of results (range 1-6) sorted by by size descending
     */
    public ArrayList<Integer> roll(int amountOfDice) {

       //return  wert = (int) (Math.random() * 6 + 1);
       // return new int[]{-1};
       // @Tina List isch eifacher zum sortiere
        //@All ich wett das no bespreche
        return new ArrayList<>();
    }

    //isch da nid härt unnötig? es isch einfach s erste element?
    public Integer getHighestRoll(List<Integer> rolls){
        return null;
    }
}
