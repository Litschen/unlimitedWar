package ch.zhaw.unlimitedWar.controller;

import ch.zhaw.unlimitedWar.model.Board;
import ch.zhaw.unlimitedWar.model.Card;
import ch.zhaw.unlimitedWar.model.Country;
import ch.zhaw.unlimitedWar.model.Player;
import ch.zhaw.unlimitedWar.model.interfaces.Event;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "GameController", urlPatterns = "/Game/*")
public class GameController extends HttpServlet {

    //region path & param variables
    public final static String PATH_SELECT_CARD = "/selectCard";
    public final static String PATH_ATTACK = "/attack";
    public final static String PATH_RESULT = "/result";
    public final static String PARAM_ATTACK_DICE = "attackDice";
    public final static String PARAM_ROLL = "roll";
    public final static String PARAM_END = "end";
    public final static String PARAM_CANCEL = "cancel";
    public final static String PARAM_COUNTRY = "country";
    public final static String PARAM_COUNTRY_CARD = "country-card";
    public final static String PARAM_NEXT_TURN = "nextTurn";
    public final static String PAGE_TO_LOAD_ON_COMPLETE = Consts.GAME;
    private final static Logger LOGGER = Logger.getLogger(GameController.class.getName());
    private Board board;
    //endregion

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    /**
     * Decides whether its the AI or the users turn and calls the appropriate methods accordingly
     *
     * @param request  from game.jsp
     * @param response servlet response
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(Consts.SESSION_EVENTS, new ArrayList<Event>());

        try {
            board = (Board) request.getSession().getAttribute(Consts.SESSION_BOARD);
            if (board != null) {
                if (PATH_SELECT_CARD.equals(request.getPathInfo())) {
                    useCard(request);
                } else if (request.getParameter(PARAM_NEXT_TURN) != null) {
                    board.getCurrentTurn().executeTurn();
                } else if (request.getParameter(PARAM_END) != null) {
                    board.getCurrentTurn().moveToNextPhase();
                } else if (board.getCurrentTurn().currentPlayerIsUser() && request.getPathInfo() != null && !request.getPathInfo().equals(PATH_RESULT)) {
                    Country chosenCountry = extractSelectedCountry(request);
                    String path = request.getPathInfo();

                    if (path.equals(PATH_ATTACK) && request.getParameter(PARAM_ROLL) != null) {
                        int attackDiceCount = request.getParameterMap().get(PARAM_ATTACK_DICE).length;
                        board.getCurrentTurn().getCurrentPlayer().setAttackDiceCount(attackDiceCount);
                    } else if (path.equals(PATH_ATTACK) && request.getParameter(PARAM_CANCEL) != null) {
                        board.getCurrentTurn().resetSelectedCountries();
                    }
                    board.getCurrentTurn().executeUserTurn(chosenCountry);
                    request.getSession().setAttribute(Consts.SESSION_EVENTS, board.getEvents());
                }
            }
            response.sendRedirect(request.getContextPath() + PAGE_TO_LOAD_ON_COMPLETE);
        } catch (IOException | NullPointerException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private Country extractSelectedCountry(HttpServletRequest request) {
        Country toReturn = null;
        try {
            int countryIndex = Integer.parseInt(request.getParameter(PARAM_COUNTRY));
            toReturn = board.getCountryById(countryIndex);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
        return toReturn;
    }

    private void useCard(HttpServletRequest request) {
        Player currentPlayer = board.getCurrentTurn().getCurrentPlayer();
        int cardIx = Integer.parseInt(request.getParameter(PARAM_COUNTRY_CARD));

        Card card = currentPlayer.getCards().get(cardIx);
        currentPlayer.addSoldiersToPlace(card.getCardBonus(currentPlayer));
        currentPlayer.removeCard(card);
    }

}