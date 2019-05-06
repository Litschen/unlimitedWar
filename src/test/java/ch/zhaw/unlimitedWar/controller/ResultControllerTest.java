package ch.zhaw.unlimitedWar.controller;

import ch.zhaw.unlimitedWar.dao.MySQLConnectionCreator;
import ch.zhaw.unlimitedWar.dao.ResultsDAO;
import ch.zhaw.unlimitedWar.model.UserBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ResultControllerTest {

    // region static variables
    private final static String USER_MAIL = "test@gmail.com";
    private final static String PARAMETER_STRING = "outcomeValues";
    //endregion
    //region data fields
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private ResultController resultController;
    private UserBean mockUserBean;
    private HttpSession mockHttpSession;
    private MySQLConnectionCreator mockMySQLConnectionCreator;
    private ResultsDAO mockResultDAO;
    //endregion

    @BeforeEach
    void setUp() {
        mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getContextPath()).thenReturn("");
        mockResponse = mock(HttpServletResponse.class);
        when(mockRequest.getSession()).thenReturn(mock(HttpSession.class));
        resultController = new ResultController();
        mockUserBean = mock(UserBean.class);
        mockHttpSession = mock(HttpSession.class);
        mockResultDAO = mock(ResultsDAO.class);
        mockMySQLConnectionCreator = mock(MySQLConnectionCreator.class);
        when(mockMySQLConnectionCreator.getResultDAO()).thenReturn(mockResultDAO);
    }


    @Test
    void testSaveResultIsCalled() throws Exception {

        //setup invalid parameter extraction
        when(mockRequest.getPathInfo()).thenReturn(ResultController.PATH_SAVE);
        when(mockRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpSession.getAttribute(anyString())).thenReturn(mockUserBean);
        when(mockMySQLConnectionCreator.getResultDAO()).thenReturn(mockResultDAO);
        when(mockUserBean.getMail()).thenReturn(USER_MAIL);
        when(mockRequest.getParameter(ResultController.PARAM_SELECTED_WIN)).thenReturn(PARAMETER_STRING);

        resultController.setmySQLConnectionCreator(mockMySQLConnectionCreator);
        resultController.doPost(mockRequest, mockResponse);

        verify(mockResultDAO, times(1)).saveResult(anyBoolean(), anyString());

    }




}