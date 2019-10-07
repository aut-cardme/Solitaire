package me.card.solitaire.graphical;

import javax.swing.*;
import java.awt.*;

/**
 * The type Top panel.
 */
public class TopPanel extends JPanel {

    /**
     * Instantiates a new Top panel.
     *
     * @param main the main
     */
    public TopPanel(GameInterface main) {
        setLayout(new BorderLayout());
        DeckPanel deck = new DeckPanel(main);
        StoredPanel stored = new StoredPanel(main);
        add(deck, BorderLayout.WEST);
        add(stored);
    }
}
