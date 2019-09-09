package me.card.solitaire.graphical;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    public TopPanel(GameInterface main) {
        setLayout(new BorderLayout());
        DeckPanel deck = new DeckPanel(main);
        StoredPanel stored = new StoredPanel(main);
        add(deck, BorderLayout.WEST);
        add(stored);
    }
}
