package java.controller;

import java.model.Board;
import java.model.Country;
import java.model.Player;
import java.model.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class GameControllerTest {

    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private Board mockBoard;
    private HttpSession mockSession;
    private GameController controller = new GameController();
    private Country mockCountry;
    private Turn mockTurn;

    private static final String NOT_EMPTY = "None empty value";

    @BeforeEach
    void setUp(){
        mockRequest  = mock(HttpServletRequest.class);
        mockBoard = mock(Board.class);
        mockTurn = mock(Turn.class);
        mockResponse = mock(HttpServletResponse.class);
        mockSession = mock(HttpSession.class);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getRequestDispatcher(GameController.PAGE_TO_LOAD_ON_COMPLETE))
                .thenReturn(mock(RequestDispatcher.class));
        when(mockSession.getAttribute(GameController.SESSION_BOARD_NAME)).thenReturn(mockBoard);
        mockCountry = mock(Country.class);
        when(mockBoard.getCountryById(1)).thenReturn(mockCountry);
        when(mockRequest.getParameter(GameController.PARAM_COUNTRY)).thenReturn("1");
        when(mockBoard.getCurrentTurn()).thenReturn(mockTurn);
    }

    @Test
    void executeTurnIsCalledTest(){
        when(mockRequest.getPathInfo()).thenReturn(NOT_EMPTY);
        when(mockRequest.getParameter(GameController.PARAM_NEXT_TURN)).thenReturn(NOT_EMPTY);
        when(mockTurn.currentPlayerIsUser()).thenReturn(false);
        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockTurn, times(1)).executeTurn();
            controller.doGet(mockRequest, mockResponse);
            verify(mockTurn, times(2)).executeTurn();
            verify(mockTurn, never()).executeUserTurn(any());
            verify(mockTurn, never()).moveToNextPhase();

        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void moveToNextPhaseIsCalledTest(){
        when(mockRequest.getPathInfo()).thenReturn(NOT_EMPTY);
        when(mockRequest.getParameter(GameController.PARAM_END)).thenReturn(NOT_EMPTY);
        when(mockTurn.currentPlayerIsUser()).thenReturn(false);
        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockTurn, times(1)).moveToNextPhase();
            controller.doGet(mockRequest, mockResponse);
            verify(mockTurn, times(2)).moveToNextPhase();
            verify(mockTurn, never()).executeUserTurn(any());
            verify(mockTurn, never()).executeTurn();

        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void executeUserTurnIsCalledTest(){
        when(mockTurn.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getParameter(GameController.PARAM_COUNTRY)).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn(NOT_EMPTY);

        //Turn is called at all
        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockTurn, times(1)).executeUserTurn(mockCountry);
            controller.doGet(mockRequest, mockResponse);
            verify(mockTurn, times(2)).executeUserTurn(mockCountry);

        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void attackDiceAreSetTest(){
        Map<String, String[]> mockMap = mock(Map.class);
        Player mockPlayer = mock(Player.class);

        when(mockTurn.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getPathInfo()).thenReturn(GameController.PATH_ATTACK);
        when(mockRequest.getParameterMap()).thenReturn(mockMap);
        when(mockRequest.getParameter(GameController.PARAM_ROLL)).thenReturn(NOT_EMPTY);
        when(mockMap.get(GameController.PARAM_ATTACK_DICE)).thenReturn(new String[3]);
        when(mockTurn.getCurrentPlayer()).thenReturn(mockPlayer);

        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockPlayer, times(1)).setAttackDiceCount(3);
        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void cancelAttackIsCalledTest(){
        when(mockTurn.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getPathInfo()).thenReturn(GameController.PATH_ATTACK);
        when(mockRequest.getParameter(GameController.PARAM_CANCEL)).thenReturn(NOT_EMPTY);
        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockTurn, times(1)).resetSelectedCountries();
        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void testFinishGame(){
        when(mockTurn.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getPathInfo()).thenReturn(GameController.PATH_RESULT);

        try {
            controller.doPost(mockRequest, mockResponse);
            verify(mockTurn, times(1)).currentPlayerIsUser();
            verifyNoMoreInteractions(mockTurn);
            verify(mockResponse, times(1)).sendRedirect(anyString());
        } catch (ServletException | IOException e) {
            fail();
        }
    }
}