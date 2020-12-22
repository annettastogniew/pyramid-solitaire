package cs3500.pyramidsolitaire.model.hw02;

/**
 * A card in a game of Pyramid Solitaire.
 */
public class Card {
  int value; // the value of this card
  Suit suit; // the suit of this card


  /**
   * Creates a card with the given value and suit and position.
   *
   * @param val the value of the card
   * @param suit the suit of the card
   * @throws IllegalArgumentException if the value of the card is not 1-13
   */
  public Card(int val, Suit suit) throws IllegalArgumentException {
    if (val > 13 || val < 0) {
      throw new IllegalArgumentException("Invalid card value.");
    }
    else {
      this.value = val;
      this.suit = suit;
    }

  }


  @Override
  public boolean equals(Object o) {
    if (o instanceof Card) {
      Card that = (Card) o;
      return (this.value == that.value)
          && (this.suit == that.suit);
    }
    else {
      return false;
    }

  }

  @Override
  public int hashCode() {
    return this.value + this.suit.hashCode();
  }

  @Override
  public String toString() {
    try {
      String result = "";

      char heart = '♥';
      char club = '♣';
      char diamond = '♦';
      char spade = '♠';

      switch (this.suit) {
        case Hearts: result = this.valToString() + heart;
          break;
        case Clubs: result = this.valToString() + club;
          break;
        case Diamonds: result = this.valToString() + diamond;
          break;
        case Spades: result = this.valToString() + spade;
          break;
        default: result = "";
      }
      return result;
    }
    catch (NullPointerException e) {
      return ".  ";
    }

  }

  /**
   * Converts the value of this card into its respective string form.
   *
   * @return the string representation of the value of this card.
   */
  private String valToString() {
    if (this.value <= 10 && this.value > 1) {
      return Integer.toString(this.value);
    }
    else if (this.value == 11) {
      return "J";
    }
    else if (this.value == 12) {
      return "Q";
    }
    else if (this.value == 13) {
      return "K";
    }
    else if (this.value == 1) {
      return "A";
    }
    else {
      return "";
    }
  }

  /**
   * Gets the value of this card.
   *
   * @return the value of this card
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Gets the Suit of this card.
   *
   * @return the suit of this card
   */
  public Suit getSuit() {
    return this.suit;
  }



}
