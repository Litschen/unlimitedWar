package controller;

import model.BoardBean;
import model.Country;

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

    private BoardBean board = new BoardBean();

    //region static variables
    private final static String ATTACKER_KEY = "attackDice";
    private final static String DEFENDER_KEY = "defendDice";
    //enddregion

    /**
     * TEXT
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

    /** Process the Request
     * @param request
     * @param response
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/game.jsp");
        try {
            if (request.getParameter("nextTurn") != null && request.getParameter("nextTurn").equals("execute")) {
                board = (BoardBean) request.getSession().getAttribute("board");
                board.executeTurn();
            } /*else if(false){    // PHASE == PLACE SOLDIERS
                this.setPhase(request, response);
            } else if (true){    // PHASE == ATTACK
                this.attackPhase(request, response);
            }*/
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// ---------- TODO: /F0310/ ----------
//    call these function in processRequest();
    private void setPhase(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<Country> countries = new ArrayList<>();
        for (String i : request.getParameterMap().get("country")){
            countries.add(board.getCountryById(Integer.parseInt(i)));
        }
        boolean placeSoldierIsDone = board.addSoldiersToCountry(countries);
        if (placeSoldierIsDone){
            // TODO move to next PHASE
        }
    }

    private void attackPhase(HttpServletRequest request, HttpServletResponse response) {
        if (request.getPathInfo().equals("/selectedCountry")) {
            Country country = board.getCountryById(Integer.parseInt(request.getParameter("country")));

            if (board.getCurrentPlayer().getOwnedCountries().contains(country)) {
                board.setAttackerCountry(country);
            } else {
                board.setDefenderCountry(country);
            }

            if (board.getAttackerCountry() != null && board.getDefenderCountry() != null){
                board.setModalToShow("attack");
            }
        } else if (request.getPathInfo().equals("/attack") && request.getParameter("cancel") != null) {
            board.setAttackerCountry(null);
            board.setDefenderCountry(null);
            board.setModalToShow("");
        } else {
            int attackDiceCount = 0;
            int defendDiceCount = 0;

            for (String key : request.getParameterMap().keySet()) {
                if (key.contains(ATTACKER_KEY)) {
                    attackDiceCount++;
                } else if (key.contains(DEFENDER_KEY)) {
                    defendDiceCount++;
                }
            }

            board.attackRoll(attackDiceCount, defendDiceCount);
        }
    }

    private void movePhase() {

    }
}
