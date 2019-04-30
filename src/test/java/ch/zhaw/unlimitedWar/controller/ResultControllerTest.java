package ch.zhaw.unlimitedWar.controller;

import ch.zhaw.unlimitedWar.dao.MySQLConnectionCreator;
import ch.zhaw.unlimitedWar.dao.ResultsDAO;
import ch.zhaw.unlimitedWar.model.UserBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

class ResultControllerTest {

    // region static variables
    private final static String USER_MAIL = "test@gmail.com";
    private final static String PARAMETER_STRING = "outcomeValues";
    //endregion

    //region data fields
    private ResultsDAO resultDAOMock;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private ResultController resultController;
    private UserBean userBean;
    private HttpSession httpSession;
    private ResultController resultControllerspy;
    private MySQLConnectionCreator mySQLConnectionCreator;
    private ResultsDAO mockResultDAO;
    //endregion

    @BeforeEach
    void setUp() {
        resultDAOmock = mock(ResultsDAO.class);
        mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getContextPath()).thenReturn("");
        mockResponse = mock(HttpServletResponse.class);
        when(mockRequest.getSession()).thenReturn(mock(HttpSession.class));
        resultController = new ResultController();
        userBean = mock(UserBean.class);
        httpSession = mock(HttpSession.class);
        resultControllerspy = spy(new ResultController());
        mySQLConnectionCreatorMock = new MySQLConnectionCreator();
        mockPlayerDao = mock(PlayerDAO.class);

    }


    @Test
    void testSaveResultIsCalled() throws Exception {

        when(mockRequest.getPathInfo()).thenReturn(ResultController.PATH_SAVE);
        when(mockRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(anyString())).thenReturn(userBean);
        when(userBean.getMail()).thenReturn("test@gmail.com");
        when(mockRequest.getParameter(ResultController.PARAM_SELECTED_WIN)).thenReturn("xx");
        resultController.doPost(mockRequest,mockResponse);


        verify(resultDAOmock, times(1)).saveResult(anyBoolean(), anyString());

    }


}