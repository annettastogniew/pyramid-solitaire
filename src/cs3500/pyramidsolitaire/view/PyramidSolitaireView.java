package cs3500.pyramidsolitaire.view;

import java.io.IOException;

/**
 * Any generic view for a game of pyramid solitaire.
 */
public interface PyramidSolitaireView {

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;

}
