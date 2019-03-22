package controller;

import model.Board;
import model.Country;
import org.junit.jupiter.api.BeforeEach;


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

    private HttpServletRequest mockRequest; //
    private HttpServletResponse mockResponse;//
    private Board mockBoard;//
    private HttpSession mockSession;
    private GameController controller = new GameController();
    private Country mockCountry;

    private static final String NOT_EMPTY = "None empty value";

    @BeforeEach
    void setUp() {

    }


}