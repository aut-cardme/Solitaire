package me.card.solitaire.graphical;

import me.card.solitaire.general.Board;
import me.card.solitaire.general.card.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BoardPanel extends JPanel implements GraphicsPainter{

    private GameInterface main;

    public BoardPanel(GameInterface main) {
        this.main = main;
        setBackground(Color.CYAN);
        setPreferredSize(new Dimension(700, 570));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    Point p = getCardPoint(e);
                    if(e.getY() <= 100 + p.getY() * 30) {
                        main.getBoard().selectCard(p.getY(), p.getX());
                        main.getBoard().storeSelected();
                        main.getBoard().clearSelection();
                    }else{
                        main.getBoard().storePossible();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Point p = getCardPoint(e);
                if(e.getY() <= 100 + p.getY() * 30) {
                    main.getBoard().selectCard(p.getY(), p.getX());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (main.getBoard().getSelectedCard() != null) {
                    if(e.getY() < 0){
                        main.getBoard().storeSelected();
                        main.getBoard().clearSelection();
                    }else {
                        Point p = getCardPoint(e);
                         main.getBoard().moveSelected(p.getX());
                        main.getBoard().clearSelection();
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int maxHeight = main.getBoard().getBoardHeight();
        Board board = main.getBoard();
        for (int width = 0; width < 8; width++) {
            for (int height = 0; height < maxHeight; height++) {
                Card card = board.getCard(height, width);
                if (card != null) {
                    if (card == board.getSelectedCard()) {
                        height = maxHeight;
                    } else {
                        main.drawCard(g, width * 100 + 10, height * 30, card);
                    }
                }
            }
        }
    }

    public Point getCardPoint(MouseEvent event) {
        int column = event.getX() / 100;
        int row = event.getY() / 30;
        row = Math.min(row, main.getBoard().getBoardHeightAt(column) - 1);
        return new Point(column, row);
    }

    @Override
    public void painter(Graphics g) {
        Board board = main.getBoard();
        if (board.getSelectedCard() != null) {
            List<Card> cards = board.getSelectedCards();
            Point mouse = main.getMouseLocation();
            for (int count = 0; count < cards.size(); count++) {
                Card card = cards.get(count);
                main.drawCard(g, mouse.getX() - 40, mouse.getY() + 30 * count, card);
            }
        }
    }
}
