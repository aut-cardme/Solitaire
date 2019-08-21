package me.card.solitaire.console;

import me.card.solitaire.general.Board;

import java.util.Scanner;

public class ConsoleMain {

    public static void main(String[] args) {
        int input = 0;

        Scanner sc = new Scanner(System.in);
        //System.out.println("Select difficulty (1, 2 or 3) - how many cards are drawn from the deck at one time");
        int diff = 1;

        Board board = new Board();

        while (input!=6 && !board.isFinished()) {
            board.printBoard();

            //Difficulty being drawing 1,2, or 3 cards
            System.out.println("[1] Move card   [2] Draw cards   [3] Store card");
            System.out.println("[4] Load game   [5] Save game    [6] End game");
            input = sc.nextInt();
            sc.nextLine();

            switch (input) {
                case 1: {
                    // move selected card to designated position
                    System.out.println("From: (Select Card or D for deck) ");
                    String anInput = sc.nextLine();

                    if (anInput.toLowerCase().equals("d")){
                        System.out.println("To: (Select Column) ");
                        int moveTo = sc.nextInt();
                        sc.nextLine();
                        board.moveDeckTo(moveTo);
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

                    System.out.println("To: (Select Column) ");
                    int moveTo = sc.nextInt();
                    sc.nextLine();

                    System.out.println(row);
                    System.out.println(col);
                    board.selectCard(row, col);
                    board.moveSelected(moveTo);

                    break;
                }
                case 2:
                    // difficulty changes how many cards are drawn
                    board.nextDeck();

                    break;
                case 3: {

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
                case 4:
                    // load game from file
                    break;
                case 5:
                    // save game to file
                    break;
                case 6:
                    // exit game - possibly auto save when closed
                    break;

            }

        }
    }
}
