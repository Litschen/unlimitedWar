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

    }

    @Test
    public void maxAttackerDiceCount_Valid() {
        try {
            Country c = new Country("c", 2, testBoard.getPlayers().get(0));
            assertEquals(testBoard.maxAttackerDiceCount(c), 1);

            c.setSoldiersCount(3);
            assertEquals(testBoard.maxAttackerDiceCount(c), 2);

            c.setSoldiersCount(4);
            assertEquals(testBoard.maxAttackerDiceCount(c), 3);

            c.setSoldiersCount(10);
            assertEquals(testBoard.maxAttackerDiceCount(c), 3);

        } catch (Exception e) {
            System.out.println(e);
            assertTrue(false);
        }
    }

    @Test
    public void maxAttackerDiceCount_Invalid() {
        Country c = new Country("c", 1, testBoard.getPlayers().get(0));
        Exception exception = assertThrows(Exception.class, () -> testBoard.maxAttackerDiceCount(c));
        assertEquals("could not calculate maxAttackerDiceCount", exception.getMessage());
    }
}