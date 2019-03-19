package model.behavior;

import model.Country;
import model.Player;

import java.util.ArrayList;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHelperBehavior {


    public static Country setUpMockCountry(Player player) {
        Country mockCountry = mock(Country.class);
        when(mockCountry.getSoldiersCount()).thenReturn(5);
        when(mockCountry.canInvade(anyObject())).thenReturn(true);
        when(mockCountry.getOwner()).thenReturn(player);

        return mockCountry;
    }

    public static ArrayList<Country> getCountryList(int numbersOfListElements, Player testPlayer) {
        ArrayList<Country> list = new ArrayList<>();
        for (int i = 0; i < numbersOfListElements; i++) {
            list.add(i, new Country("Polen", 5, testPlayer));
        }
        return list;
    }

    public static ArrayList<Country> getMockCountryList(int numbersOfListElements, Player testPlayer) {
        ArrayList<Country> countries = new ArrayList<>();
        for (int i = 0; i < numbersOfListElements; i++) {
            countries.add(setUpMockCountry(testPlayer));
        }
        return countries;
    }


    public static Player setUpMockPlayer() {
        Player mockPlayer = mock(Player.class);
        return mockPlayer;
    }
}
