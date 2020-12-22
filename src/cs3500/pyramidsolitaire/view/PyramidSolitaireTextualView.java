package cs3500.pyramidsolitaire.view;


import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;
import java.util.List;

/**
 * A textual view of a game of pyramid solitaire.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model; // the model that holds the information for the
  // game being displayed by this view
  private final Appendable textualOutput; // the location at which this view will be output

  /**
   * Constructor for a pyramid solitaire textual view with one model, and no outputs.
   *
   * @param model the model of the game that is being represented textually
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.textualOutput = new StringBuffer(); // should this be null or just dont mention it
  }

  /**
   * Constructor for a textual view that considers both the model of the game, and the output to be
   * given to the user.
   *
   * @param model the model of the game that is being textually represented
   * @param output the output that should be given to the user, based on the state of the model's
   *               game
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable output) {
    this.model = model;
    this.textualOutput = output;
  }

  @Override
  public void render() throws IOException {
    this.textualOutput.append(this.toString());
  }

  @Override
  public String toString() {
    try {
      if (this.model.getScore() == 0) {
        return "You win!";
      }
      else if (this.model.isGameOver()) {
        return this.youLostToString();
      }
      else {
        return this.inPlayPyramidToString();
      }
    }
    catch (IllegalStateException e) {
      return "";
    }
  }

  /**
   * Converts the pyramid of the model of this view into its visual representation.
   *
   * @return A pyramid of card visualizations corresponding to the cards in the pyramid of the model
   *         of this view.
   */
  private String inPlayPyramidToString() {
    int numRows = this.model.getNumRows();
    String result = "";

    for (int i = 0; i < numRows; i++) {
      int padding = (this.maxSpacing() - ((3 * (this.model.getRowWidth(i)))
          + (this.model.getRowWidth(i) - 1))) / 2;
      String space = " ";
      result = result + space.repeat(padding);

      for (int j = 0; j < this.model.getRowWidth(i); j++) {
        if (j == (this.model.getRowWidth(i) - 1)) {
          if (this.model.getCardAt(i, j) == null) {
            result = result + ".";
          }
          else {
            result = result + this.model.getCardAt(i, j).toString();
          }
        }
        else {
          if (this.model.getCardAt(i, j) == null) {
            result = result + ".   ";
          }
          else {
            result = result + this.addPadding(this.model.getCardAt(i, j).toString()) + " ";
          }
        }
      }

      result = result + '\n';
    }

    result = result + this.drawToString();
    return result;
  }

  /**
   * Adds necessary padding to the String representation of a card in this view.
   *
   * @param cardString the String representation of a card, to be padded
   * @return the String representation with a space added if the card has a one digit value,
   *         otherwise just the card string
   */
  private String addPadding(String cardString) {
    if (cardString.length() < 3) {
      return cardString + " ";
    }
    else {
      return cardString;
    }
  }

  /**
   * The maximum number of characters in a row in the pyramid of this view.
   *
   * @return the maximum number of characters in any row
   */
  private int maxSpacing() {
    int maxWidth = this.model.getRowWidth(this.model.getNumRows() - 1);
    return (maxWidth * 3) + (maxWidth - 1);
  }

  /**
   * The losing message.
   *
   * @return the String "Game over. Score: ##" where ## is the score when the game ended.
   */
  private String youLostToString() {
    return "Game over. Score: " + Integer.toString(this.model.getScore());
  }

  /**
   * The String representation of the draw pile for this game.
   *
   * @return The string Draw: followed by textual representations of the cards in the draw pile
   *         in this game.
   */
  private String drawToString() {
    List<?> drawPile = this.model.getDrawCards();
    int numDraw = this.model.getNumDraw();
    String result = "Draw:";

    if (this.emptyDrawPile()) {
      return result;
    }

    else {
      for (int i = 0; i < numDraw; i++) {
        if (i == (numDraw - 1)) {
          if (drawPile.get(i) == null) {
            result = result + " .";
          }
          else {
            result = result + " " + drawPile.get(i).toString().replace(" ", "");
          }
        }
        else {
          if (drawPile.get(i) == null) {
            result = result + " " + ".  " + ",";
          }
          else {
            result = result + " " + drawPile.get(i).toString() + ",";
          }
        }
      }


      return result;

    }

  }

  /**
   * Determines if the draw pile of this game contains no cards.
   *
   * @return true if the list contains only null values, false if the list contains any non-null
   *         values
   */
  private boolean emptyDrawPile() {
    boolean result = true;
    for (int i = 0; i < this.model.getNumDraw(); i++) {
      if (this.model.getDrawCards().get(i) != null) {
        result = false;
        break;
      }
    }

    return result;
  }

}
