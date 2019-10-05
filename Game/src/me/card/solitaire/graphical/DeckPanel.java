package me.card.solitaire.graphical;

import me.card.solitaire.general.card.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeckPanel extends JPanel implements GraphicsPainter {

    private GameInterface main;

    private boolean dragging;

    public DeckPanel(GameInterface main) {
        this.main = main;
        setPreferredSize(new Dimension(120, 120));
        setBackground(new Color(18, 117, 5));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    main.getBoard().storeDeck();
                } else {
                    main.getBoard().nextDeck();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (main.getBoard().getTopDeck() != null) {
                    dragging = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                //END OF DRAG
                if (dragging) {
                    if (e.getY() > getHeight()) {
                        Point point = main.getMouseLocation();
                        int column = point.getX() / 100;
                        main.getBoard().moveDeckTo(column);
                    } else if (e.getX() > getWidth()) {
                        main.getBoard().storeDeck();
                    }
                    dragging = false;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (main.getBoard().getDeckSize() > 0) {
            Point point = main.getMouseLocation();
            if (dragging && (point.getY() > getHeight() || point.getX() > getWidth())) {
                main.drawCard(g, 10, 10, main.getBoard().getBehindTopDeck());
            } else {
                Card card = main.getBoard().getTopDeck();
                if (main.getBoard().getDeckSize() != 1) {
                    main.drawCard(g, 10, 10, main.getBoard().getBehindTopDeck());
                }
                main.drawCard(g, card == null ? 10 : 40, 10, card);
            }
        }
    }

    @Override
    public void painter(Graphics g) {
        if (dragging) {
            Point point = main.getMouseLocation();
            if (point.getY() > getHeight() || point.getX() > getWidth()) {
                main.drawCard(g, point.getX() - 40, point.getY(), main.getBoard().getTopDeck());
            }
        }
    }
}
