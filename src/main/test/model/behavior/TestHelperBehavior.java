package java.model.behavior;

import java.model.Country;
import java.model.Player;
import java.model.enums.PlayerColor;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHelperBehavior {


    public static Country getMockCountry(Player player) {
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
            countries.add(getMockCountry(testPlayer));
        }
        return countries;
    }


    public static Player getMockPlayer() {
        Player mockPlayer = mock(Player.class);
        return mockPlayer;
    }

    public static void stubPlayerMethods(Player mockPlayer, List<Country> countries, int soldiersToPlace, PlayerColor color) {
        when(mockPlayer.getOwnedCountries()).thenReturn(countries);
        when(mockPlayer.getSoldiersToPlace()).thenReturn(soldiersToPlace);
        when(mockPlayer.getPlayerColor()).thenReturn(color);
    }
}
