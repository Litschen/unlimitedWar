package ch.zhaw.unlimitedWar.controller;

import ch.zhaw.unlimitedWar.dao.MySQLConnectionCreator;
import ch.zhaw.unlimitedWar.dao.PlayerDAO;
import ch.zhaw.unlimitedWar.model.UserBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController controller = new UserController();
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private UserBean mockUser;
    private MySQLConnectionCreator mockConCreator;
    private PlayerDAO mockDAO;

    @BeforeEach
    void setUp() {
        mockUser = mock(UserBean.class);
        mockDAO = mock(PlayerDAO.class);
        mockConCreator = mock(MySQLConnectionCreator.class);
        when(mockConCreator.getPlayerDAO()).thenReturn(mockDAO);

        mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("mockUser")).thenReturn(mockUser);
        when(mockRequest.getContextPath()).thenReturn("");
        when(mockRequest.getSession()).thenReturn(mockSession);

        mockResponse = mock(HttpServletResponse.class);
    }

    @Test
    void testSave() throws IOException {

        controller.doPost(mockRequest, mockResponse);
        verify(mockResponse, times(1)).sendRedirect("");
    }

    @Test
    void testCancel() throws IOException {

        controller.doPost(mockRequest, mockResponse);
        verify(mockResponse, times(1)).sendRedirect("");

    }

    @Test
    void testGetUserDataFromInputValid() throws IOException {

        controller.doPost(mockRequest, mockResponse);
        verify(mockResponse, times(1)).sendRedirect("");

    }

    @Test
    void testGetUserDataFromInputMissingArgs() throws IOException {

        controller.doPost(mockRequest, mockResponse);
        verify(mockResponse, times(1)).sendRedirect("");

    }

    @Test
    void testRegisterSuccess() throws IOException {

        controller.doPost(mockRequest, mockResponse);
        verify(mockResponse, times(1)).sendRedirect("");

    }

    @Test
    void testRegisterFail() throws IOException {

        controller.doPost(mockRequest, mockResponse);
        verify(mockResponse, times(1)).sendRedirect("");

    }
}
