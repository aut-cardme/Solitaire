package me.card.solitaire.graphical;

import me.card.solitaire.general.Board;
import me.card.solitaire.general.card.Card;
import me.card.solitaire.general.card.SuitType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StoredPanel extends JPanel implements GraphicsPainter {

    private GameInterface main;

    public SuitType dragging;

    public StoredPanel(GameInterface main) {
        this.main = main;
        setBackground(new Color(18, 117, 5));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Point point = main.getMouseLocation();
                int column = point.getX() / 100 - 3;
                if (column >= 0) {
                    dragging = SuitType.values()[column];
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                //END OF DRAG
                if (dragging != null && e.getY() > getHeight()) {
                    Point point = main.getMouseLocation();
                    int column = point.getX() / 100;
                    main.getBoard().moveTopStoredTo(dragging, column);
                }
                dragging = null;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int maxWidth = getWidth();
        int count = SuitType.values().length - 1;
        Board b = main.getBoard();
        for (SuitType st : SuitType.values()) {
            List<Card> cards = b.getStored(st);
            int size = cards.size();
            if (size == 0 || dragging == st) {
                main.drawCard(g, maxWidth - 90 - 100 * count, 10, null);
            } else {
                main.drawCard(g, maxWidth - 90 - 100 * count, 10, cards.get(size - 1));
            }
            count--;
        }
    }

    @Override
    public void painter(Graphics g) {
        if (dragging != null) {
            Point point = main.getMouseLocation();
            if (point.getY() > getHeight() || point.getX() > getWidth()) {
                List<Card> stored = main.getBoard().getStored(dragging);
                if (stored.size() > 0) {
                    Card card = stored.get(stored.size() - 1);
                    main.drawCard(g, point.getX() - 40, point.getY(), card);
                }
            }
        }
    }
}
