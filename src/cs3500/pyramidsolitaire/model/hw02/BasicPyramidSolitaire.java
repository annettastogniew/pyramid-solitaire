package cs3500.pyramidsolitaire.model.hw02;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the model interface for a game of Pyramid Solitaire: maintains state and
 * enforces rules of gameplay.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {
  protected List<Card> deck; // the deck for this game
  protected ArrayList<ArrayList<Card>> pyramid; // the pyramid for this game
  protected ArrayList<Card> stock; // the stock pile for this game
  protected ArrayList<Card> drawPile; // the draw pile for this game
  protected int numRows = 7; // the number of rows in this game
  protected int numDraws = 3; // the number of draw cards in this game
  protected boolean started = false; // the state of game: true if started, false if not yet started

  /**
   * Default constructor for a pyramid solitaire model.
   */
  public BasicPyramidSolitaire() {
    this.deck = new ArrayList<>();
    this.pyramid = new ArrayList<>();
    this.stock = new ArrayList<>();
    this.drawPile = new ArrayList<>();
    this.numRows = -1;
    this.numDraws = -1;
    this.started = false;
  }

  /**
   * Constructor for BasicPyramidSolitaire that takes deck.
   *
   * @param deck the deck of cards for this model
   */
  public BasicPyramidSolitaire(List<Card> deck) {
    this.deck = deck;
    this.pyramid = new ArrayList<>();
    this.stock = new ArrayList<>();
    this.drawPile = new ArrayList<>();
    this.numRows = -1;
    this.numDraws = -1;
    this.started = false;
  }



  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();

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

    return deck;
  }


  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    if (this.isNotValidDeck(deck)) {
      throw new IllegalArgumentException("The given deck is invalid.");
    }
    else if (numRows < 1) {
      throw new IllegalArgumentException("The number of rows must be positive.");
    }
    else if (numDraw < 0) {
      throw new IllegalArgumentException("The number of draws must be at least zero.");
    }
    else {
      this.numRows = numRows;
      this.numDraws = numDraw;
      this.started = true;
      this.copyDeck(deck);
      if (shuffle) {
        this.deck = this.shuffleDeck(this.deck);
      }
      if ((this.numCardsInPyramid(numRows) + numDraw) > deck.size()) {
        throw new IllegalArgumentException("Not enough cards in deck to deal proposed pyramid and"
            + "draw pile.");
      }
      else {
        this.pyramid = this.dealPyramid();
        this.stock = this.makeStock();
        this.drawPile = this.dealDraw();
      }
    }

  }

  /**
   * Checks if the given deck is not a valid deck by the parameters of this model.
   *
   * @return true if the deck contains the correct 52 cards for a basic game of pyramid solitaire
   */
  protected boolean isNotValidDeck(List<Card> deck) {
    return (deck == null || deck.size() != this.getDeck().size()
        || !deck.containsAll(this.getDeck()));
  }


  /**
   * Creates a copy of the given deck in the same order if shuffle is false, or shuffled if true.
   *
   * @param deck the deck to copy
   */
  private void copyDeck(List<Card> deck) {
    ArrayList<Card> deckCopy = new ArrayList<Card>();

    for (int i = 0; i < deck.size(); i++) {
      deckCopy.add(deck.get(i));
    }

    this.deck = deckCopy;
  }

  /**
   * Shuffles the deck of this model.
   *
   * @return a list of all of the cards in the original deck, in a random order.
   */
  protected List<Card> shuffleDeck(List<Card> deck) {

    List<Card> shuffled = new ArrayList<Card>();

    while (deck.size() > 0) {
      Random rand = new Random();
      int pick = rand.nextInt(deck.size());

      shuffled.add(deck.get(pick));
      deck.remove(deck.get(pick));
    }
    return shuffled;
  }


  /**
   * Counts the number of cards need in a pyramid with the specified number of rows, by the rules
   * of this model.
   *
   * @param numRowsInPyramid the proposed number of rows in the pyramid
   * @return the number of cards needed to form the pyramid
   */
  protected int numCardsInPyramid(int numRowsInPyramid) {
    int result = 0;
    while (numRowsInPyramid > 0) {
      result = result + numRowsInPyramid;
      numRowsInPyramid = numRowsInPyramid - 1;
    }
    return result;
  }

  /**
   * Sorts the given deck into pyramid form, with the given number of rows and the given number of
   * draw cards available at a time.
   */
  protected ArrayList<ArrayList<Card>> dealPyramid() {

    ArrayList<ArrayList<Card>> pyramid = new ArrayList<ArrayList<Card>>();

    int count = 0;
    for (int i = 0; i < this.numRows; i++) {
      ArrayList<Card> row = new ArrayList<Card>();

      for (int j = 0; j <= i; j++) {
        Card thisCard = this.deck.get(count);
        row.add(thisCard);
        count++;
      }

      pyramid.add(row);
    }

    return pyramid;
  }

  /**
   * Creates the stock pile from which draw pile cards will be drawn.
   *
   * @return a list of cards for the stock pile
   */
  protected ArrayList<Card> makeStock() {
    ArrayList<Card> stockPile = new ArrayList<Card>();

    for (int i = this.numCardsInPyramid(this.numRows); i < this.deck.size(); i++) {
      stockPile.add(this.deck.get(i));
    }

    return stockPile;
  }

  /**
   * Sorts the remainder of the deck that is not in the pyramid, drawing the given number of cards
   * and keeping the remaining cards in one position (the pile).
   *
   */
  protected ArrayList<Card> dealDraw() {

    ArrayList<Card> draw = new ArrayList<Card>();

    for (int i = 0; i < this.numDraws; i++) {
      draw.add(this.stock.get(i));
    }

    this.stock.subList(0, this.numDraws).clear();
    return draw;
  }

  /**
   * Throws exception if card at given position is out of bounds.
   * @param row the row of the card
   * @param card the position in the row of the card
   */
  private void outOfBounds(int row, int card) {
    if (row < 0 || row >= this.numRows || card < 0 || card >= this.getRowWidth(row)) {
      throw new IllegalArgumentException("Out of bounds.");
    }
  }

  /**
   * Throws an IllegalStateException if the game has not been started.
   */
  private void notStarted() {
    if (!this.started) {
      throw new IllegalStateException("Game hasn't started yet.");
    }
  }

  /**
   * Throws an IllegalArgumentException if the card at the given position is null.
   *
   * @param row the row of the card
   * @param card the position in the row of the card
   */
  private void nullCard(int row, int card) {
    if (this.pyramid.get(row).get(card) == null) {
      throw new IllegalArgumentException("No card here.");
    }
  }

  /**
   * Throws an IllegalArgumentException if the card at the given position is null.
   *
   * @param drawIndex the position of the card
   */
  private void nullDrawCard(int drawIndex) {
    if (this.drawPile.get(drawIndex) == null) {
      throw new IllegalArgumentException("No card here.");
    }
  }

  /**
   * Throws an IllegalArgumentException if the given draw index is invalid.
   *
   * @param drawIndex the position of the card
   */
  private void invalidDrawIndex(int drawIndex) {
    if (drawIndex < 0 || drawIndex >= this.numDraws) {
      throw new IllegalArgumentException("Out of bounds.");
    }
  }


  @Override
  public void remove(int row1, int card1, int row2, int card2) {
    this.notStarted();
    this.outOfBounds(row1, card1);
    this.outOfBounds(row2, card2);
    this.nullCard(row1, card1);
    this.nullCard(row2, card2);
    if (!this.isExposedCard(row1, card1) || !this.isExposedCard(row2, card2)) {
      throw new IllegalArgumentException("One or more selected cards are not exposed.");
    }
    else if (row1 == row2 && card1 == card2) {
      throw new IllegalArgumentException("Must select two different cards.");
    }
    else if (this.pyramid.get(row1).get(card1).getValue()
        + this.pyramid.get(row2).get(card2).getValue() != 13) {
      throw new IllegalArgumentException("Card values do not add up to 13.");
    }
    else {
      this.pyramid.get(row1).set(card1, null);
      this.pyramid.get(row2).set(card2, null);
    }

  }


  @Override
  public void remove(int row, int card) throws IllegalArgumentException,IllegalStateException {
    this.notStarted();
    this.outOfBounds(row, card);
    this.nullCard(row, card);
    if (!this.isExposedCard(row, card)) {
      throw new IllegalArgumentException("Selected card is not exposed.");
    }
    else if (this.pyramid.get(row).get(card).getValue() != 13) {
      throw new IllegalArgumentException("Card value is not 13.");
    }
    else {
      this.pyramid.get(row).set(card, null);
    }
  }


  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException,IllegalStateException {
    this.notStarted();
    this.outOfBounds(row, card);
    this.invalidDrawIndex(drawIndex);
    this.nullCard(row, card);
    this.nullDrawCard(drawIndex);
    if (!this.isExposedCard(row, card)) {
      throw new IllegalArgumentException("Selected card is not exposed.");
    }
    else if (this.pyramid.get(row).get(card).getValue() +
        this.drawPile.get(drawIndex).getValue() != 13) {
      throw new IllegalArgumentException("Card values do not add to 13.");
    }
    else {
      this.pyramid.get(row).set(card, null);
      this.discardDraw(drawIndex);
    }
  }


  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException,IllegalStateException {
    this.notStarted();
    this.invalidDrawIndex(drawIndex);
    this.nullDrawCard(drawIndex);
    if (this.stock.size() > 0) {
      this.drawPile.set(drawIndex, this.stock.get(0));
      this.stock.remove(0);
    }
    else {
      this.drawPile.set(drawIndex, null);
    }
  }

  @Override
  public int getNumRows() {
    return this.numRows;
  }

  @Override
  public int getNumDraw() {
    return this.numDraws;
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
      return row + 1;
    }
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't been started yet.");
    }
    else {
      return this.getScore() == 0 || this.noMoreMoves();
    }
  }

  /**
   * Determines if there are no more moves or discards in this model.
   *
   * @return false if the stock and draw piles contain no cards, or if there are still moves
   *         in the exposed cards of the pyramid, true if there are no cards in stock and draw pile,
   *         and no possible moves in the pyramid
   */
  private boolean noMoreMoves() {
    if (this.stock.size() != 0 || !this.isDrawPileEmpty()) {
      return false;
    }
    else {
      return !this.doPairsExist(this.findExposedCards());
    }
  }

  /**
   * Determines if there are no cards in the draw pile.
   *
   * @return true if the list of cards is full of null elements, false if cards exist in list
   */
  private boolean isDrawPileEmpty() {
    boolean result = true;
    for (int i = 0; i < this.numDraws; i ++)  {
      if (this.drawPile.get(i) != null) {
        result = false;
        break;
      }
    }
    return result;
  }

  /**
   * All of the cards in this game's pyramid that are exposed.
   *
   * @return a list of exposed cards
   */
  private ArrayList<Card> findExposedCards() {
    ArrayList<Card> exposedCards = new ArrayList<>();

    for (int i = 0; i < this.pyramid.size(); i ++) {
      ArrayList<Card> thisRow = this.pyramid.get(i);

      for (int j = 0; j < thisRow.size(); j++) {
        Card thisCard = thisRow.get(j);

        if (this.isExposedCard(i, j) && thisCard != null) {
          exposedCards.add(thisCard);
        }
      }
    }

    return exposedCards;
  }

  /**
   * Determines if the card at the given card and row in the pyramid of this game is exposed.
   * @param row the row of the card in the pyramid
   * @param card the card's position within the row of the pyramid
   * @return true if the card is in the bottom row of the pyramid or has no neighboring cards
   *         in the next row down; false if neither of these apply
   */
  protected boolean isExposedCard(int row, int card) {
    return ((row == (this.numRows - 1)) || this.nextRowEmpty(row, card));
  }


  /**
   * Determines if the next row after the given row has empty cards in the given card position and
   * the next one over.
   *
   * @param row the row of the current card
   * @param card the card position of the current card
   * @return true if the two positions in the next row touching the card at the given position are
   *         empty, otherwise false
   */
  protected boolean nextRowEmpty(int row, int card) {
    return (((this.pyramid.get(row + 1).get(card)) == null)
        && ((this.pyramid.get(row + 1).get(card + 1)) == null));
  }


  /**
   * Determines if any pairs that add up to 13 exist within the given cards.
   *
   * @param cards exposed cards in the pyramid of this game
   * @return true if two cards add up to 13, or one card has value of 13; false if not
   */
  protected boolean doPairsExist(ArrayList<Card> cards) {

    for (int i = 0; i < cards.size(); i++) {
      Card thisCard = cards.get(i);

      if (thisCard.getValue() == 13) {
        return true;
      }

      for (int j = 0; j < cards.size(); j++) {
        Card thatCard = cards.get(j);
        if (j != i) {
          if (thisCard.getValue() + thatCard.getValue() == 13) {
            return true;
          }
        }
      }

    }

    return false;
  }

  @Override
  public int getScore() throws IllegalStateException {

    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet.");
    }

    else {
      int score = 0;

      for (int i = 0; i < this.numRows; i ++) {

        for (int j = 0; j < this.getRowWidth(i); j++) {

          if (this.pyramid.get(i).get(j) == null) {
            score = score + 0;
          }

          else {
            score = score + this.pyramid.get(i).get(j).getValue();
          }

        }
      }

      return score;
    }

  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {

    if (!this.started) {
      throw new IllegalStateException("The game hasn't been started yet.");
    }

    else if (row >= this.numRows || row < 0 || card > this.getRowWidth(row) || card < 0) {
      throw new IllegalArgumentException("The coordinates are invalid.");
    }

    else {
      Card thisCard = this.pyramid.get(row).get(card);
      if (thisCard == null) {
        return null;
      }
      else {
        return new Card(thisCard.getValue(), thisCard.getSuit());
      }
    }
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {

    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet.");
    }

    else {
      List<Card> getDraws = new ArrayList<Card>();
      for (int i = 0; i < this.drawPile.size(); i++) {
        getDraws.add(this.drawPile.get(i));
      }
      return getDraws;
    }
  }
}
