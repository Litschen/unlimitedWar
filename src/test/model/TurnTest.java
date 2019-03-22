package model;

import controller.GameController;
import model.behavior.UserBehavior;
import model.enums.Flag;
import model.enums.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


class TurnTest {

    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private Turn mockBoard;
    private HttpSession mockSession;
    private GameController controller = new GameController();
    private Country mockCountry;

    private static final String NOT_EMPTY = "None empty value";
    private Turn testBoard;

    @BeforeEach
    void setUp() {
        mockRequest = mock(HttpServletRequest.class);
        mockBoard = mock(Turn.class);
        mockResponse = mock(HttpServletResponse.class);
        mockSession = mock(HttpSession.class);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getRequestDispatcher(GameController.PAGE_TO_LOAD_ON_COMPLETE))
                .thenReturn(mock(RequestDispatcher.class));
        when(mockSession.getAttribute(GameController.SESSION_BOARD_NAME)).thenReturn(mockBoard);
        mockCountry = mock(Country.class);
        when(mockBoard.controller.getCountryById(1)).thenReturn(mockCountry);
        when(mockRequest.getParameter(GameController.PARAM_COUNTRY)).thenReturn("1");
    }

    @Test
    void executeTurnIsCalledTest() {
        when(mockRequest.getPathInfo()).thenReturn(NOT_EMPTY);
        when(mockRequest.getParameter(GameController.PARAM_NEXT_TURN)).thenReturn(NOT_EMPTY);
        when(mockBoard.currentPlayerIsUser()).thenReturn(false);
        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockBoard, times(1)).executeTurn();
            controller.doGet(mockRequest, mockResponse);
            verify(mockBoard, times(2)).executeTurn();
            verify(mockBoard, never()).executeUserTurn(any());
            verify(mockBoard, never()).moveToNextPhase();

        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void testSetAttackAndDefendCountry() {
        Country testCountry = testBoard.getCurrentPlayer().getOwnedCountries().get(0);
        testCountry.setSoldiersCount(3);

        Country testCountryDefend = testBoard.controller.getPlayers().get(1).getOwnedCountries().get(1);
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
        int amountPlayer = testBoard.controller.getPlayers().size();

        testBoard.moveToNextPhase();
        assertEquals(Phase.ATTACKPHASE, testBoard.getCurrentPhase());
        assertEquals(p, testBoard.getCurrentPlayer());

        testBoard.moveToNextPhase();
        assertEquals(Phase.MOVINGPHASE, testBoard.getCurrentPhase());
        assertEquals(p, testBoard.getCurrentPlayer());

        testBoard.moveToNextPhase();
        assertEquals(Phase.SETTINGPHASE, testBoard.getCurrentPhase());
        assertNotEquals(p, testBoard.getCurrentPlayer());

        assertEquals(amountPlayer, testBoard.controller.getPlayers().size());
    }


    @Test
    void testMoveToNextPhaseRemoveUser() {
        List<Player> players = testBoard.controller.getPlayers();
        int amountPlayer = players.size();
        testBoard.setCurrentPhase(Phase.MOVINGPHASE);

        Player curPlayer = testBoard.getCurrentPlayer();
        Player losePlayer = testBoard.controller.getPlayers().get(1);
        curPlayer.getOwnedCountries().addAll(losePlayer.getOwnedCountries());
        losePlayer.getOwnedCountries().clear();

        testBoard.moveToNextPhase();

        assertEquals(amountPlayer - 1, testBoard.controller.getPlayers().size());
    }


    @Test
    void moveToNextPhaseIsCalledTest() {
        when(mockRequest.getPathInfo()).thenReturn(NOT_EMPTY);
        when(mockRequest.getParameter(GameController.PARAM_END)).thenReturn(NOT_EMPTY);
        when(mockBoard.currentPlayerIsUser()).thenReturn(false);
        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockBoard, times(1)).moveToNextPhase();
            controller.doGet(mockRequest, mockResponse);
            verify(mockBoard, times(2)).moveToNextPhase();
            verify(mockBoard, never()).executeUserTurn(any());
            verify(mockBoard, never()).executeTurn();

        } catch (ServletException | IOException e) {
            fail();
        }
    }


    @Test
    void executeUserTurnIsCalledTest() {
        when(mockBoard.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getParameter(GameController.PARAM_COUNTRY)).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn(NOT_EMPTY);

        //Turn is called at all
        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockBoard, times(1)).executeUserTurn(mockCountry);
            controller.doGet(mockRequest, mockResponse);
            verify(mockBoard, times(2)).executeUserTurn(mockCountry);

        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void attackDiceAreSetTest() {
        Map<String, String[]> mockMap = mock(Map.class);
        Player mockPlayer = mock(Player.class);

        when(mockBoard.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getPathInfo()).thenReturn(GameController.PATH_ATTACK);
        when(mockRequest.getParameterMap()).thenReturn(mockMap);
        when(mockRequest.getParameter(GameController.PARAM_ROLL)).thenReturn(NOT_EMPTY);
        when(mockMap.get(GameController.PARAM_ATTACK_DICE)).thenReturn(new String[3]);
        when(mockBoard.getCurrentPlayer()).thenReturn(mockPlayer);

        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockPlayer, times(1)).setAttackDiceCount(3);
        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void cancelAttackIsCalledTest() {
        when(mockBoard.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getPathInfo()).thenReturn(GameController.PATH_ATTACK);
        when(mockRequest.getParameter(GameController.PARAM_CANCEL)).thenReturn(NOT_EMPTY);
        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockBoard, times(1)).resetSelectedCountries();
        } catch (ServletException | IOException e) {
            fail();
        }
    }


    @Test
    void testResetSelectedCountries() {
        Player player = testBoard.getCurrentPlayer();
        testBoard.setFirstSelectedCountry(new Country("c1", 1, player));
        testBoard.setSecondSelectedCountry(new Country("c2", 6, player));
        testBoard.setFlag(Flag.ATTACK);

        assertTrue(testBoard.getFirstSelectedCountry().isSelected());
        assertTrue(testBoard.getSecondSelectedCountry().isSelected());

        testBoard.resetSelectedCountries();
        assertNull(testBoard.getFirstSelectedCountry());
        assertNull(testBoard.getSecondSelectedCountry());
        assertEquals(Flag.NONE, testBoard.getFlag());
    }


    @Test
    void testEliminatePlayersAndCheckUserResultWin() {
        for (Player player : testBoard.controller.getPlayers()) {
            player.getOwnedCountries().clear();
        }

        Player user = testBoard.controller.getPlayers().stream().filter(o -> ((Player) o).getBehavior() instanceof UserBehavior).findFirst().get();
        user.getOwnedCountries().addAll(testBoard.controller.getCountries());

        assertEquals(4, testBoard.controller.getPlayers().size());
        assertEquals(Flag.NONE, testBoard.getFlag());

        testBoard.eliminatePlayersAndCheckUserResult();
        assertEquals(1, testBoard.controller.getPlayers().size());
        assertEquals(Flag.GAME_WIN, testBoard.getFlag());
    }

    @Test
    void testEliminatePlayersAndCheckUserResultLose() {
        Player user = testBoard.controller.getPlayers().stream().filter(o -> ((Player) o).getBehavior() instanceof UserBehavior).findFirst().get();
        user.getOwnedCountries().clear();
        assertEquals(4, testBoard.controller.getPlayers().size());
        assertEquals(Flag.NONE, testBoard.getFlag());

        testBoard.eliminatePlayersAndCheckUserResult();
        assertEquals(3, testBoard.controller.getPlayers().size());
        assertEquals(Flag.GAME_LOSE, testBoard.getFlag());
    }


    @Test
    void testFinishGame() {
        when(mockBoard.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getPathInfo()).thenReturn(GameController.PATH_RESULT);

        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockBoard, times(1)).currentPlayerIsUser();
            verifyNoMoreInteractions(mockBoard);
            verify(mockResponse, times(1)).sendRedirect(anyString());
        } catch (ServletException | IOException e) {
            fail();
        }
    }
}