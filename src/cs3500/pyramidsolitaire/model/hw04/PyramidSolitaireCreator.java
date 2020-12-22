package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * A builder for creating a game of pyramid solitaire.
 */
public class PyramidSolitaireCreator {

  /**
   * The possible game types.
   */
  public enum GameType { BASIC, RELAXED, MULTIPYRAMID }


  /**
   * Creates an instance of the specified type of game of pyramid solitaire.
   *
   * @param type the desired type of game to be played
   * @return a game of the desired tyep
   */
  public static PyramidSolitaireModel<Card> create(GameType type) {
    switch (type) {
      case BASIC :
        return new BasicPyramidSolitaire();
      case RELAXED :
        return new RelaxedPyramidSolitaire();
      case MULTIPYRAMID:
        return new MultiPyramidPyramidSoltaire();
      default :
        return null;
    }
  }

}
