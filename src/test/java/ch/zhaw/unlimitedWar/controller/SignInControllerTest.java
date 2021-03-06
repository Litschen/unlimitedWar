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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SignInControllerTest {

    //region static variables
    private final static String VALID_EMAIL = "max@gmail.com";
    private final static String VALID_PASSWORD = "123";
    private final static String INVALID_EMAIL = "asdfsajk";
    private final static String INVALID_PASSWORD = "asdfasdf";
    //endregion
    //region data fields
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private SignInController controller = spy(new SignInController());
    private PlayerDAO mockPlayerDao;
    //endregion

    @BeforeEach
    void setUp() {
        mockPlayerDao = mock(PlayerDAO.class);
        MySQLConnectionCreator mockConnectionCreator = mock(MySQLConnectionCreator.class);
        controller.setConnectionCreator(mockConnectionCreator);
        when(mockConnectionCreator.getPlayerDAO()).thenReturn(mockPlayerDao);
        mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getContextPath()).thenReturn("");
        mockResponse = mock(HttpServletResponse.class);
        when(mockRequest.getSession()).thenReturn(mock(HttpSession.class));
    }

    @Test
    void testSuccessfulSignIn() throws IOException {
        //setup valid parameter extraction
        when(mockRequest.getParameter(SignInController.MAIL_PARAMETER_NAME)).thenReturn(VALID_EMAIL);
        when(mockRequest.getParameter(SignInController.PASSWORD_PARAMETER_NAME)).thenReturn(VALID_PASSWORD);

        //setup dao validation
        when(mockPlayerDao.getValidatedUser(VALID_EMAIL, VALID_PASSWORD)).thenReturn(new UserBean());

        controller.doPost(mockRequest, mockResponse);

        assertFalse(SignInController.DISPLAY_ERROR_MESSAGE);
        verify(mockResponse, times(1)).sendRedirect(SignInController.PAGE_TO_LOAD_ON_COMPLETE);

    }

    @Test
    void testUnsuccessfulSignIn() throws IOException {
        //setup invalid parameter extraction
        when(mockRequest.getParameter(SignInController.MAIL_PARAMETER_NAME)).thenReturn(INVALID_EMAIL);
        when(mockRequest.getParameter(SignInController.PASSWORD_PARAMETER_NAME)).thenReturn(INVALID_PASSWORD);

        //setup ch.zhaw.unlimitedWar.dao validation
        when(mockPlayerDao.getValidatedUser(INVALID_EMAIL, INVALID_PASSWORD)).thenReturn(null);

        controller.doPost(mockRequest, mockResponse);

        assertTrue(SignInController.DISPLAY_ERROR_MESSAGE);
        verify(mockResponse, times(1)).sendRedirect(SignInController.PAGE_TO_LOAD_ON_ERROR);
    }
}