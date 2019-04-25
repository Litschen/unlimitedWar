package ch.zhaw.unlimitedWar.model;

import ch.zhaw.unlimitedWar.helpers.TestHelperBehavior;
import ch.zhaw.unlimitedWar.model.behavior.RandomBehavior;
import ch.zhaw.unlimitedWar.model.enums.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PlayerTest {

    private Player testPlayer;

    @BeforeEach
    void setUp() {
        List<PlayerColor> colorPlayer = new ArrayList<>(Arrays.asList(PlayerColor.values()));
        testPlayer = new Player(colorPlayer.remove(1), "testPlayer", new RandomBehavior());
    }

    @Test
    void testCalculateSoldiersToPlace() {
        fellowCountries(testPlayer.getOwnedCountries(), 1);
        assertEquals(3, testPlayer.calculateSoldiersToPlace(new ArrayList<>()));

        fellowCountries(testPlayer.getOwnedCountries(), 5);
        assertEquals(3, testPlayer.calculateSoldiersToPlace(new ArrayList<>()));

        fellowCountries(testPlayer.getOwnedCountries(), 10);
        assertEquals(3, testPlayer.calculateSoldiersToPlace(new ArrayList<>()));

        fellowCountries(testPlayer.getOwnedCountries(), 12);
        assertEquals(4, testPlayer.calculateSoldiersToPlace(new ArrayList<>()));

        fellowCountries(testPlayer.getOwnedCountries(), 12);
        assertEquals(4, testPlayer.calculateSoldiersToPlace(new ArrayList<>()));

        fellowCountries(testPlayer.getOwnedCountries(), 30);
        assertEquals(10, testPlayer.calculateSoldiersToPlace(new ArrayList<>()));
    }

    @Test
    void testCalculateSoldiersToPlaceContinentBonus() {
        fellowCountries(testPlayer.getOwnedCountries(), 1);
        Continent continent = new Continent(10, testPlayer.getOwnedCountries(), "test");
        assertEquals(10 + Player.COUNTRY_WEIGHT, testPlayer.calculateSoldiersToPlace(Collections.singletonList(continent)));

    }

    @Test
    void testCalculateSoldiersToPlaceContinentNotOwned() {
        fellowCountries(testPlayer.getOwnedCountries(), 1);
        Continent continent = new Continent(10, testPlayer.getOwnedCountries(), "test");
        continent.addCountry(TestHelperBehavior.getMockCountry(mock(Player.class)));
        assertEquals(Player.COUNTRY_WEIGHT, testPlayer.calculateSoldiersToPlace(Collections.singletonList(continent)));

    }

    @Test
    void testCalculateSoldiersToPlaceMin() {
        //owned countries = 0
        assertEquals(Player.COUNTRY_WEIGHT, testPlayer.calculateSoldiersToPlace(new ArrayList<>()));
    }

    private void fellowCountries(List<Country> owned, int amountOfCountries){
        owned.clear();
        for (int i = 0; i < amountOfCountries; i++) {
            owned.add(new Country("test", 1, testPlayer));
        }
    }

}