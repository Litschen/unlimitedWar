package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {

    //region static variables
    public static final int MAX_VALUE = 6;
    public static final int MIN_VALUE = 1;
    public static final int MAX_THROWS = 10;
    //endregion


    /**
     * @return Random number between 1 and 6
     */
    public static int roll() {
        return roll(MIN_VALUE, MAX_VALUE);
    }

    /**
     * @param amountOfDice how many dice should be thrown
     * @return int array of results (range 1-6) sorted by by size descending
     */
    public static int[] roll(int amountOfDice) {
        List<Integer> dices = new ArrayList<>();
        int i = 0;
        while (i < amountOfDice) {
            dices.add(Dice.roll());
            i++;
        }
        Collections.sort(dices);
        Collections.reverse(dices);

        return toIntArray(dices);
    }

    /**
     * @param min
     * @param max
     * @return number between min and max
     */
    public static int roll(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private static int[] toIntArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        return array;
    }

}
