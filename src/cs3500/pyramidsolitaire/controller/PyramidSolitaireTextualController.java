package cs3500.pyramidsolitaire.controller;



import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The controller for a specific game of pyramid solitaire using the cards and model of this
 * program.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private final Readable input; // the source of game information for this controller
  private final Appendable output; // the location at which this game will be output
  private final Scanner scan; // a scanner for reading the game information from input
  private PyramidSolitaireModel<?> model; // the model that holds information for this game
  private boolean quit = false; // whether the game has been quit: true if quit, false if not quit
  private int inputResult; // the row or card number given by the user in a move
  private PyramidSolitaireView view; // the view to which this controller passes information


  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("The provided model is null.");
    }
    else if (deck == null) {
      throw new IllegalArgumentException("The provided deck is null.");
    }
    else {
      try {
        this.model = model;
        model.startGame(deck, shuffle, numRows, numDraw); // starts the game
        this.view = new PyramidSolitaireTextualView(model, this.output);

        while (this.scan.hasNext() && !model.isGameOver() && !this.quit) {
          view.render(); // renders the view of the game, in whatever state it is currently in
          this.output.append('\n');

          this.output.append("Score: " + model.getScore() + '\n'); // shows current score
          // score = sum of values of cards in pyramid

          try {
            String input = this.scan.next();
            switch (input) { // switch case for first argument, which describes move type
              case "rm1" : // argument for removing one card from the pyramid
                this.rm1();
                break;

              case "rm2" : // argument for removing two cards from game
                this.rm2();
                break;

              case "rmwd" : // argument for removing one card from draw pile and one card from
                // pyramid
                this.rmwd();
                break;
              case "dd" : // argument for discarding one card from the draw pile
                this.dd();
                break;
              case "q" : // arguments for quitting the game
              case "Q" :
                this.quit = true;
                break;
              default :
                this.output.append("Invalid input. Try again. ");
            }
          }
          catch (IllegalArgumentException e) {
            this.output.append("Invalid move. Play again. " + e.getMessage() + '\n');
          }
        }

        if (this.quit) {
          this.quit(); // if game is quit display game quit view
        }

        else if (model.isGameOver()) {
          this.view.render(); // if game is over display game over view
        }

        else {
          throw new IllegalStateException("No input left, but game is not over.");
        }

      }
      catch (IOException e) {
        throw new IllegalStateException("The controller cannot receive input or provide output.");
      }
      catch (IllegalArgumentException e) {
        throw new IllegalStateException("The game could not be started.");
      }
    }
  }

  /**
   * Removes a single card from the pyramid.
   *
   * @throws IOException if there is a problem inputting/outputting information
   */
  private void rm1() throws IOException {
    int row; // row of the card to be removed
    int card; // position in row of card to be removed

    this.getValidInput(); // gets next argument, should be row number or quit

    row = this.inputResult - 1; // set row to row number given by user

    this.getValidInput(); // get next argument, should be card position or quit

    card = this.inputResult - 1; // set card to card position given by user

    model.remove(row, card); // removes the card at the given position
  }

  /**
   * Removes two cards from the pyramid.
   *
   * @throws IOException if there is a problem inputting/outputting information.
   */
  private void rm2() throws IOException {
    int row1; // row of first card to be removed
    int card1; // position in row of first card to be removed
    int row2; // row of second card to be removed
    int card2; // position in row of second card to be removed

    this.getValidInput();

    row1 = this.inputResult - 1;

    this.getValidInput();

    card1 = this.inputResult - 1;

    this.getValidInput();

    row2 = this.inputResult - 1;

    this.getValidInput();

    card2 = this.inputResult - 1;

    model.remove(row1, card1, row2, card2); // removes the cards at positions provided
    // by user from the model
  }

  /**
   * Removes one card from the pyramid and one card from the draw pile.
   *
   * @throws IOException if there is a problem inputting/outputting information
   */
  private void rmwd() throws IOException {
    int drawIndex; // the index of the card to be removed from the draw pile
    int rowP; // the row of the card to be removed from the pyramid
    int cardP; // the position in the row of the card to be removed from the pyramid

    this.getValidInput();

    drawIndex = this.inputResult - 1;

    this.getValidInput();

    rowP = this.inputResult - 1;

    this.getValidInput();

    cardP = this.inputResult - 1;

    model.removeUsingDraw(drawIndex, rowP, cardP); // removes the cards at the given positions from
    // the model
  }

  /**
   * Discards a card from the draw pile.
   *
   * @throws IOException if there is a problem inputting/outputting information.
   */
  private void dd() throws IOException {
    int drawIndex1; // the index of the card to be removed from the draw pile

    this.getValidInput();

    drawIndex1 = this.inputResult - 1;

    model.discardDraw(drawIndex1); // removes the card at the given index from the model
  }


  /**
   * Displays message for game that has been quit.
   *
   * @throws IOException if an input/output cannot be processed
   */
  private void quit() throws IOException {
    this.output.append("Game quit!" + '\n');
    this.output.append("State of the game when quit:" + '\n');
    this.view.render();
    this.output.append('\n');
    this.output.append("Score: " + model.getScore() + '\n');
  }


  /**
   * Prompts user for input until a valid input is given.
   *
   * @return the valid user input
   * @throws IOException if the input cannot be received or an output cannot be transmitted.
   */
  private void getValidInput() throws IOException {
    while (this.scan.hasNext()) {
      String result = this.scan.next();
      try {
        this.inputResult = Integer.parseInt(result);
        break;
      }
      catch (NumberFormatException e) {
        if (result.equals("q") || result.equals("Q")) {
          this.quit = true;
          break;
        }
        else {
          this.output.append("Invalid input. Try again.");
          this.output.append('\n');
        }
      }
    }
  }

  /**
   * Constructor for a controller for a game of pyramid solitaire.
   *
   * @param rd the given input
   * @param ap the corresponding output
   * @throws IllegalArgumentException if either the input ot output are null
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null) {
      throw new IllegalArgumentException("Unable to process null input.");
    }
    else if (ap == null) {
      throw new IllegalArgumentException("Output is null.");
    }
    else {
      this.input = rd;
      this.output = ap;
      this.scan = new Scanner(this.input);
    }
  }

}
