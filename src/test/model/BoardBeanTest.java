package model;

import model.Enum.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void testAddSoldiersToCountry() {
        // prepare Data
        Country countryToSet = testBoard.getCurrentPlayer().getOwnedCountries().get(0);
        int countryIndex = testBoard.getCountries().indexOf(countryToSet);

        testBoard.setSoldiersToPlace(3);
        int initialSoldiers = countryToSet.getSoldiersCount();

        // execute test
        testBoard.addSoldiersToCountry(countryIndex);
        testBoard.addSoldiersToCountry(countryIndex);
        assertEquals(1, testBoard.getSoldiersToPlace());
        assertEquals(Phase.SETTINGPHASE, testBoard.getCurrentPhase());

        testBoard.addSoldiersToCountry(countryIndex);
        assertEquals(0, testBoard.getSoldiersToPlace());
        assertEquals(initialSoldiers + 3, countryToSet.getSoldiersCount());
        assertEquals(Phase.ATTACKPHASE, testBoard.getCurrentPhase());
    }

    @Test
    public void testAddSoldiersToCountryNotOwnedCountry() {
        // prepare data
        int countryIndex = 0;
        boolean notOwnedCountry = false;

        // get a not owned country
        for (int i = 0; !notOwnedCountry; i++) {
            countryIndex = i;
            notOwnedCountry = !testBoard.getCurrentPlayer().getOwnedCountries().contains(testBoard.getCountries().get(i));
        }

        testBoard.setSoldiersToPlace(3);
        Country country = testBoard.getCountries().get(countryIndex);
        int initialSoldiers = country.getSoldiersCount();

        // execute test
        testBoard.addSoldiersToCountry(countryIndex);
        assertEquals(3, testBoard.getSoldiersToPlace());
        assertEquals(Phase.SETTINGPHASE, testBoard.getCurrentPhase());
        assertEquals(initialSoldiers, country.getSoldiersCount());
    }

    @Test
    public void testAddSoldiersToCountryNegativeValue() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> testBoard.addSoldiersToCountry(-1));
    }

}