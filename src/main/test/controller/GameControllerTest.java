package controller;

import model.Board;
import model.Country;
import model.Player;
import model.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class GameControllerTest {

    private static final String NOT_EMPTY = "None empty value";
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private GameController controller = new controller.GameController();
    private Country mockCountry;
    private Turn mockTurn;

    @BeforeEach
    void setUp() {
        mockRequest = mock(HttpServletRequest.class);
        Board mockBoard = mock(Board.class);
        mockTurn = mock(Turn.class);
        mockResponse = mock(HttpServletResponse.class);
        HttpSession mockSession = mock(HttpSession.class);
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
    void testExecuteTurnIsCalled() {
        when(mockRequest.getPathInfo()).thenReturn(NOT_EMPTY);
        when(mockRequest.getParameter(GameController.PARAM_NEXT_TURN)).thenReturn(NOT_EMPTY);
        when(mockTurn.currentPlayerIsUser()).thenReturn(false);

        controller.doPost(mockRequest, mockResponse);
        verify(mockTurn, times(1)).executeTurn();
        controller.doGet(mockRequest, mockResponse);
        verify(mockTurn, times(2)).executeTurn();
        verify(mockTurn, never()).executeUserTurn(any());
        verify(mockTurn, never()).moveToNextPhase();
    }

    @Test
    void testMoveToNextPhaseIsCalled() {
        when(mockRequest.getPathInfo()).thenReturn(NOT_EMPTY);
        when(mockRequest.getParameter(GameController.PARAM_END)).thenReturn(NOT_EMPTY);
        when(mockTurn.currentPlayerIsUser()).thenReturn(false);

        controller.doPost(mockRequest, mockResponse);
        verify(mockTurn, times(1)).moveToNextPhase();
        controller.doGet(mockRequest, mockResponse);
        verify(mockTurn, times(2)).moveToNextPhase();
        verify(mockTurn, never()).executeUserTurn(any());
        verify(mockTurn, never()).executeTurn();
    }

    @Test
    void testExecuteUserTurnIsCalled() {
        when(mockTurn.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getParameter(GameController.PARAM_COUNTRY)).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn(NOT_EMPTY);

        //Turn is called at all
        controller.doPost(mockRequest, mockResponse);
        verify(mockTurn, times(1)).executeUserTurn(mockCountry);
        controller.doGet(mockRequest, mockResponse);
        verify(mockTurn, times(2)).executeUserTurn(mockCountry);
    }

    @Test
    void testAttackDiceAreSet() {
        Map<String, String[]> mockMap = mock(Map.class);
        Player mockPlayer = mock(Player.class);

        when(mockTurn.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getPathInfo()).thenReturn(GameController.PATH_ATTACK);
        when(mockRequest.getParameterMap()).thenReturn(mockMap);
        when(mockRequest.getParameter(GameController.PARAM_ROLL)).thenReturn(NOT_EMPTY);
        when(mockMap.get(GameController.PARAM_ATTACK_DICE)).thenReturn(new String[3]);
        when(mockTurn.getCurrentPlayer()).thenReturn(mockPlayer);
    }

    @Test
    void testCancelAttackIsCalled() {
        when(mockTurn.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getPathInfo()).thenReturn(GameController.PATH_ATTACK);
        when(mockRequest.getParameter(GameController.PARAM_CANCEL)).thenReturn(NOT_EMPTY);

        controller.doPost(mockRequest, mockResponse);
        verify(mockTurn, times(1)).resetSelectedCountries();
    }

    @Test
    void testFinishGame() throws Exception {
        when(mockTurn.currentPlayerIsUser()).thenReturn(true);
        when(mockRequest.getPathInfo()).thenReturn(GameController.PATH_RESULT);

        controller.doPost(mockRequest, mockResponse);
        verify(mockTurn, times(1)).currentPlayerIsUser();
        verifyNoMoreInteractions(mockTurn);
        verify(mockResponse, times(1)).sendRedirect(anyString());
    }
}