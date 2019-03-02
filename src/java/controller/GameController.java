package controller;

import model.BoardBean;
import model.Country;
import model.Enum.Phase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        name = "GameController",
        urlPatterns = "/Game/*")
public class GameController extends HttpServlet {

    private BoardBean board;

    public void setBoard(BoardBean board){
        this.board = board;
    }

    //region static variables
    private final static String ATTACKER_KEY = "attackDice";
    //enddregion

    /**
     * TEXT
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * TEXT
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process the Request
     *
     * @param request
     * @param response
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/game.jsp");
        try {
            if (request.getParameter("nextTurn") != null && request.getParameter("nextTurn").equals("execute")) {
                board = (BoardBean) request.getSession().getAttribute("board");
                board.executeTurn();
            } else if (board.getCurrentPhase() == Phase.SETTINGPHASE) {
                this.setPhase(request, response);
            } else if (board.getCurrentPhase() == Phase.ATTACKPHASE) {
                this.attackPhase(request, response);
            } else if (board.getCurrentPhase() == Phase.MOVINGPHASE) {
                this.movePhase(request, response);
            }
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    // ---------- TODO: /F0310/ ----------
    private void setPhase(HttpServletRequest request, HttpServletResponse response) {
        String countryName = request.getParameter("country");
        board.addSoldiersToCountry(countryName);
    }

    private void attackPhase(HttpServletRequest request, HttpServletResponse response) {
        if (request.getPathInfo().equals("/selectedCountry")) {
            Country country = board.getCountryByName(request.getParameter("country"));
            board.setAttackAndDefendCountry(country);
        } else if (request.getPathInfo().equals("/attack") && request.getParameter("roll") != null) {
            int attackDiceCount = 0;

            for (String key : request.getParameterMap().keySet()) {
                if (key.contains(ATTACKER_KEY)) {
                    attackDiceCount++;
                }
            }

            board.attackRoll(attackDiceCount);
        } else if (request.getPathInfo().equals("/attack") && request.getParameter("cancel") != null) {
            board.cancelAttack();
        }
    }

    private void movePhase(HttpServletRequest request, HttpServletResponse response) {

    }
}
