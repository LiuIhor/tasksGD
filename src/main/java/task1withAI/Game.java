package task1withAI;

import java.util.Scanner;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);

    public void start() {
        GameBoard gameBoard = new GameBoard();

        while (true) {
            System.out.print("Input command: ");
            String[] command;
            while (true) {
                command = scanner.nextLine().split(" ");
                if (command[0].equals("exit")) {
                    break;
                }
                if (command.length < 3) {
                    System.out.println("Bad parameters!");
                } else {
                    break;
                }
            }
            if (command[0].equals("exit")) {
                break;
            }
            Player player1 = new Player(Symbol.X, Level.valueOf(command[1].toUpperCase()));
            Player player2 = new Player(Symbol.O, Level.valueOf(command[2].toUpperCase()));

            gameBoard.initField();
            gameBoard.printField();

            int result;
            while (true) {
                player1.move(gameBoard);
                gameBoard.printField();
                result = gameBoard.checkField();
                if (result != 0) {
                    break;
                }
                player2.move(gameBoard);
                gameBoard.printField();
                result = gameBoard.checkField();
                if (result != 0) {
                    break;
                }
            }
            printResult(result);
        }
    }

    public static void printResult(int result) {
        switch (result) {
            case 1 -> System.out.println("X wins");
            case 2 -> System.out.println("O wins");
            case 3 -> System.out.println("Draw");
        }
    }
}