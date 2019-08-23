/*
 * Copyright Ashley Thew, Connor Teh-Hall 2019.
 * Project for AUT University, Program Design & Construction
 */

package me.card.solitaire.general.card;

/**
 * The enum Card type.
 */
public enum CardType {
    /**
     * Ace card type.
     */
    ACE("A"),
    /**
     * Two card type.
     */
    TWO("2"),
    /**
     * Three card type.
     */
    THREE("3"),
    /**
     * Four card type.
     */
    FOUR("4"),
    /**
     * Five card type.
     */
    FIVE("5"),
    /**
     * Six card type.
     */
    SIX("6"),
    /**
     * Seven card type.
     */
    SEVEN("7"),
    /**
     * Eight card type.
     */
    EIGHT("8"),
    /**
     * Nine card type.
     */
    NINE("9"),
    /**
     * Ten card type.
     */
    TEN("X"),
    /**
     * Jack card type.
     */
    JACK("J"),
    /**
     * Queen card type.
     */
    QUEEN("Q"),
    /**
     * King card type.
     */
    KING("K");

    /**
     * The Id.
     */
    String id;

    CardType(String id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getID() {
        return id;
    }

    /**
     * Is type after boolean.
     *
     * @param type the type
     * @return the boolean
     */
    public boolean isTypeAfter(CardType type) {
        switch (this) {
            case ACE: {
                return type == null;
            }
            default: {
                return type != null && ordinal() - type.ordinal() == 1;
            }
        }
    }
}
