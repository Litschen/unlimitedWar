package ch.zhaw.unlimitedWar.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SignOutController", urlPatterns = "/SignOut")
public class SignOutController extends HttpServlet {

    //region static variables
    private final static String PAGE_AFTER_SIGN_OUT = Consts.SIGN_IN;
    private final static Logger LOGGER = Logger.getLogger(SignOutController.class.getName());
    //endregion

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        UserController.setSessionUser(request.getSession(), null);
        try {
            response.sendRedirect(request.getContextPath() + PAGE_AFTER_SIGN_OUT);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
