package controller;


import dao.ResultsDAO;
import model.ResultBean;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ResultController", urlPatterns = "/Result")
public class ResultController extends HttpServlet {

    //region static variables
    public final static boolean OUTCOME_PARAMETER_NAME = false ;
    public final static String MAIL_PARAMETER_NAME = "mail";
    //endregion

    //region data fields
    private ResultBean result = null;
    private ResultsDAO resultDAO;
    //endregion

    //region getter/setter
    public ResultBean getResult() {
        return result;
    }
    public void setUser(ResultBean user) {
        this.result = result;
    }


    //added for testing purposes
    public ResultsDAO getResultDAO() {
        return this.resultDAO;
    }
    //endregion

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        boolean outcome = Boolean.parseBoolean(request.getParameter(String.valueOf((OUTCOME_PARAMETER_NAME))));
        String mail = request.getParameter(MAIL_PARAMETER_NAME);

        if (getResultDAO() != null) {
            try {
                getResultDAO().saveResult(outcome, mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}
