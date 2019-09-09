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
    HEARTS("♥"),
    /**
     * Diamonds suit type.
     */
    DIAMONDS("♦"),
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
     * Get console id string.
     *
     * @return the console string
     */
    public String getConsoleID(){
         return isRed() ? "\u001B[31m" + getID() + "\u001B[0m" : getID();
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
