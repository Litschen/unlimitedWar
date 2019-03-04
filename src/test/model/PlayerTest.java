package model;

import model.Behaviors.RandomBehavior;
import model.Enum.ColorPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    private Player testPlayer;

    @BeforeEach
    void setUp() {
        testPlayer = new Player("blue", "testPlayer", new RandomBehavior());
    }

    @Test
    void testCalculateSoldiersToPlace() {
        ArrayList<Country> owned = testPlayer.getOwnedCountries();
        for (int i = 0; i < 15; i++) {
            owned.add(new Country("test", 1, testPlayer));
        }
        assertEquals(15 / Player.COUNTRY_WEIGHT, testPlayer.calculateSoldiersToPlace());
    }

    @Test
    void testCalculateSoldiersToPlaceMin() {
        //owned countries = 0
        assertEquals(Player.COUNTRY_WEIGHT, testPlayer.calculateSoldiersToPlace());
    }

}