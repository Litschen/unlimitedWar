package ch.zhaw.unlimitedWar.helpers;

import ch.zhaw.unlimitedWar.model.Card;
import ch.zhaw.unlimitedWar.model.Country;
import ch.zhaw.unlimitedWar.model.Player;
import ch.zhaw.unlimitedWar.model.enums.PlayerColor;
import ch.zhaw.unlimitedWar.model.helpers.PlaceSoldiers;

import java.util.ArrayList;
import java.util.Collections;
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

    public static List<Country> getCountryList(int numbersOfListElements, Player testPlayer) {
        List<Country> list = new ArrayList<>();
        for (int i = 0; i < numbersOfListElements; i++) {
            list.add(i, new Country("Polen", 5, testPlayer));
        }
        return list;
    }

    public static List<Country> getMockCountryList(int numbersOfListElements, Player testPlayer) {
        List<Country> countries = new ArrayList<>();
        for (int i = 0; i < numbersOfListElements; i++) {
            countries.add(getMockCountry(testPlayer));
        }
        return countries;
    }

    public static Player getMockPlayer() {
        Player mockPlayer = mock(Player.class);
        stubPlayerMethods(mockPlayer, null, 0, PlayerColor.RED);
        return mockPlayer;
    }

    public static void stubPlayerMethods(Player mockPlayer, List<Country> countries, int soldiersToPlace, PlayerColor color) {
        when(mockPlayer.getOwnedCountries()).thenReturn(countries);
        when(mockPlayer.getSoldiersToPlace()).thenReturn(soldiersToPlace);
        when(mockPlayer.getPlayerColor()).thenReturn(color);
    }

    public static List<Country> setUpToTestPlaceSoldiers(int numOfOwnedCountries, List<Integer> neighborsCount, Player player) {
        List<Country> ownedCountries = TestHelperBehavior.getCountryList(numOfOwnedCountries, player);

        Player opponent = TestHelperBehavior.getMockPlayer();
        for (int i = 0; i < neighborsCount.size(); i++) {
            List<Country> opponentCountries = TestHelperBehavior.getMockCountryList(neighborsCount.get(i), opponent);
            ownedCountries.get(i).addNeighboringCountries(opponentCountries);
        }

        return ownedCountries;
    }

    public static void countriesBorderingEachOther(List<Country> countries) {
        for (Country country : countries) {
            for (Country neighbor : countries) {
                if (!country.equals(neighbor)) {
                    country.addNeighboringCountries(Collections.singletonList(neighbor));
                }
            }
        }
    }

    public static PlaceSoldiers createPlaceSoldiers(Player player, List<Country> ownedCountries, int soldiersToPlace) {
        player.addOwnedCountries(ownedCountries);
        return new PlaceSoldiers(null, soldiersToPlace, player);
    }
}
