package model;

import model.Enum.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardBeanTest {

    private BoardBean testBoard;

    @BeforeEach
    void setUp() {
        testBoard = new BoardBean();
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
            assertEquals(BoardBean.START_SOLDIER_PER_PLAYER, soldierCount);
        }
        assertEquals(BoardBean.COUNTRY_COUNT_GENERATION, testBoard.getCountries().size());
    }

    @Test
    void testSetCountryAttributes() {

    }


    @Test
    void testCurrentPlayerIsUser() {
    }

    @Test
    void testExecuteTurn() {
    }

    @Test
    void testExecuteUserTurn() {
    }

    @Test
    void testSetAttackAndDefendCountry() {
    }


    @Test
    void testMoveToNextPhase() {
    }

    @Test
    void testResetSelectedCountries() {
    }
}

