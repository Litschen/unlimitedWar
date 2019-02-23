package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardBeanTest {

    private BoardBean testBoard;

    @BeforeEach
    void setUp() {
        testBoard = new BoardBean();
    }

    @Test
    void testGenerateCountries(){
        int ownedCountriesPrevious = testBoard.getPlayers().get(0).getOwnedCountries().size();
        for(Player currentPlayer : testBoard.getPlayers()){
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