package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestHelperCountry {

    public static List<Integer> setUpRolls(int throwValue, int amountOfDice) {
        List<Integer> throwResults = new ArrayList<>();
        for (int i = 0; i < amountOfDice; i++) {
            throwResults.add(throwValue);
        }
        return throwResults;
    }
}

