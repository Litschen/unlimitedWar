package controller;

import model.Country;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardBeanTest {

    private BoardBean toTest;

    @BeforeEach
    void setUp() {
        toTest = new BoardBean();
    }

    @Test
    void testGenerateCountries(){
        int ownedCountriesPrevious = toTest.getPlayers().get(0).getOwnedCountries().size();
        for(Player currentPlayer : toTest.getPlayers()){
            assertEquals(ownedCountriesPrevious, currentPlayer.getOwnedCountries().size());
            int soldierCount = 0;
            for(Country country : currentPlayer.getOwnedCountries())
            {
                soldierCount += country.getSoldiersCount();
            }
            assertEquals(BoardBean.START_SOLDIER_PER_PLAYER, soldierCount);
        }

    }
}