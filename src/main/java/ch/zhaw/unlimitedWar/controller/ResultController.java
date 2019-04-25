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
    //endregion

    //region data fields
    private ResultsDAO resultDAO;
    //endregion

    //region getter/setter
    public List<ResultBean> getAllResultsOfUser(UserBean user){
        List<ResultBean> resultOfUser = new ArrayList ();
        try{
            setUpDBConnection();
            resultOfUser.addAll(resultDAO.getAllResultsOfUser(user.getMail()));
            resultDAO.closeConnection();
        }catch (Exception e){ LOGGER.log(Level.WARNING, "DATABASE ERROR: Could not establish connection", e);}
        return resultOfUser;
    }

    // endregion

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        String redirectPageTo = HOME_PAGE;
        String path = request.getPathInfo();
        if (PATH_SAVE.equals(path)) {
            insertResult(request);
            redirectPageTo = HOME_PAGE;
        }

        try {
            response.sendRedirect(request.getContextPath() + redirectPageTo);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "DATABASE ERROR: Could not establish connection", e);
        }
    }

    private void insertResult(HttpServletRequest request) {
        try{boolean outcome = false;
            UserBean user = (UserBean) request.getSession().getAttribute("user");
            String mail = user.getMail();
            if( request.getParameter(PARAM_SELECTED_WIN) != null){
                outcome = true;
            }
            setUpDBConnection();
            resultDAO.saveResult(outcome, mail );
            resultDAO.closeConnection();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "DATABASE ERROR: Could not establish connection", e);
        }
    }
    private void setUpDBConnection() {
        try {
            resultDAO = new ResultsDAO(MySQLConnectionCreator.getConnection());
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.WARNING, "DATABASE ERROR: Could not establish connection", e);
        }
    }

}