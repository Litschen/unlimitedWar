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

@WebServlet(name = "GameController", urlPatterns = "/Game/*")
public class GameController extends HttpServlet {

    private BoardBean board;

    //region path & param variables
    private final static String PATH_ATTACK = "/attack";
    private final static String PARAM_ATTACK_DICE = "attackDice";
    private final static String PARAM_ROLL = "roll";
    private final static String PARAM_END = "end";
    private final static String PARAM_CANCEL = "cancel";
    private final static String PARAM_COUNTRY = "country";
    private final static String PARAM_NEXT_TURN = "nextTurn";
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
                if (request.getParameter(PARAM_NEXT_TURN) != null) {
                    board.executeTurn();
                } else if (request.getParameter(PARAM_END) != null) {
                    board.moveToNextPhase();
                } else if (board.currentPlayerIsUser()) {
                    Country chosenCountry = extractSelectedCountry(request, response);
                    String path = request.getPathInfo();

                    // handle attack modal
                    if (path.equals(PATH_ATTACK) && request.getParameter(PARAM_ROLL) != null) {
                        int attackDiceCount = request.getParameterMap().get(PARAM_ATTACK_DICE).length;
                        board.getCurrentPlayer().setAttackDiceCount(attackDiceCount);
                    } else if (path.equals(PATH_ATTACK) && request.getParameter(PARAM_CANCEL) != null) {
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
            int countryIndex = Integer.parseInt(request.getParameter(PARAM_COUNTRY));
            return board.getCountryById(countryIndex);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }

}