package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import java.util.ArrayList;
import java.util.List;

/**
 * A game of pyramid solitaire where there are multiple successive pyramids.
 */
public class MultiPyramidPyramidSoltaire extends BasicPyramidSolitaire {
  private final int overlap; // the number of overlapping rows in this multipyramid game
  private final int firstRowCount; // the number of cards in the first row fo this multipyramid game

  /**
   * A generic constructor for a game of multipyramid solitaire.
   */
  public MultiPyramidPyramidSoltaire() {
    super();
    this.overlap = this.getNumOverlapRows();
    this.firstRowCount = this.numInFirstRowMP();
  }

  /**
   * A construtor for a game of multipyramid solitaire that takes a deck.
   *
   * @param deck the desired deck for this game
   */
  public MultiPyramidPyramidSoltaire(List<Card> deck) {
    super(deck);
    this.overlap = this.getNumOverlapRows();
    this.firstRowCount = this.numInFirstRowMP();
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();

    for (int j = 0; j < 2; j++) {
      for (int i = 1; i <= 13; i ++) {
        deck.add(new Card(i, Suit.Hearts));
      }

      for (int i = 1; i <= 13; i ++) {
        deck.add(new Card(i, Suit.Clubs));
      }

      for (int i = 1; i <= 13; i ++) {
        deck.add(new Card(i, Suit.Diamonds));
      }

      for (int i = 1; i <= 13; i ++) {
        deck.add(new Card(i, Suit.Spades));
      }
    }

    return deck;
  }

  @Override
  protected boolean isNotValidDeck(List<Card> deck) {
    return (deck == null || deck.size() != this.getDeck().size()
        || !this.hasCorrectCards(deck));
  }

  /**
   * Determines that the given deck has exactly 2 copies of each card.
   *
   * @param deck the deck to check for duplicates
   * @return true if the deck only has 2 of each card, false if otherwise
   */
  private boolean hasCorrectCards(List<Card> deck) {
    boolean result = true;
    for (int i = 0; i < deck.size(); i++) {
      Card thisCard = deck.get(i);

      int countDups = 0;

      for (int j = 0; j < deck.size(); j++) {
        Card thatCard = deck.get(j);
        if (thisCard.equals(thatCard)) {
          countDups++;
        }
      }

      if (countDups != 2) {
        result = false;
      }

    }
    return result;
  }

  @Override
  protected int numCardsInPyramid(int numRowsInPyramid) {
    int result = 0;
    for (int i = 0; i < numRowsInPyramid; i++) {
      if (i >= ((this.numRows - this.overlap) - 1)) {
        result = result + i + this.firstRowCount;
      }
      else {
        result = result + ((i + 1) * 3);
      }
    }
    return result;
  }

  @Override
  protected ArrayList<ArrayList<Card>> dealPyramid() {

    ArrayList<ArrayList<Card>> pyramid = new ArrayList<ArrayList<Card>>();

    int count = 0;
    for (int i = 0; i < this.numRows; i++) {
      ArrayList<Card> row = new ArrayList<Card>();

      for (int j = 0; j < this.firstRowCount + i; j++) {
        Card thisCard = this.deck.get(count);
        if (this.shouldBeEmptySpot(i, j)) {
          row.add(null);
        }
        else {
          row.add(thisCard);
          count++;
        }
      }

      pyramid.add(row);
    }

    return pyramid;
  }


  private int numInFirstRowMP() {
    if ((this.numRows % 2) == 0) {
      return this.numRows + 1;
    }
    else {
      return this.numRows;
    }
  }


  private int getNumOverlapRows() {
    return (int) Math.ceil(this.numRows * 0.5);
  }

  /**
   * Determines if the given indices in this multipyramid should contain a card or be empty.
   *
   * @param row the row in the pyramid that we are referencing
   * @param card the position in the row of the pyramid that we are referencing
   * @return true if the spot should not contain a card, false if it should contain a card
   */
  private boolean shouldBeEmptySpot(int row, int card) {
    if (row >= ((this.numRows - this.overlap) - 1)) {
      return false;
    }
    else {
      int numCardsInRow = (row + 1) * 3;
      int numSpotsInRow = this.getRowWidth(row);
      int numEmptiesInRow = numSpotsInRow - numCardsInRow;
      int groupEmpties = numEmptiesInRow / 2;
      int groupCards = row + 1;
      return (card > row && card <= (groupEmpties + row)
          || card >= ((groupCards * 2) + groupEmpties) && card < (numSpotsInRow - groupCards));
    }
  }


  @Override
  public int getRowWidth(int row) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet.");
    }
    else if (row >= this.numRows) {
      throw new IllegalArgumentException("Row number is invalid.");
    }
    else {
      return row + this.firstRowCount;
    }
  }
}
