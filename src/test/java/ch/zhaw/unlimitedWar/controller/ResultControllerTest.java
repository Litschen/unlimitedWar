package ch.zhaw.unlimitedWar.controller;

import ch.zhaw.unlimitedWar.dao.ResultsDAO;
import ch.zhaw.unlimitedWar.model.UserBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

class ResultControllerTest {

    private ResultsDAO resultDAOmock;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private ResultController resultController;
    private UserBean userBean;
    private HttpSession httpSession;
    private ResultController resultControllerspy;

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

    }


    @Test
    void testSaveResultIsCalled() throws Exception {

        resultController.doPost(mockRequest,mockResponse);
        when(mockRequest.getPathInfo()).thenReturn(ResultController.PATH_SAVE);
        when(mockRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(anyString())).thenReturn(userBean);
        when(mockRequest.getParameter(anyString())).thenReturn(ResultController.PARAM_SELECTED_WIN);

        doReturn(1).when(resultControllerspy).setUpDBConnection();
        verify(resultDAOmock, times(1)).saveResult(anyBoolean(), anyString());

    }










}