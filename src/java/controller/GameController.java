package controller;

import model.Behaviors.UserBehavior;
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

@WebServlet(
        name = "GameController",
        urlPatterns = "/Game/*")
public class GameController extends HttpServlet {

    private BoardBean board;

    public void setBoard(BoardBean board) {
        this.board = board;
    }

    //region static variables
    private final static String ATTACKER_KEY = "attackDice";
    private final static String MOVE_KEY = "moveSoldiers";
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
            board = (BoardBean) request.getSession().getAttribute("board");
            if (board != null) {
                if (request.getParameter("nextTurn") != null && request.getParameter("nextTurn").equals("execute")) {
                    board.executeTurn();
                } else if (board.getCurrentPhase() == Phase.SETTINGPHASE) {
                    this.settingPhase(request, response);
                } else if (request.getParameter("end") != null) {
                    if (board.getCurrentPhase() == Phase.ATTACKPHASE) {
                        board.setCurrentPhase(Phase.MOVINGPHASE);
                    } else if (board.getCurrentPhase() == Phase.MOVINGPHASE) {
                        board.setCurrentPhase(Phase.SETTINGPHASE);
                        board.cyclePlayer();
                    }
                } else {
                    this.extractSelectedCountry(request, response);
                }
            }
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void extractSelectedCountry(HttpServletRequest request, HttpServletResponse response) {
        int countryIndex = Integer.parseInt(request.getParameter("country"));
        Country chosenCountry = board.getCountryById(countryIndex);
        if (board.getFirstSelectedCountry() == null) {
            board.setFirstSelectedCountry(chosenCountry);
        } else {
            if (chosenCountry != board.getFirstSelectedCountry()) {
                board.setSecondSelectedCountry(chosenCountry);
            }
        }
    }

    private void settingPhase(HttpServletRequest request, HttpServletResponse response) {
        int countryIndex = Integer.parseInt(request.getParameter("country"));
        Country chosenCountry = board.getCountryById(countryIndex);
        if (chosenCountry.getOwner() == board.getCurrentPlayer()
                && board.currentPlayerIsUser()
                && board.getSoldiersToPlace() > 0) {

            chosenCountry.addSoldier();
            board.setSoldiersToPlace(board.getSoldiersToPlace() - 1);
        }
    }

    private void attackPhase(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getPathInfo();
        if (request.getParameter("end") != null) {
            board.setCurrentPhase(Phase.MOVINGPHASE);
        } else if (path.equals("/selectedCountry")) {
            Country country = board.getCountryByName(request.getParameter("country"));
            board.setAttackAndDefendCountry(country);
        } else if (path.equals("/attack") && request.getParameter("roll") != null) {
            int attackDiceCount = request.getParameterMap().get(ATTACKER_KEY).length;
            board.attackRoll(attackDiceCount);
        } else if (path.equals("/attack") && request.getParameter("cancel") != null) {
            board.cancelAttack();
        }
    }

    private void movePhase(HttpServletRequest request, HttpServletResponse response) {
        String countryName = request.getParameter("country");
        String path = request.getPathInfo();
        if (request.getParameter("end") != null) {
            board.setCurrentPhase(Phase.SETTINGPHASE);
        } else if (path.equals("/selectedCountry")) {
            Country country = board.getCountryByName(request.getParameter("country"));
            board.setMoveSoldiersToCountry(country, countryName);
        } else if (path.equals("/move") && request.getParameter("roll") != null) {
            int moveSoilderCount = 0;
            for (String key : request.getParameterMap().keySet()) {
                if (key.contains(MOVE_KEY)) {
                    moveSoilderCount++;
                }
            }
        } else if (path.equals("/move") && request.getParameter("cancel") != null) {
            board.cancelMove();
        }
    }
}
