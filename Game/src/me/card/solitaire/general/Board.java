package me.card.solitaire.general;

import me.card.solitaire.general.card.Card;
import me.card.solitaire.general.card.CardType;
import me.card.solitaire.general.card.SuitType;

import java.util.*;

public class Board {

    private static int COLUMN_AMOUNT = 6;

    private List<Card>[] columns;
    private List<Card> deck;
    private int selectedRow, selectedColumn;

    public Board(){
        //TODO maybe call setup here not sure yet
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }

    public int getBoardHeight(){
        int height = 0;
        for (List<Card> column : columns) {
            height = Math.max(height, column.size());
        }
        return height;
    }

    public void setup(){
        columns = new List[COLUMN_AMOUNT];
        deck = new ArrayList<>(CardType.values().length * SuitType.values().length);
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

    public boolean selectCard(int row, int col){
        if(col >= COLUMN_AMOUNT){
            return false;
        }

        List<Card> column = columns[col];
        int col_size = column.size();
        if(col_size <= row){
            return false;
        }

        Card check = column.get(row);
        if(check.isHidden()){
            return false;
        }

        for(int start = row + 1; start < col_size; start++){
            Card next = column.get(start);
            if(!check.isOppositeColor(next)){
                return false;
            }
            check = next;
        }

        selectedRow = row;
        selectedColumn = col;
        return true;
    }

    public String getCardRepresentation(int row, int column){
        try {
            Card card = columns[column].get(row);
            return card.isHidden() ? "??" : card.getCardNo().getID() + card.getSuit().getID();
        }catch(IndexOutOfBoundsException e){
            return "  ";
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setup();
        board.printBoard();
        System.out.println(board.selectCard(1,1));
        board.printBoard();
    }

    public void printBoard(){
        StringBuilder builder = new StringBuilder();
        for(int column = 0; column < COLUMN_AMOUNT; column++){
            builder.append("%s%s");
        }
        String rows = builder.toString();
        int height = getBoardHeight();
        for(int row = 0; row < height; row++){
            String[] cols = new String[COLUMN_AMOUNT*2];
            for(int column = 0; column < COLUMN_AMOUNT; column++){
                cols[column * 2] = row == selectedRow && column == selectedColumn ? ">" : " ";
                cols[column * 2 + 1] = getCardRepresentation(row, column);
            }
            System.out.println(String.format(rows, cols));
        }
    }
}
