package me.card.solitaire.general;

import me.card.solitaire.general.card.Card;
import me.card.solitaire.general.card.CardType;
import me.card.solitaire.general.card.SuitType;

import java.util.*;

public class Board {

    private static int COLUMN_AMOUNT = 6;

    private List<Card>[] columns;
    private List<Card> cards;

    public Board(){

    }

    public void setup(){
        columns = new List[COLUMN_AMOUNT];
        cards = new ArrayList<>();
        List<Card> cards = new ArrayList<>();
        Arrays.stream(CardType.values()).forEach(ct->{
            Arrays.stream(SuitType.values()).forEach(st ->{
                cards.add(new Card(st, ct));
            });
        });
        Collections.shuffle(cards);

        for(int column = 0; column < COLUMN_AMOUNT; column++){
            List<Card> colCards = new ArrayList<>();
            for(int row = 0; row < column; row++){
                Card hidden = getRandomAndRemove(cards);
                hidden.setHidden(true);
                colCards.add(hidden);
            }
            Card visible = getRandomAndRemove(cards);
            colCards.add(visible);
            columns[column] = colCards;
        }
    }

    private static Random random = new Random();

    private Card getRandomAndRemove(List<Card> list){
        return list.remove(random.nextInt(list.size()));
    }

    public int getBoardHeight(){
        int height = 0;
        for (List<Card> column : columns) {
            height = Math.max(height, column.size());
        }
        return height;
    }

    public String getCard(int row, int column){
        try {
            Card card = columns[column].get(row);
            return card.isHidden() ? "??" : card.getCardNo().getID() + card.getSuit().getID();
        }catch (IndexOutOfBoundsException e){
            return "  ";
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setup();
        int height = board.getBoardHeight();
        StringBuilder builder = new StringBuilder();
        for(int column = 0; column < COLUMN_AMOUNT; column++){
            builder.append("%s ");
        }
        String rows = builder.toString();

        for(int row = 0; row < height; row++){
            String[] cols = new String[COLUMN_AMOUNT];
            for(int column = 0; column < COLUMN_AMOUNT; column++){
                cols[column] = board.getCard(row, column);
            }
            System.out.println(String.format(rows, cols));
        }

    }
}
