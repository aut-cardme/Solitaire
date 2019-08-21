/*
 * Copyright (c) 2019.
 * Project for AUT University, Program Design & Construction
 */

package me.card.solitaire.general;

import me.card.solitaire.general.card.Card;
import me.card.solitaire.general.card.CardType;
import me.card.solitaire.general.card.SuitType;

import java.util.*;

/**
 * The type Board.
 */
public class Board {


    /**
     * The constant COLUMN_AMOUNT.
     */
    public static final int COLUMN_AMOUNT = 7;

    private List<Card>[] columns;
    private List<Card>[] stored;
    private List<Card> deck;
    private int selectedRow = -1, selectedColumn = -1;
    private int deckPoint = -1;

    /**
     * Instantiates a new Board.
     */
    public Board() {
        setup();
    }

    /**
     * Gets selected row.
     *
     * @return the selected row
     */
    public int getSelectedRow() {
        return selectedRow;
    }

    /**
     * Gets selected column.
     *
     * @return the selected column
     */
    public int getSelectedColumn() {
        return selectedColumn;
    }

    /**
     * Gets selected card.
     *
     * @return the selected card
     */
    public Card getSelectedCard() {
        return getCard(selectedRow, selectedColumn);
    }

    /**
     * Has the board selected a card
     *
     * @return if a card has been selected
     */
    public boolean hasSelected() {
        return getCard(selectedRow, selectedColumn) != null;
    }

    /**
     * Clear selection.
     */
    public void clearSelection() {
        selectedRow = -1;
        selectedColumn = -1;
    }

    /**
     * Checks the stored cards to see if the game is finished
     *
     * @return if all the cards have been stored
     */
    public boolean isFinished() {
        for (int i = 0; i < stored.length; i++) {
            if (stored.length != CardType.values().length) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks each column and returns the maximum height of the board
     *
     * @return the board maximum height
     */
    public int getBoardHeight() {
        int height = 0;
        for (List<Card> column : columns) {
            height = Math.max(height, column.size());
        }
        return height;
    }

    /**
     * Setup for the board, can be used to reset the board also
     */
    public void setup() {
        columns = new List[COLUMN_AMOUNT];
        stored = new List[SuitType.values().length];
        deck = new ArrayList<>(CardType.values().length * SuitType.values().length);

        Arrays.stream(CardType.values()).forEach(ct -> {
            Arrays.stream(SuitType.values()).forEach(st -> {
                deck.add(new Card(st, ct));
            });
        });
        Collections.shuffle(deck);

        for (int column = 0; column < COLUMN_AMOUNT; column++) {
            List<Card> colCards = new ArrayList<>();
            for (int row = 0; row < column; row++) {
                Card hidden = getRandomAndRemove(deck);
                hidden.setHidden(true);
                colCards.add(hidden);
            }
            Card visible = getRandomAndRemove(deck);
            colCards.add(visible);
            columns[column] = colCards;
        }

        Arrays.stream(SuitType.values()).forEach(st -> {
            stored[st.ordinal()] = new ArrayList<>();
        });

        selectedColumn = -1;
        selectedRow = -1;
        deckPoint = -1;
    }

    /**
     * Setup for the test case
     */

    public void setupTest() {
        Card card = new Card(SuitType.SPADES, CardType.NINE);
        Card card1 = new Card(SuitType.HEARTS, CardType.EIGHT);
        Card card2 = new Card(SuitType.SPADES, CardType.SEVEN);
        columns[4].set(4, card);
        columns[4].add(card1);
        columns[6].remove(6);
        columns[6].set(5,card2);

    }


    private static final Random random = new Random();

    private Card getRandomAndRemove(List<Card> list) {
        return list.remove(random.nextInt(list.size()));
    }

    /**
     * Move selected card to another column
     * Removes card from original column and clears selections when complete
     *
     * @param toColumn the to column
     * @return if the move was successful
     */
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

    /**
     * Move the top card in the deck to a column
     * Removes the card from the deck when complete
     *
     * @param toColumn the to column
     * @return if the move was successful
     */
    public boolean moveDeckTo(int toColumn) {
        if (deckPoint == -1) {
            return false;
        }
        Card card = deck.get(deckPoint);
        Card toTop = getTopCard(toColumn);
        if (toTop != null && (!card.isOppositeColor(toTop) || !toTop.getCardNo().isTypeAfter(card.getCardNo()))) {
            return false;
        }
        columns[toColumn].add(card);
        deck.remove(deckPoint);
        deckPoint -= 1;
        return true;
    }

    /**
     * Reveals the next card in the deck
     * At the end of the deck, it then resets to not showing value
     *
     * @return if the deck has a next card toi draw
     */
    public boolean nextDeck() {
        if (deck.size() == 0) {
            return false;
        }
        deckPoint += 1;
        if (deckPoint == deck.size()) {
            deckPoint = -1;
        }
        return true;
    }

    /**
     * Store selected card and cards below
     *
     * @return if it was correctly able to store the cards
     */
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

    /**
     * Store single card
     *
     * @param card the card to store
     * @return if was able to store card
     */
    public boolean storeCard(Card card) {
        List<Card> cards = stored[card.getSuit().ordinal()];
        CardType before = cards.size() == 0 ? null : cards.get(cards.size() - 1).getCardNo();
        if (card.getCardNo().isTypeAfter(before)) {
            cards.add(card);
            return true;
        }
        return false;
    }

    /**
     * Store the top card on the deck
     * Removes card on completion
     *
     * @return if was able to store
     */
    public boolean storeDeck() {
        if (deckPoint == -1) {
            return false;
        }
        Card card = deck.get(deckPoint);
        if (!storeCard(card)) {
            return false;
        }
        deck.remove(deckPoint);
        deckPoint -= 1;
        return true;
    }

    /**
     * Select card at index
     *
     * @param row the row
     * @param col the column
     * @return if was able to select the card
     */
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

    /**
     * Get top card from column
     *
     * @param column the column
     * @return the top card
     */
    public Card getTopCard(int column) {
        List<Card> cards = columns[column];
        return cards.size() > 0 ? cards.get(cards.size() - 1) : null;
    }

    /**
     * Gets card at index
     *
     * @param row    the row
     * @param column the column
     * @return the card
     */
    public Card getCard(int row, int column) {
        try {
            return columns[column].get(row);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    /**
     * Is row and column valid on the board
     *
     * @param row    the row
     * @param column the column
     * @return if is valid position on the board
     */
    public boolean isValid(int row, int column) {
        return row >= 0 && row < 5 && column >= 0 && column < 5;
    }

    /**
     * Remove card at index
     * Reveals hidden card when complete
     *
     * @param row    the row
     * @param column the column
     * @return was successful in removing the card
     */
    public boolean removeCard(int row, int column) {
        Card card = getCard(row, column);
        if (card == null) {
            return false;
        }
        columns[column].remove(row);
        revealCard(row - 1, column);
        return true;
    }

    /**
     * Reveal if possible
     *
     * @param row    the row
     * @param column the column
     * @return if the card was revealed
     */
    public boolean revealCard(int row, int column) {
        Card before = getCard(row, column);
        return before != null && before.setHidden(false);
    }

    /**
     * Gets card representation.
     *
     * @param row    the row
     * @param column the column
     * @return the card representation or ?? if hidden
     */
    public String getCardRepresentation(int row, int column) {
        Card card = getCard(row, column);
        if (card == null) {
            return "  ";
        }
        return card.isHidden() ? "??" : card.getDisplay();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Board board = new Board();
        board.setupTest();
        board.printBoard();
        System.out.println(board.selectCard(6,5));
    }

    /**
     * Print board.
     */
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
        System.out.print("Deck: ");
        if (deckPoint != -1) {
            System.out.print(deck.get(deckPoint).getDisplay());
        } else {
            if (deck.size() > 0) {
                System.out.print("??");
            } else {
                System.out.print("Empty");
            }
        }
        System.out.println();
    }
}
