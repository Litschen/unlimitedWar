package model;

import model.Behaviors.UserBehavior;
import model.Enum.Flag;
import model.Enum.Phase;
import model.Enum.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        assertEquals(BoardBean.COUNTRY_COUNT_GENERATION, testBoard.getCountries().size());
    }

    /**
     * Only tests name setting, if bordering is set correctly is checked in Countrytest bordering test.
     */
    @Test
    void testSetCountryAttributes() {
        for(Country currentCountry : testBoard.getCountries()){
            assertTrue(!currentCountry.getName().equals(""));
            assertTrue(currentCountry.getName().length() > 1);
        }
    }

    @Test
    void testSetAttackAndDefendCountry() {
        Country testCountry = testBoard.getCurrentPlayer().getOwnedCountries().get(0);
        testCountry.setSoldiersCount(3);

        Country testCountryDefend = testBoard.getPlayers().get(1).getOwnedCountries().get(1);
        testCountry.getNeighboringCountries().add(testCountryDefend);

        assertNull(testBoard.getFirstSelectedCountry());
        assertNull(testBoard.getSecondSelectedCountry());
        assertSame(Flag.NONE, testBoard.getFlag());


        testBoard.setAttackAndDefendCountry(testCountry);
        assertSame(testCountry, testBoard.getFirstSelectedCountry());
        assertNull(testBoard.getSecondSelectedCountry());
        assertSame(testBoard.getFlag(), Flag.NONE);


        testBoard.setAttackAndDefendCountry(testCountryDefend);
        assertSame(testCountry, testBoard.getFirstSelectedCountry());
        assertSame(testCountryDefend, testBoard.getSecondSelectedCountry());
        assertSame(Flag.ATTACK, testBoard.getFlag());
    }


    @Test
    void testMoveToNextPhase() {
        testBoard.setCurrentPhase(Phase.SETTINGPHASE);
        Player p = testBoard.getCurrentPlayer();
        int amountPlayer = testBoard.getPlayers().size();

        testBoard.moveToNextPhase();
        assertEquals(Phase.ATTACKPHASE, testBoard.getCurrentPhase());
        assertEquals(p, testBoard.getCurrentPlayer());

        testBoard.moveToNextPhase();
        assertEquals(Phase.MOVINGPHASE, testBoard.getCurrentPhase());
        assertEquals(p, testBoard.getCurrentPlayer());

        testBoard.moveToNextPhase();
        assertEquals(Phase.SETTINGPHASE, testBoard.getCurrentPhase());
        assertNotEquals(p, testBoard.getCurrentPlayer());

        assertEquals(amountPlayer, testBoard.getPlayers().size());
    }

    @Test
    void testMoveToNextPhaseRemoveUser() {
        ArrayList<Player> players = testBoard.getPlayers();
        int amountPlayer = players.size();
        testBoard.setCurrentPhase(Phase.MOVINGPHASE);

        Player curPlayer = testBoard.getCurrentPlayer();
        Player losePlayer = testBoard.getPlayers().get(1);
        curPlayer.getOwnedCountries().addAll(losePlayer.getOwnedCountries());
        losePlayer.getOwnedCountries().clear();

        testBoard.moveToNextPhase();

        assertEquals(amountPlayer - 1, testBoard.getPlayers().size());
    }

    @Test
    void testResetSelectedCountries() {
        Player player = testBoard.getCurrentPlayer();
        testBoard.setFirstSelectedCountry(new Country("c1", 1, player));
        testBoard.setSecondSelectedCountry(new Country("c2", 6, player));
        testBoard.setFlag(Flag.ATTACK);

        testBoard.resetSelectedCountries();
        assertEquals(null, testBoard.getFirstSelectedCountry());
        assertEquals(null, testBoard.getSecondSelectedCountry());
        assertEquals(Flag.NONE, testBoard.getFlag());
    }
}

