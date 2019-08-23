/*
 * Copyright Ashley Thew, Connor Teh-Hall 2019.
 * Project for AUT University, Program Design & Construction
 */

package me.card.solitaire.general.card;

/**
 * The enum Suit type.
 */
public enum SuitType {
    /**
     * Hearts suit type.
     */
    HEARTS("\u001B[31m♥\u001B[0m"),
    /**
     * Diamonds suit type.
     */
    DIAMONDS("\u001B[31m♦\u001B[0m"),
    /**
     * Spades suit type.
     */
    SPADES("♠"),
    /**
     * Clubs suit type.
     */
    CLUBS("♣");

    /**
     * The Id.
     */
    String id;

     SuitType(String id){
        this.id = id;
     }

    /**
     * Get id string.
     *
     * @return the string
     */
    public String getID(){
         return id;
     }

    /**
     * Is red boolean.
     *
     * @return the boolean
     */
    public boolean isRed(){
         return ordinal() < 2;
     }

    /**
     * Is black boolean.
     *
     * @return the boolean
     */
    public boolean isBlack(){
         return !isRed();
     }
}
