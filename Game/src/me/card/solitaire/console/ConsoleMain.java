package me.card.solitaire.console;

import me.card.solitaire.general.Board;
import me.card.solitaire.general.io.StatisticsFileIO;
import me.card.solitaire.general.statistics.PlayerStatistics;

import java.util.List;
import java.util.Scanner;

public class ConsoleMain {

    private static final String statsFile = "stats.txt";

    public static void main(String[] args) {
        Board board = new Board();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = "";
        while(name.isEmpty()){
            name = sc.nextLine();
        }
        List<PlayerStatistics> statisticsList = StatisticsFileIO.load(statsFile);
        PlayerStatistics best = null;
        for (PlayerStatistics stats : statisticsList) {
            if(stats.getName().equals(name)){
                if(best==null || stats.getMoves() < best.getMoves()){
                    best = stats;
                }
            }
        }

        if(best==null){
            System.out.println("You have no previous recorded statistics.");
        }else{
            System.out.println("Your best game was " + best.getMoves() + " moves, taking " + best.getSeconds() + " seconds.");
        }

        PlayerStatistics newStats = new PlayerStatistics(name);
        long started = System.currentTimeMillis();

        String input = "";
        while (!input.equals("e") && !board.isFinished()) {

            board.printBoard();

            System.out.println("(m)ove card   (d)raw card    (s)tore card");
            System.out.println("(st)ats       (a)uto-store   (e)nd game");
            input = sc.nextLine();

            switch (input) {
                case "m": {
                    // move selected card to designated position
                    System.out.println("From: (Select Card - 34 (rowcolumn) or D for deck) ");
                    String anInput = sc.nextLine().toLowerCase();

                    if (anInput.equals("d")){
                        System.out.println("To: (Select Column) ");
                        int moveTo = sc.nextInt();
                        sc.nextLine();
                        if(board.moveDeckTo(moveTo)) {
                            newStats.incrementMoves();
                        }
                        break;
                    }
                    int card = Integer.parseInt(anInput);

                    int row;
                    int col;

                    row = card / 10;
                    if (card < 100) {
                        col = card % 10;
                    } else {
                        col = card % 100;
                    }

                    System.out.println("To: (Select Column) ");
                    int moveTo = sc.nextInt();
                    sc.nextLine();

                    board.selectCard(row, col);
                    if(board.moveSelected(moveTo)) {
                        newStats.incrementMoves();
                    }
                    break;
                }
                case "d":
                    // draw top card of deck
                    if(board.nextDeck()){
                        newStats.incrementMoves();
                    }
                    break;
                case "s": {
                    // stores the selected card
                    System.out.println("From: (Select Card or D for deck) ");
                    String anInput = sc.nextLine();

                    if (anInput.toLowerCase().equals("d")){
                        if(board.storeDeck()) {
                            newStats.incrementMoves();
                        }
                        break;
                    }
                    int card = Integer.parseInt(anInput);

                    int row;
                    int col;

                    if (card < 100) {
                        row = card / 10;
                        col = card % 10;
                    } else {
                        row = card / 100;
                        col = card % 100;
                    }

                    board.selectCard(row, col);
                    if(board.storeSelected()){
                        newStats.incrementMoves();
                    }
                    break;
                }
                case "st":
                    break;
                case "a":
                    if(board.storePossible()){
                        newStats.incrementMoves();
                    }
                    break;
                case "e":
                    // exit game
                    break;
            }
        }
        long taken = System.currentTimeMillis() - started;
        long seconds = taken / 1000;
        newStats.setSeconds((int)seconds);
        StatisticsFileIO.save(newStats, statsFile);
        System.out.println("You completed the game with " + newStats.getMoves() + " moves, taking " + newStats.getSeconds() + " seconds.");

    }
}
