package task1withAI;

public class GameBoard {
    private static final int SIZE = 3;

    public String[][] gameField;

    public static int getSIZE() {
        return SIZE;
    }

    public void initField() {
        gameField = new String[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gameField[i][j] = Symbol.SPACE.toString();
            }
        }
    }

    public void printField() {
        System.out.println("---------");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(gameField[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public int checkField() {
        if (checkDiagWin(Symbol.X) || checkRowColWin(Symbol.X)) {
            return 1; // X wins
        } else if (checkDiagWin(Symbol.O) || checkRowColWin(Symbol.O)) {
            return 2; // O wins
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameField[i][j].equals(Symbol.SPACE.toString())) {
                    return 0; // Not finished
                }
            }
        }
        return 3; // Draw
    }

    private boolean checkDiagWin(Symbol symbol) {
        boolean leftRightDiag = true;
        boolean rightLeftDiag = true;

        for (int i = 0; i < SIZE; i++) {
            leftRightDiag &= (gameField[i][i].equals(symbol.toString()));
            rightLeftDiag &= (gameField[SIZE - i - 1][i].equals(symbol.toString()));
        }
        return leftRightDiag || rightLeftDiag;
    }

    private boolean checkRowColWin(Symbol symbol) {
        boolean cols, rows;

        for (int col = 0; col < SIZE; col++) {
            cols = true;
            rows = true;

            for (int row = 0; row < SIZE; row++) {
                cols &= (gameField[col][row].equals(symbol.toString()));
                rows &= (gameField[row][col].equals(symbol.toString()));
            }
            if (cols || rows) {
                return true;
            }
        }
        return false;
    }
}