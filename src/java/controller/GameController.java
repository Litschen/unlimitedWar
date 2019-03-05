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
                } else if (request.getParameter("end") != null) {
                    this.moveToNextPhase();
                } else {
                    Country chosenCountry = this.extractSelectedCountry(request, response);
                    String path = request.getPathInfo();

                    // handle attack modal
                    if (path.equals("/attack") && request.getParameter("roll") != null) {
                        int attackDiceCount = request.getParameterMap().get(ATTACKER_KEY).length;
                        board.getCurrentPlayer().setAttackDiceCount(attackDiceCount);
                    } else if (path.equals("/attack") && request.getParameter("cancel") != null) {
                        board.resetSelectedCountries();
                    }
                    // handle phase
                    board.executeUserTurn(chosenCountry);
                }
            }
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private Country extractSelectedCountry(HttpServletRequest request, HttpServletResponse response) {
        try {
            int countryIndex = Integer.parseInt(request.getParameter("country"));
            return board.getCountryById(countryIndex);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void moveToNextPhase() {
        Phase currentPhase = board.getCurrentPhase();

        if (currentPhase == Phase.SETTINGPHASE) {
            board.setCurrentPhase(Phase.ATTACKPHASE);
        } else if (currentPhase == Phase.ATTACKPHASE) {
            board.setCurrentPhase(Phase.MOVINGPHASE);
        } else if (currentPhase == Phase.MOVINGPHASE) {
            board.setCurrentPhase(Phase.SETTINGPHASE);
            board.cyclePlayer();
        }

        board.resetSelectedCountries();
    }
}