package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestHelperCountry {

    public static List<Integer> CalculateRoll(int firstDiceThrow, int secondDiceThrow, int thirdDiceRoll) {
        if (secondDiceThrow == 0) {
            return new ArrayList<>(Arrays.asList(firstDiceThrow, null, thirdDiceRoll));
        } else if (thirdDiceRoll == 0) {
            return new ArrayList<>(Arrays.asList(firstDiceThrow, secondDiceThrow, null));
        } else {
            return new ArrayList<>(Arrays.asList(firstDiceThrow, secondDiceThrow, thirdDiceRoll));
        }
    }
}

