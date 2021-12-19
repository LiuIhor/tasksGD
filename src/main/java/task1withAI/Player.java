package task1withAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    public static Scanner scanner = new Scanner(System.in);

    private final Symbol symbol;
    private final Level level;

    public Player(Symbol symbol, Level level) {
        this.symbol = symbol;
        this.level = level;
    }

    public void move(GameBoard gameBoard) {
        switch (level) {
            case USER -> userMove(gameBoard);
            case EASY -> easyAiMove(gameBoard);
            case MEDIUM -> mediumAiMove(gameBoard);
            case HARD -> hardAiMove(gameBoard);
            default -> System.out.println("Wrong level!");
        }
    }

    public void userMove(GameBoard gameBoard) {
        do {
            int row;
            int col;
            System.out.print("Enter the coordinates: ");

            String[] coordinates = scanner.nextLine().split(" ");

            try {
                row = Integer.parseInt(coordinates[0]);
                col = Integer.parseInt(coordinates[1]);

                if (col < 1 || row > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (gameBoard.gameField[row - 1][col - 1].equals(Symbol.SPACE.toString())) {
                gameBoard.gameField[row - 1][col - 1] = symbol.toString();
                break;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        } while (true);
    }

    public boolean isPossibleMove(GameBoard gameBoard, int row, int col) {
        if (row < 0 || row > GameBoard.getSIZE() - 1 || col < 0 || col > GameBoard.getSIZE() - 1) {
            return false;
        } else return !gameBoard.gameField[row][col].equals(Symbol.X.toString()) &&
                !gameBoard.gameField[row][col].equals(Symbol.O.toString());
    }

    public void easyAiMove(GameBoard gameBoard) {
        System.out.println("Making move level \"easy\"");
        randomMove(gameBoard);
    }

    public void randomMove(GameBoard gameBoard) {
        while (true) {
            int randomRow = (int) (Math.random() * GameBoard.getSIZE());
            int randomCol = (int) (Math.random() * GameBoard.getSIZE());

            if (isPossibleMove(gameBoard, randomRow, randomCol)) {
                gameBoard.gameField[randomRow][randomCol] = symbol.toString();
                break;
            }
        }
    }

    public void mediumAiMove(GameBoard gameBoard) {
        if (!findMediumMove(gameBoard)) {
            randomMove(gameBoard);
        }
    }

    public boolean findMediumMove(GameBoard gameBoard) {
        int row = -1;
        int col = -1;
        boolean foundWin = false;

        System.out.println("Making move level \"medium\"");

        for (int i = 0; i < GameBoard.getSIZE(); i++) {
            for (int j = 0; j < GameBoard.getSIZE(); j++) {
                if (gameBoard.gameField[i][j].equals(Symbol.SPACE.toString())) {
                    gameBoard.gameField[i][j] = symbol.toString();

                    if (gameBoard.checkField() != 0) {
                        row = i;
                        col = j;
                        foundWin = true;
                        break;
                    }
                    gameBoard.gameField[i][j] =
                            (symbol.toString().equals(Symbol.X.toString()) ? Symbol.O.toString() : Symbol.X.toString());
                    if (gameBoard.checkField() != 0) {
                        row = i;
                        col = j;
                        foundWin = true;
                        break;
                    }
                    gameBoard.gameField[i][j] = Symbol.SPACE.toString();
                }
            }
            if (foundWin) {
                break;
            }
        }
        if (foundWin) {
            gameBoard.gameField[row][col] = symbol.toString();
            return true;
        }
        return false;
    }

    public void hardAiMove(GameBoard gameBoard) {
        System.out.println("Making move level \"hard\"");

        Move bestMove = minimax(gameBoard.gameField, symbol.toString(), symbol.toString());
        gameBoard.gameField[bestMove.index[0]][bestMove.index[1]] = symbol.toString();
    }

    public Move minimax(String[][] gameField, String callingPlayer, String currentPlayer) {
        List<Move> moves = new ArrayList<>();
        String enemySymbol = (callingPlayer.equals(Symbol.X.toString())) ? Symbol.O.toString() : Symbol.X.toString();
        String callingSymbol = (callingPlayer.equals(Symbol.X.toString())) ? Symbol.X.toString() : Symbol.O.toString();
        String enemyPlayer = (currentPlayer.equals(Symbol.X.toString())) ? Symbol.O.toString() : Symbol.X.toString();

        // Counting the score of this move
        if (isWin(gameField, enemySymbol)) {
            return new Move(-10);
        } else if (isWin(gameField, callingSymbol)) {
            return new Move(10);
        } else if (!isEmptyCellsLeft(gameField)) {
            return new Move(0);
        }

        for (int i = 0; i < GameBoard.getSIZE(); i++) {
            for (int j = 0; j < GameBoard.getSIZE(); j++) {
                if (gameField[i][j].equals(Symbol.SPACE.toString())) {
                    Move move = new Move();
                    move.index = new int[]{i, j};
                    gameField[i][j] = currentPlayer;
                    Move result = minimax(gameField, callingPlayer, enemyPlayer);
                    // save the score for the minimax
                    move.score = result.score;
                    gameField[i][j] = Symbol.SPACE.toString();
                    moves.add(move);
                }
            }
        }

        // Choose the move with the highest score
        int bestMove = 0;

        if (currentPlayer.equals(callingPlayer)) {
            int bestScore = -10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score > bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        } else {
            int bestScore = 10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score < bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        }

        // minimax returns the best move to the latest function caller
        return moves.get(bestMove);
    }

    private static boolean isEmptyCellsLeft(String[][] gameField) {
        boolean gotEmptiesCells = false;
        for (int i = 0; i < GameBoard.getSIZE(); i++) {
            for (int j = 0; j < GameBoard.getSIZE(); j++) {
                if (gameField[i][j].equals(Symbol.SPACE.toString())) {
                    gotEmptiesCells = true;
                    break;
                }
            }
        }
        return gotEmptiesCells;
    }

    private static boolean isWin(String[][] gameField, String playerSymbol) {
        boolean leftRightDiag = true;
        boolean rightLeftDiag = true;

        for (int i = 0; i < GameBoard.getSIZE(); i++) {
            leftRightDiag &= (gameField[i][i].equals(playerSymbol));
            rightLeftDiag &= (gameField[GameBoard.getSIZE() - i - 1][i].equals(playerSymbol));
        }

        boolean cols = false;
        boolean rows = false;

        for (int col = 0; col < GameBoard.getSIZE(); col++) {
            cols = true;
            rows = true;

            for (int row = 0; row < GameBoard.getSIZE(); row++) {
                cols &= (gameField[col][row].equals(playerSymbol));
                rows &= (gameField[row][col].equals(playerSymbol));
            }
            if (cols || rows) {
                break;
            }
        }

        return leftRightDiag || rightLeftDiag || cols || rows;
    }
}

