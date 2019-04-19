package controller;

import dao.PlayerDAO;
import model.UserBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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
        when(controller.getPlayerDAO()).thenReturn(mockPlayerDao);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        when(mockRequest.getRequestDispatcher(SignInController.PAGE_TO_LOAD_ON_ERROR))
                .thenReturn(mock(RequestDispatcher.class));
        when(mockRequest.getRequestDispatcher(SignInController.PAGE_TO_LOAD_ON_COMPLETE))
                .thenReturn(mock(RequestDispatcher.class));
    }

    @Test
    void testSuccessfulSignIn() {
        //setup valid parameter extraction
        when(mockRequest.getParameter(SignInController.MAIL_PARAMETER_NAME)).thenReturn(VALID_EMAIL);
        when(mockRequest.getParameter(SignInController.PASSWORD_PARAMETER_NAME)).thenReturn(VALID_PASSWORD);

        //setup dao validation
        when(mockPlayerDao.getValidatedUser(VALID_EMAIL, VALID_PASSWORD)).thenReturn(new UserBean());

        controller.doPost(mockRequest, mockResponse);

        assertFalse(SignInController.DISPLAY_ERROR_MESSAGE);
        verify(mockRequest, times(1)).getRequestDispatcher(SignInController.PAGE_TO_LOAD_ON_COMPLETE);

    }

    @Test
    void testUnsuccessfulSignIn() {
        //setup invalid parameter extraction
        when(mockRequest.getParameter(SignInController.MAIL_PARAMETER_NAME)).thenReturn(INVALID_EMAIL);
        when(mockRequest.getParameter(SignInController.PASSWORD_PARAMETER_NAME)).thenReturn(INVALID_PASSWORD);

        //setup dao validation
        when(mockPlayerDao.getValidatedUser(INVALID_EMAIL, INVALID_PASSWORD)).thenReturn(null);

        controller.doPost(mockRequest, mockResponse);

        assertTrue(SignInController.DISPLAY_ERROR_MESSAGE);
        verify(mockRequest, times(1)).getRequestDispatcher(SignInController.PAGE_TO_LOAD_ON_ERROR);
    }
}