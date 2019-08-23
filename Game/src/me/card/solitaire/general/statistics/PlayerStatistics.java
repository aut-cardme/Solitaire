/*
 * Copyright (c) 2019.
 * Project for AUT University, Program Design & Construction
 */

package me.card.solitaire.general.statistics;

public class PlayerStatistics {

    private String name;
    private int moves;
    private int seconds;

    public PlayerStatistics(String name) {
        this.name = name;
    }

    public PlayerStatistics(String name, int moves, int seconds) {
        this.name = name;
        this.moves = moves;
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public int getMoves() {
        return moves;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void incrementMoves(){
        this.moves++;
    }
}
