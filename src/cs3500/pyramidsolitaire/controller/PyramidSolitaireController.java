package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.List;

/**
 * The controller for a game of pyramid solitaire, which communicates between the model and view.
 */
public interface PyramidSolitaireController {

  /**
   * Plays a new game of pyramid solitaire using the given model.
   * @param model the model of the game of pyramid solitaire, concerns game functionality
   * @param deck the deck of cards with which the game will be played
   * @param shuffle true if the deck is to be shuffled at the beginning of the game, false if deck
   *                order should remain same
   * @param numRows the desired number of rows of the pyramid in the game
   * @param numDraw the number of draw cards visible at any one time in the game
   * @param <K> the cards in the deck of the game
   * @throws IllegalArgumentException if the model is null
   * @throws IllegalStateException if the controller is unable to receive input/give output, or if
   *                               game cannot be started
   */
  <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle, int numRows,
      int numDraw);
  // use startGame()

}
