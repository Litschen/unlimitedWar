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
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController controller = new UserController();
    private HttpServletRequest mockRequest;
    private HttpSession mockSession;
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
        controller.setConnectionCreator(mockConCreator);

        mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getContextPath()).thenReturn("");
        createMockSession();

        mockResponse = mock(HttpServletResponse.class);
    }

    @Test
    void testSave() throws IOException, SQLException {
        setUpUserParams();
        when(mockRequest.getParameter("register")).thenReturn(null);
        when(mockRequest.getParameter("save")).thenReturn("save");
        when(mockDAO.updatePlayer(anyString(), anyString(), anyString())).thenReturn(1);

        controller.doGet(mockRequest, mockResponse);
        verify(mockResponse, times(1)).sendRedirect(Consts.SIGN_IN);
        verify(mockSession, times(3)).setAttribute(anyString(), anyObject());
    }

    @Test
    void testSaveMultiple() throws IOException, SQLException {
        setUpUserParams();
        when(mockRequest.getParameter("register")).thenReturn(null);
        when(mockRequest.getParameter("save")).thenReturn("save");
        when(mockDAO.updatePlayer(anyString(), anyString(), anyString())).thenReturn(2);

        controller.doGet(mockRequest, mockResponse);
        verify(mockResponse, times(1)).sendRedirect(Consts.SIGN_IN);
        verify(mockSession, times(3)).setAttribute(anyString(), anyObject());
        assertEquals(1, controller.getEvents().size());
    }

    @Test
    void testCancel() throws IOException {
        when(mockRequest.getParameter("cancel")).thenReturn("b");

        controller.doPost(mockRequest, mockResponse);

        assertEquals(0, controller.getEvents().size());
        verify(mockRequest, times(2)).getSession();
        verify(mockSession, times(2)).setAttribute(anyString(), anyObject());
        verify(mockResponse, times(1)).sendRedirect(Consts.SIGN_IN);
    }

    @Test
    void testGetUserDataFromInputValid() throws IOException {
        setUpUserParams();

        controller.getUserDataFromInput(mockRequest);

        assertEquals(0, controller.getEvents().size());
        assertEquals("a", controller.getUser().getName());
        assertEquals("a@a.a", controller.getUser().getMail());
        assertNotNull(controller.getUser().getPassword());
    }

    @Test
    void testGetUserDataFromInputMissingArgs() throws IOException {
        setUpUserParams();
        when(mockRequest.getParameter(UserController.PARAM_PASSWORD)).thenReturn("b");

        controller.getUserDataFromInput(mockRequest);

        assertEquals(1, controller.getEvents().size());
        assertNull(controller.getUser().getName());
        assertNull(controller.getUser().getMail());
        assertNull(controller.getUser().getPassword());
    }

    @Test
    void testRegisterSuccess() throws IOException, SQLException {
        when(mockRequest.getParameter("register")).thenReturn("register");
        when(mockDAO.getPlayerByMail(anyString())).thenReturn(null);
        when(mockDAO.createNewPlayer(anyString(), anyString(), anyString())).thenReturn(1);
        setUpUserParams();

        controller.doPost(mockRequest, mockResponse);

        verify(mockResponse, times(1)).sendRedirect(Consts.SIGN_IN);
        verify(mockSession, times(3)).setAttribute(anyString(), anyObject());
    }

    @Test
    void testRegisterFail() throws IOException, SQLException {
        setUpUserParams();
        when(mockRequest.getParameter("register")).thenReturn("register");
        when(mockDAO.getPlayerByMail(anyString())).thenReturn(mockUser);

        controller.doPost(mockRequest, mockResponse);

        verify(mockResponse, times(1)).sendRedirect(Consts.PROFILE);
    }

    private void setUpUserParams() {
        when(mockRequest.getParameter(UserController.PARAM_NAME)).thenReturn("a");
        when(mockRequest.getParameter(UserController.PARAM_MAIL)).thenReturn("a@a.a");
        when(mockRequest.getParameter(UserController.PARAM_PASSWORD)).thenReturn("a");
        when(mockRequest.getParameter(UserController.PARAM_CONFIRM_PASSWORD)).thenReturn("a");
    }

    private void createMockSession() {
        mockSession = mock(HttpSession.class);
        when(mockRequest.getSession()).thenReturn(mockSession);
    }
}
