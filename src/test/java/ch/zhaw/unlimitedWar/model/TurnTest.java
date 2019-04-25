package ch.zhaw.unlimitedWar.model;

import ch.zhaw.unlimitedWar.helpers.TestHelperBehavior;
import ch.zhaw.unlimitedWar.model.behavior.UserBehavior;
import ch.zhaw.unlimitedWar.model.enums.Flag;
import ch.zhaw.unlimitedWar.model.enums.Phase;
import ch.zhaw.unlimitedWar.model.enums.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TurnTest {

    private Turn turn;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        mockPlayer = mock(Player.class);
        Player mockPlayer0 = mock(Player.class);
        Player mockPlayer1 = mock(Player.class);
        List<Player> mockPlayers = new ArrayList<>();
        mockPlayers.add(mockPlayer);
        mockPlayers.add(mockPlayer0);
        mockPlayers.add(mockPlayer1);
        when(mockPlayer.getBehavior()).thenReturn(new UserBehavior());
        when(mockPlayer.getOwnedCountries()).thenReturn(TestHelperBehavior.getCountryList(5, mockPlayer));
        when(mockPlayer0.getOwnedCountries()).thenReturn(TestHelperBehavior.getCountryList(4, mockPlayer0));
        when(mockPlayer1.getOwnedCountries()).thenReturn(TestHelperBehavior.getCountryList(4, mockPlayer1));

        turn = new Turn(mockPlayers, TestHelperBehavior.getCountryList(5, mockPlayer), 0);
    }

    @Test
    void testSetAttackAndDefendCountry() {
        Country testCountry = turn.getCurrentPlayer().getOwnedCountries().get(0);
        testCountry.setSoldiersCount(3);

        Country testCountryDefend = turn.getActivePlayers().get(1).getOwnedCountries().get(1);
        testCountry.addNeighboringCountry(testCountryDefend);

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
        turn.setCurrentPhase(Phase.SET);
        Player p = turn.getCurrentPlayer();
        int amountPlayer = turn.getActivePlayers().size();

        turn.moveToNextPhase();
        assertEquals(Phase.ATTACK, turn.getCurrentPhase());
        assertEquals(p, turn.getCurrentPlayer());

        turn.moveToNextPhase();
        assertEquals(Phase.MOVE, turn.getCurrentPhase());
        assertEquals(p, turn.getCurrentPlayer());

        turn.moveToNextPhase();
        assertEquals(Phase.SET, turn.getCurrentPhase());
        assertNotEquals(p, turn.getCurrentPlayer());

        assertEquals(amountPlayer, turn.getActivePlayers().size());
    }

    @Test
    void testMoveToNextPhaseRemovePlayer() {
        List<Player> players = turn.getActivePlayers();
        int amountPlayer = players.size();
        turn.setCurrentPhase(Phase.MOVE);

        Player curPlayer = turn.getActivePlayers().get(1);
        Player losePlayer = turn.getActivePlayers().get(0);
        curPlayer.getOwnedCountries().addAll(losePlayer.getOwnedCountries());
        losePlayer.getOwnedCountries().clear();

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
        Board board = new Board(PlayerColor.BLUE, "Felix");
        turn = board.getCurrentTurn();
        Player user = null;
        for (Player player : turn.getActivePlayers()) {
            if (player.getBehavior() instanceof UserBehavior) {
                user = player;
            }
        }
        assertEquals(4, turn.getActivePlayers().size());
        assertEquals(Flag.NONE, turn.getFlag());
        for (Player player : turn.getActivePlayers()) {
            if (player != user) {
                player.getOwnedCountries().clear();
            }
        }
        turn.eliminatePlayersAndCheckUserResult();
        assertEquals(1, turn.getActivePlayers().size());
        assertEquals(Flag.GAME_WIN, turn.getFlag());
    }

    @Test
    void testEliminatePlayersAndCheckUserResultLose() {
        Board board = new Board(PlayerColor.BLUE, "Felix");
        turn = board.getCurrentTurn();
        Player user = null;
        for (Player player : turn.getActivePlayers()) {
            if (player.getBehavior() instanceof UserBehavior) {
                user = player;
            }
        }
        assertEquals(4, turn.getActivePlayers().size());
        assertEquals(Flag.NONE, turn.getFlag());
        for (Player player : turn.getActivePlayers()) {
            if (player == user) {
                player.getOwnedCountries().clear();
            }
        }
        turn.eliminatePlayersAndCheckUserResult();
        assertEquals(3, turn.getActivePlayers().size());
        assertEquals(Flag.GAME_LOSE, turn.getFlag());
    }
}