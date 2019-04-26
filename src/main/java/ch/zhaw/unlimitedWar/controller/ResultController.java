package ch.zhaw.unlimitedWar.controller;


import ch.zhaw.unlimitedWar.dao.MySQLConnectionCreator;
import ch.zhaw.unlimitedWar.dao.ResultsDAO;
import ch.zhaw.unlimitedWar.model.ResultBean;
import ch.zhaw.unlimitedWar.model.UserBean;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "ResultController", urlPatterns = "/Result")
public class ResultController extends HttpServlet {

    //region static variables
    public final static String PATH_SAVE = "/Save";
    private final static String HOME_PAGE = Pages.HOME;
    public final static String PARAM_SELECTED_WIN = "win";
    private final static Logger LOGGER = Logger.getLogger(ResultSet.class.getName());
    public static final String DATABASE_ERROR = "DATABASE ERROR: Could not establish connection";
    //endregion

    //region data fields
    private ResultsDAO resultDAO;
    //endregion

    //region getter/setter
    public List<ResultBean> getAllResultsOfUser(UserBean user){
        List<ResultBean> resultOfUser = new ArrayList <> ();
        try{
            setUpDBConnection();
            resultOfUser.addAll(resultDAO.getAllResultsOfUser(user.getMail()));
            resultDAO.closeConnection();
        }catch (Exception e){ LOGGER.log(Level.WARNING, DATABASE_ERROR, e);}
        return resultOfUser;
    }

    // endregion

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        if (PATH_SAVE.equals(request.getPathInfo())) {
            insertResult(request);
        }

        try {
            response.sendRedirect(request.getContextPath() + HOME_PAGE);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, DATABASE_ERROR, e);
        }
    }

    private void insertResult(HttpServletRequest request) {
        try{
            UserBean user = UserController.getSessionUser(request.getSession());
            boolean outcome = request.getParameter(PARAM_SELECTED_WIN) != null;

            setUpDBConnection();
            resultDAO.saveResult(outcome, user.getMail() );
            resultDAO.closeConnection();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, DATABASE_ERROR, e);
        }
    }
    private void setUpDBConnection() {
        try {
            resultDAO = new ResultsDAO(MySQLConnectionCreator.getConnection());
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, DATABASE_ERROR, e);
        }
    }

}