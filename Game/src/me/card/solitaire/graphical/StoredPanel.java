package me.card.solitaire.graphical;

import me.card.solitaire.general.Board;
import me.card.solitaire.general.card.Card;
import me.card.solitaire.general.card.SuitType;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class StoredPanel extends JPanel {

    private GameInterface main;

    public StoredPanel(GameInterface main) {
        this.main = main;
        setBackground(Color.GREEN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int maxWidth = getX() + getWidth();
        int count = SuitType.values().length+1;
        Board b = main.getBoard();
        for(SuitType st : SuitType.values()){
            List<Card> cards = b.getStored(st);
            int size = cards.size();
            if(size==0){
                main.drawCard(g, maxWidth-100*count, 10, null);
            }else {
                main.drawCard(g, maxWidth-100*count, 10, cards.get(size-1));
            }
            count--;
        }
    }
}
