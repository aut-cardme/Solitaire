package me.card.solitaire.console;

import me.card.solitaire.general.Board;

import java.util.Scanner;

public class ConsoleMain {

    public static void main(String[] args) {
        String input = "";

        Scanner sc = new Scanner(System.in);
        //System.out.println("Select difficulty (1, 2 or 3) - how many cards are drawn from the deck at one time");
        int diff = 1;

        Board board = new Board();

        while (!input.equals("e") && !board.isFinished()) {
            board.printBoard();

            System.out.println("(m)ove card   (d)raw card   (s)tore card");
            System.out.println("(lo)ad game   (sa)ve game   (e)nd game");
            input = sc.nextLine();

            switch (input) {
                case "m": {
                    // move selected card to designated position
                    System.out.println("From: (Select Card or D for deck) ");
                    String anInput = sc.nextLine().toLowerCase();

                    if (anInput.equals("d")){
                        System.out.println("To: (Select Column) ");
                        int moveTo = sc.nextInt();
                        sc.nextLine();
                        board.moveDeckTo(moveTo);
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

                    System.out.println(row);
                    System.out.println(col);
                    board.selectCard(row, col);
                    board.moveSelected(moveTo);
                    break;
                }
                case "d":
                    // draw top card of deck
                    board.nextDeck();

                    break;
                case "s": {

                    System.out.println("From: (Select Card or D for deck) ");
                    String anInput = sc.nextLine();

                    if (anInput.toLowerCase().equals("d")){
                        board.storeDeck();
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
                    board.storeSelected();

                    break;
                }
                case "lo":
                    // load game from file
                    break;
                case "sa":
                    // save game to file
                    break;
                case "e":
                    // exit game - possibly auto save when closed
                    break;

            }

        }
    }
}
