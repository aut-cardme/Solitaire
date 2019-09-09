/*
 * Copyright Ashley Thew, Connor Teh-Hall 2019.
 * Project for AUT University, Program Design & Construction
 */

package me.card.solitaire.general.card;

/**
 * The type Card.
 */
public class Card {

    /**
     * The Suit.
     */
    protected SuitType suit;
    /**
     * The Card no.
     */
    protected CardType cardNo;
    /**
     * The Hidden.
     */
    protected boolean hidden;

    /**
     * Instantiates a new Card.
     *
     * @param suit   the suit
     * @param cardNo the card no
     */
    public Card(SuitType suit, CardType cardNo) {
        this.suit = suit;
        this.cardNo = cardNo;
    }

    /**
     * Instantiates a new Card.
     *
     * @param suit   the suit
     * @param cardNo the card no
     * @param hidden the hidden
     */
    public Card(SuitType suit, CardType cardNo, boolean hidden) {
        this.suit = suit;
        this.cardNo = cardNo;
        this.hidden = hidden;
    }

    /**
     * Gets suit.
     *
     * @return the suit
     */
    public SuitType getSuit() {
        return suit;
    }

    /**
     * Gets card number
     *
     * @return the card number
     */
    public CardType getCardNo() {
        return cardNo;
    }

    /**
     * Is hidden
     *
     * @return if the card is hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Sets hidden.
     *
     * @param hidden the hidden
     * @return the previous value
     */
    public boolean setHidden(boolean hidden) {
        boolean oldValue = this.hidden;
        this.hidden = hidden;
        return oldValue;
    }

    /**
     * Is opposite color boolean.
     *
     * @param card the card to check
     * @return if the card is opposite color
     */
    public boolean isOppositeColor(Card card){
        return suit.isRed() ? card.getSuit().isBlack() : card.getSuit().isRed();
    }

    /**
     * Get console display info of the card
     *
     * @return the card as a string
     */
    public String getConsoleDisplay(){
        return cardNo.getID() + suit.getConsoleID();
    }

    /**
     * Get display info of the card
     *
     * @return the card as a string
     */
    public String getDisplay(){
        return cardNo.getID() + suit.getID();
    }
}
