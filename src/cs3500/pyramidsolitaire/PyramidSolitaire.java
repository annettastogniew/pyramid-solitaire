package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.io.IOException;
import java.io.StringReader;

/**
 * A factory class for creating a game of pyramid solitaire.
 */
public final class PyramidSolitaire {

  /**
   * Starts a game of pyramid solitaire according to the user's input.
   *
   * @param args the user input
   */
  public static void main(String[] args) throws IOException {
    int row = 7; // the number of rows in the game
    int draw = 3; // the number of draw cards in the game
    Readable in; // reads user input from the command-line
    Appendable out = new StringBuffer(); // the location at which the game will be output
    PyramidSolitaireModel<Card> model; // the model that holds the information for this game
    PyramidSolitaireController controller; // the controller that passes the game information from
    // the model to the view

    try {
      switch (args[0]) {
        case "basic" :
          model = new PyramidSolitaireCreator().create(GameType.BASIC);
          // creates a game of pyramid solitaire with one pyramid, where only exposed cards can be
          // paired
          break;
        case "relaxed" :
          model = new PyramidSolitaireCreator().create(GameType.RELAXED);
          // creates a game of pyramid solitaire with one pyramid, where cards that are not exposed,
          // but are only covered by one card with which they can make a pair, can be played
          break;
        case "multipyramid" :
          model = new PyramidSolitaireCreator().create(GameType.MULTIPYRAMID);
          // creates a game of pyramid solitaire with three overlapping pyramids
          break;
        default :
          model = null;
          break;
      }


      try {
        row = Integer.parseInt(args[1]);
        draw = Integer.parseInt(args[2]);

        String inString = "";
        for (int i = 3; i < args.length; i++) {
          inString = inString + args[i] + " ";
        }
        in = new StringReader(inString);
      }
      catch (NumberFormatException e) {
        String inString = "";
        for (int i = 1; i < args.length; i++) {
          inString = inString + args[i];
        }
        in = new StringReader(inString);
      }

      controller = new PyramidSolitaireTextualController(in, out);

      controller.playGame(model, model.getDeck(), false, row, draw);
    }
    catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
      out.append("Game terminated.");
    }

    System.out.println(out);
  }
}
