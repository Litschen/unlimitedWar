package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {

    //region static variables
    public static final int MAX_VALUE = 6;
    public static final int MIN_VALUE = 1;
    public static final String INVALID_AMOUNT = "amount of dice has to be > 0";
    //endregion

    /**
     * throw a dice (range MIN_VALUE - MAX_VALUE)
     *
     * @return Random number in above range
     */
    public static int roll() {
        return roll(MIN_VALUE, MAX_VALUE);
    }

    /**
     * throw a given amount of dice range MIN_VALUE - MAX_VALUE)and save the results in an array.
     * the results are sorted in descending order.
     *
     * @param amountOfDice how many dice should be thrown
     * @return int array of results sorted descending
     */
    public static int[] roll(int amountOfDice) {
        if (amountOfDice < 0) {
            throw new IllegalArgumentException(INVALID_AMOUNT);
        }

        List<Integer> dices = new ArrayList<>();
        for (int i = 0; i < amountOfDice; i++) {
            dices.add(Dice.roll());
        }
        Collections.sort(dices);
        Collections.reverse(dices);

        return toIntArray(dices);
    }

    /**
     * generate a random number between the passed min and max value (inclusive)
     *
     * @param min minimum value of the result
     * @param max maximum value of the result
     * @return generated number
     */
    public static int roll(int min, int max) {
        if (min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * convert a List of Integer to an int array
     *
     * @param list list of integers
     * @return int[] with all elements of the passed list
     */
    private static int[] toIntArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        return array;
    }

}
