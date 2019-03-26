package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    private Board testBoard;

    @BeforeEach
    void setUp() {
        testBoard = new Board();
    }

    @Test
    void testGenerateCountries() {
        int ownedCountriesPrevious = testBoard.getPlayers().get(0).getOwnedCountries().size();
        for (Player currentPlayer : testBoard.getPlayers()) {
            assertEquals(ownedCountriesPrevious, currentPlayer.getOwnedCountries().size());
            int soldierCount = 0;
            for (Country country : currentPlayer.getOwnedCountries()) {
                soldierCount += country.getSoldiersCount();
            }
            assertEquals(Board.START_SOLDIER_PER_PLAYER, soldierCount);
        }
        assertEquals(Board.COUNTRY_COUNT_GENERATION, testBoard.getCountries().size());
    }

    /**
     * Only tests name setting, if bordering is set correctly is checked in CountrytTest
     */
    @Test
    void testSetCountryAttributes() {
        for (Country currentCountry : testBoard.getCountries()) {
            assertTrue(!currentCountry.getName().equals(""));
            assertTrue(currentCountry.getName().length() > 1);
        }
    }


}

