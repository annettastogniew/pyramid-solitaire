package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;

/**
 * A game of pyramid solitaire where two adjacent cards in different rows, where the bottom
 * card is exposed, can be removed if the sum of their values is 13.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {

  @Override
  protected boolean isExposedCard(int row, int card) {
    return ((row == (this.numRows - 1)) || this.nextRowEmpty(row, card)
        || this.hasRelaxedMatch(row, card));
  }

  /**
   * Determines if the card at the given position can make a pair in a relaxed game of pyramid
   * solitaire.
   *
   * @param row the row of the card in question
   * @param card the position in the row of the card in question
   * @return true if the card is only covered by one card, and those cards' values add to 13
   */
  private boolean hasRelaxedMatch(int row, int card) {
    Card thisCard = this.pyramid.get(row).get(card);
    Card leftNeighbor = this.pyramid.get(row + 1).get(card);
    Card rightNeighbor = this.pyramid.get(row + 1).get(card + 1);
    if (leftNeighbor == null && rightNeighbor != null) {
      return (rightNeighbor.getValue() + thisCard.getValue() == 13);
    }
    else if (rightNeighbor == null && leftNeighbor != null) {
      return (leftNeighbor.getValue() + thisCard.getValue() == 13);
    }
    else {
      return false;
    }

  }

}
