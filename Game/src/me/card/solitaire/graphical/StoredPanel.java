package me.card.solitaire.graphical;

import me.card.solitaire.general.Board;
import me.card.solitaire.general.card.Card;
import me.card.solitaire.general.card.SuitType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * The type Stored panel.
 */
public class StoredPanel extends JPanel implements GraphicsPainter, MouseListener {

    private GameInterface main;

    /**
     * The suittype being dragged
     */
    public SuitType dragging;

    /**
     * Instantiates a new Stored panel.
     *
     * @param main the game interface
     */
    public StoredPanel(GameInterface main) {
        this.main = main;
        setBackground(new Color(18, 117, 5));
        addMouseListener(this);
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = main.getMouseLocation();
        int column = point.getX() / 100 - 3;
        if (column >= 0) {
            dragging = SuitType.values()[column];
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //END OF DRAG
        if (dragging != null && e.getY() > getHeight()) {
            Point point = main.getMouseLocation();
            int column = point.getX() / 100;
            main.getBoard().moveTopStoredTo(dragging, column);
        }
        dragging = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
