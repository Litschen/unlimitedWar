package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player testPlayer;

    @BeforeEach
    void setUp(){
        testPlayer = new Player("blue", "testPlayer", new RandomBehavior());
    }

    @Test
    void calculateSoldiersToPlaceTestMin() {
        //owned countries = 0
        assertEquals(Player.COUNTRY_WEIGHT, testPlayer.calculateSoldiersToPlace());
    }

    @Test
    void calculateSoldiersToPlaceTest() {
        ArrayList<Country> owned = testPlayer.getOwnedCountries();
        for(int i = 0; i < 15 ; i++){
            owned.add(new Country("test", 1, testPlayer));
        }
        assertEquals(15 / Player.COUNTRY_WEIGHT, testPlayer.calculateSoldiersToPlace());
    }

}