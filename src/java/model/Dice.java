package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {

    //region static variables
    public static final int MAX_VALUE = 6;
    public static final int MIN_VALUE = 1;
    public static final int MAX_THROWS = 10;
    //endregion

    /**
     * @param amountOfDice how many dice should be thrown
     * @return int array of results (range 1-6) sorted by by size descending
     */

    public static int[] roll(int amountOfDice) {
        int[] dices = new int[amountOfDice];
        int i = 0;
        while (i < dices.length) {
            dices[i] = Dice.roll();
            i++;
        }
        Arrays.sort(dices);
        Collections.reverse(Arrays.asList(dices));
        return dices;
    }

    /**
     * @return Random number between 1 and 6
     */
    public static int roll() {
        return roll(MIN_VALUE, MAX_VALUE);
    }

    /**
     * @param min
     * @param max
     * @return number between min and max
     */
    public static int roll(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }


}
