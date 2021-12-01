package task1;

import java.util.*;

/**
 * Project: Simple Tic-Tac-Toe
 * Worked on a project: Liu Ihor
 * */
public class Program {

    /**
     * flag to indicate the status of the game
     * true - the game is on
     * false - end of the game
     * */
    private static boolean flag = true;

    public static void main(String[] args) {
        Symbol symbol = Symbol.O;

        /**
         * Starting field
         * */
        String[] symbols = new String[9];
        Arrays.fill(symbols, Symbol.SPACE.toString());

        print(symbols);

        do {
            symbol = symbol.oppose();
            move(symbols, symbol);
            print(symbols);
            check(symbols, symbol);
        } while (flag);
    }

    /**
     * This method displays the playing field
     * */
    private static void print(String [] symbols) {
        System.out.println("---------");

        int step = 0;
        int end = 3;

        do {
            System.out.print("| ");

            for(int i = step; i < end; i++){
                System.out.print(symbols[i] + " ");
            }

            System.out.println("|");

            step += 3;
            end += 3 ;
        } while (end <= 9);

        System.out.println("---------");
    }

    /**
     * This method performs the player's turn
     * field index - playing field cell number
     * */
    private static void move(String [] symbols, Symbol symbol) {
        Scanner scanner = new Scanner(System.in);
        do {
            int row;
            int column;
            int index;

            System.out.print("Enter the coordinates: ");

            String[] coordinates = scanner.nextLine().split(" ");

            try {
                row = Integer.parseInt(coordinates[0]);
                column = Integer.parseInt(coordinates[1]);

                if (column > 3 || column < 1 || row > 3 || row < 1) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (row == 1) {
                index = -1 + column;
            } else if (row == 2) {
                index = column + 2;
            } else {
                index = column + 5;
            }

            if (symbols[index].equals(Symbol.SPACE.toString())) {
                symbols[index] = symbol.toString();
                break;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        } while (true);
    }

    /**
     * This method checks if the game is over
     * */
    private static boolean checkFinished(String[] symbols) {
        for (String symbol : symbols) {
            if (symbol.equals(Symbol.SPACE.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks the player for a win
     * */
    public static boolean  checkWins(String[] symbols, Symbol symbol) {
        boolean horizontal = (symbol.toString().equals(symbols[0]) && symbol.toString().equals(symbols[1]) && symbol.toString().equals(symbols[2]))
                ||
                (symbol.toString().equals(symbols[3]) && symbol.toString().equals(symbols[4]) && symbol.toString().equals(symbols[5]))
                ||
                (symbol.toString().equals(symbols[6]) && symbol.toString().equals(symbols[7]) && symbol.toString().equals(symbols[8]));

        boolean vertical = (symbol.toString().equals(symbols[0]) && symbol.toString().equals(symbols[3]) && symbol.toString().equals(symbols[6]))
                ||
                (symbol.toString().equals(symbols[1]) && symbol.toString().equals(symbols[4]) && symbol.toString().equals(symbols[7]))
                ||
                (symbol.toString().equals(symbols[2]) && symbol.toString().equals(symbols[5]) && symbol.toString().equals(symbols[8]));

        boolean diagonal = (symbol.toString().equals(symbols[0]) && symbol.toString().equals(symbols[4]) && symbol.toString().equals(symbols[8]))
                ||
                (symbol.toString().equals(symbols[2]) && symbol.toString().equals(symbols[4]) && symbol.toString().equals(symbols[6]));

        return vertical || horizontal || diagonal;
    }

    /**
     * This method counts game characters on the field
     * */
    private static int countSymbol(String[] symbols, Symbol symbol){
        int count = 0;

        for (String s : symbols) {
            if (s.equals(symbol.toString())){
                count++;
            }
        }
        return count;
    }

    /**
     * This method checks and displays the game status
     * */
    private static void check(String[] symbols, Symbol symbol) {
        if (checkWins(symbols, symbol) && checkWins(symbols, symbol.oppose()) ||
                Math.abs(countSymbol(symbols, symbol.oppose()) - countSymbol(symbols, symbol)) > 1) {
            flag = false;
            System.out.println("Impossible");
        } else if (checkWins(symbols, symbol)) {
            flag = false;
            System.out.println(symbol + " wins");
        } else if (!checkFinished(symbols)) {
            flag = false;
            System.out.println("Draw");
        }
    }

    private enum Symbol {
        X {
            @Override
            public String toString() {
                return "X";
            }

            @Override
            public Symbol oppose() {
                return O;
            }
        },

        O {
            @Override
            public String toString() {
                return "O";
            }

            @Override
            public Symbol oppose() {
                return X;
            }
        },

        SPACE {
            @Override
            public String toString() {
                return "_";
            }
            @Override
            public Symbol oppose() {
                return null;
            }
        };

        public abstract Symbol oppose();
    }
}

