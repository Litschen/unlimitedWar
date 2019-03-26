package java.model;

import java.model.behavior.TestHelperBehavior;
import java.model.behavior.UserBehavior;
import java.model.enums.Flag;
import java.model.enums.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class TurnTest {

    private Turn turn;
    private Player mockPlayer;
    private Player mockPlayer0;
    private Player mockPlayer1;
    private List<Country> countries;

    @BeforeEach
    void setUp() {

        mockPlayer = mock(Player.class);
        mockPlayer0 = mock(Player.class);
        mockPlayer1 = mock(Player.class);
        List<Player> mockPlayers = new ArrayList<>();
        mockPlayers.add(mockPlayer);
        mockPlayers.add(mockPlayer0);
        mockPlayers.add(mockPlayer1);
        when(mockPlayer.getBehavior()).thenReturn(new UserBehavior());
        countries = TestHelperBehavior.getCountryList(5, mockPlayer);
        when(mockPlayer.getOwnedCountries()).thenReturn(countries);
        when(mockPlayer0.getOwnedCountries()).thenReturn(TestHelperBehavior.getCountryList(4, mockPlayer0));

        turn = new Turn(mockPlayers, countries);

    }

    @Test
    void testSetAttackAndDefendCountry() {
        Country testCountry = turn.getCurrentPlayer().getOwnedCountries().get(0);
        testCountry.setSoldiersCount(3);

        Country testCountryDefend = turn.getActivePlayers().get(0).getOwnedCountries().get(1);
        testCountry.getNeighboringCountries().add(testCountryDefend);

        assertNull(turn.getFirstSelectedCountry());
        assertNull(turn.getSecondSelectedCountry());
        assertSame(Flag.NONE, turn.getFlag());


        turn.setAttackAndDefendCountry(testCountry);
        assertSame(testCountry, turn.getFirstSelectedCountry());
        assertNull(turn.getSecondSelectedCountry());
        assertSame(turn.getFlag(), Flag.NONE);


        turn.setAttackAndDefendCountry(testCountryDefend);
        assertSame(testCountry, turn.getFirstSelectedCountry());
        assertSame(testCountryDefend, turn.getSecondSelectedCountry());
        assertSame(Flag.ATTACK, turn.getFlag());
    }


    @Test
    void testMoveToNextPhase() {
        turn.setCurrentPhase(Phase.SETTINGPHASE);
        Player p = turn.getCurrentPlayer();
        int amountPlayer = turn.getActivePlayers().size();

        turn.moveToNextPhase();
        assertEquals(Phase.ATTACKPHASE, turn.getCurrentPhase());
        assertEquals(p, turn.getCurrentPlayer());

        turn.moveToNextPhase();
        assertEquals(Phase.MOVINGPHASE, turn.getCurrentPhase());
        assertEquals(p, turn.getCurrentPlayer());

        turn.moveToNextPhase();
        assertEquals(Phase.SETTINGPHASE, turn.getCurrentPhase());
        assertNotEquals(p, turn.getCurrentPlayer());

        assertEquals(amountPlayer, turn.getActivePlayers().size());
    }


    @Test
    void testMoveToNextPhaseRemoveUser() {
        List<Player> players = turn.getActivePlayers();
        int amountPlayer = players.size();
        turn.setCurrentPhase(Phase.MOVINGPHASE);

        Player curPlayer = turn.getCurrentPlayer();
        Player losePlayer = turn.getActivePlayers().get(1);
        curPlayer.getOwnedCountries().addAll(losePlayer.getOwnedCountries());
        losePlayer.getOwnedCountries().clear();

        countries.clear();

        turn.moveToNextPhase();

        assertEquals(amountPlayer - 1, turn.getActivePlayers().size());
    }



    @Test
    void testResetSelectedCountries() {
        turn.setFirstSelectedCountry(new Country("c1", 1, mockPlayer));
        turn.setSecondSelectedCountry(new Country("c2", 6, mockPlayer));
        turn.setFlag(Flag.ATTACK);

        assertTrue(turn.getFirstSelectedCountry().isSelected());
        assertTrue(turn.getSecondSelectedCountry().isSelected());

        turn.resetSelectedCountries();
        assertNull(turn.getFirstSelectedCountry());
        assertNull(turn.getSecondSelectedCountry());
        assertEquals(Flag.NONE, turn.getFlag());
    }

    @Test
    void testEliminatePlayersAndCheckUserResultWin() {
        for (Player player : turn.getActivePlayers()) {
            player.getOwnedCountries().clear();
        }

        Player user = turn.getActivePlayers().stream().filter(o -> ((Player) o).getBehavior() instanceof UserBehavior).findFirst().get();
        user.getOwnedCountries().addAll(turn.getCountries());

        assertEquals(4, turn.getActivePlayers().size());
        assertEquals(Flag.NONE, turn.getFlag());

        turn.eliminatePlayersAndCheckUserResult();
        assertEquals(1, turn.getActivePlayers().size());
        assertEquals(Flag.GAME_WIN, turn.getFlag());
    }

    @Test
    void testEliminatePlayersAndCheckUserResultLose() {
        Player user = turn.getActivePlayers().stream().filter(o -> ((Player) o).getBehavior() instanceof UserBehavior).findFirst().get();
        user.getOwnedCountries().clear();
        assertEquals(4, turn.getActivePlayers().size());
        assertEquals(Flag.NONE, turn.getFlag());

        turn.eliminatePlayersAndCheckUserResult();
        assertEquals(3, turn.getActivePlayers().size());
        assertEquals(Flag.GAME_LOSE, turn.getFlag());
    }
}