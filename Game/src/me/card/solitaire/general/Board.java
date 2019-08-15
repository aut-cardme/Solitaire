package me.card.solitaire.general;

import me.card.solitaire.general.card.Card;
import me.card.solitaire.general.card.CardType;
import me.card.solitaire.general.card.SuitType;

import java.util.*;

public class Board {

    public static int COLUMN_AMOUNT = 7;

    private List<Card>[] columns;
    private List<Card>[] stored;
    private List<Card> deck;
    private int selectedRow = -1, selectedColumn = -1;
    private int deckPoint = -1;

    public Board() {
        setup();
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }

    public Card getSelectedCard() {
        return getCard(selectedRow, selectedColumn);
    }

    public boolean hasSelected() {
        return getCard(selectedRow, selectedColumn) != null;
    }

    public void clearSelection() {
        selectedRow = -1;
        selectedColumn = -1;
    }

    public int getBoardHeight() {
        int height = 0;
        for (List<Card> column : columns) {
            height = Math.max(height, column.size());
        }
        return height;
    }

    public void setup() {
        columns = new List[COLUMN_AMOUNT];
        stored = new List[SuitType.values().length];
        deck = new ArrayList<>(CardType.values().length * SuitType.values().length);

        List<Card> cards = new ArrayList<>();
        Arrays.stream(CardType.values()).forEach(ct -> {
            Arrays.stream(SuitType.values()).forEach(st -> {
                cards.add(new Card(st, ct));
            });
        });
        Collections.shuffle(cards);

        for (int column = 0; column < COLUMN_AMOUNT; column++) {
            List<Card> colCards = new ArrayList<>();
            for (int row = 0; row < column; row++) {
                Card hidden = getRandomAndRemove(cards);
                hidden.setHidden(true);
                colCards.add(hidden);
            }
            Card visible = getRandomAndRemove(cards);
            colCards.add(visible);
            columns[column] = colCards;
        }

        Arrays.stream(SuitType.values()).forEach(st -> {
            stored[st.ordinal()] = new ArrayList<>();
        });
    }

    public void setupTest() {
        Card card = new Card(SuitType.HEARTS, CardType.ACE);
        Card card1 = new Card(SuitType.SPADES, CardType.TWO);
        Card card2 = new Card(SuitType.HEARTS, CardType.THREE);
        columns[1].set(1, card1);
        columns[1].add(card);
        columns[2].add(card2);
        columns[0].clear();
    }

    private static Random random = new Random();

    private Card getRandomAndRemove(List<Card> list) {
        return list.remove(random.nextInt(list.size()));
    }

    public boolean moveSelected(int toColumn) {
        if (!hasSelected() || selectedColumn == toColumn) {
            return false;
        }
        Card selected = getSelectedCard();
        Card toTop = getTopCard(toColumn);
        if (toTop != null && (!selected.isOppositeColor(toTop) || !toTop.getCardNo().isTypeAfter(selected.getCardNo()))) {
            return false;
        }
        do {
            columns[toColumn].add(selected);
            removeCard(selectedRow, selectedColumn);
        } while ((selected = getSelectedCard()) != null);
        clearSelection();
        return true;
    }

    public boolean storeSelected() {
        if (!hasSelected()) {
            return false;
        }
        boolean stored = false;
        for (int row = columns[selectedColumn].size() - 1; row >= selectedRow; row--) {
            Card card = getCard(row, selectedColumn);
            if (!storeCard(card)) {
                break;
            }
            stored = true;
            removeCard(row, selectedColumn);
        }
        if (stored) {
            clearSelection();
        }
        return stored;
    }

    public boolean storeCard(Card card) {
        List<Card> cards = stored[card.getSuit().ordinal()];
        CardType before = cards.size() == 0 ? null : cards.get(cards.size() - 1).getCardNo();
        if (card.getCardNo().isTypeAfter(before)) {
            cards.add(card);
            return true;
        }
        return false;
    }

    public boolean selectCard(int row, int col) {
        if (col >= COLUMN_AMOUNT) {
            return false;
        }

        List<Card> column = columns[col];
        int col_size = column.size();
        if (col_size <= row) {
            return false;
        }

        Card check = column.get(row);
        if (check.isHidden()) {
            return false;
        }

        for (int start = row + 1; start < col_size; start++) {
            Card next = column.get(start);
            if (!check.isOppositeColor(next) && !next.getCardNo().isTypeAfter(check.getCardNo())) {
                return false;
            }
            check = next;
        }

        selectedRow = row;
        selectedColumn = col;
        return true;
    }

    public Card getTopCard(int column) {
        List<Card> cards = columns[column];
        return cards.size() > 0 ? cards.get(cards.size() - 1) : null;
    }

    public Card getCard(int row, int column) {
        try {
            return columns[column].get(row);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public boolean removeCard(int row, int column) {
        Card card = getCard(row, column);
        if (card == null) {
            return true;
        }
        columns[column].remove(row);
        revealCard(row - 1, column);
        return true;
    }

    public boolean revealCard(int row, int column) {
        Card before = getCard(row, column);
        return before != null ? before.setHidden(false) : false;
    }

    public String getCardRepresentation(int row, int column) {
        Card card = getCard(row, column);
        if (card == null) {
            return "  ";
        }
        return card.isHidden() ? "??" : card.getDisplay();
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setupTest();
        board.printBoard();
        System.out.println(board.selectCard(1, 1));
        System.out.println(board.moveSelected(0));
        board.printBoard();
    }

    public void printBoard() {
        //This part can probably be made static in actual console part
        StringBuilder rowBuilder = new StringBuilder("%s|");
        StringBuilder columnBuilder = new StringBuilder("    ");
        for (int column = 0; column < COLUMN_AMOUNT; column++) {
            rowBuilder.append("%s%s");
            columnBuilder.append(String.format("%d  ", column));
        }
        System.out.println(columnBuilder.toString());
        String rows = rowBuilder.toString();
        //Till here

        int height = getBoardHeight();
        for (int row = 0; row < height; row++) {
            String[] cols = new String[COLUMN_AMOUNT * 2 + 1];
            cols[0] = String.valueOf(row);
            for (int column = 0; column < COLUMN_AMOUNT; column++) {
                cols[column * 2 + 1] = row == selectedRow && column == selectedColumn ? ">" : " ";
                cols[column * 2 + 2] = getCardRepresentation(row, column);
            }
            System.out.println(String.format(rows, cols));
        }


        System.out.print("Stored: ");
        Arrays.stream(SuitType.values()).forEach(st -> {
            List<Card> cards = stored[st.ordinal()];
            if (cards.size() == 0) {
                System.out.print("~" + st.getID() + " ");
            } else {
                Card card = cards.get(cards.size() - 1);
                System.out.print(card.getDisplay() + " ");
            }
        });
        System.out.println();

        //DECK
    }
}
