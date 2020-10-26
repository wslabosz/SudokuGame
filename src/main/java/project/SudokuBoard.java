package project;

import java.util.*;

public class SudokuBoard {
    public SudokuBoard(SudokuSolver sudokuSolver){
        this.sudokuSolver = sudokuSolver;
    }
    public static final int SIZE = 9;
    private final int[][] board = new int[SIZE][SIZE];
    private final SudokuSolver sudokuSolver;

    public int getNumberFromPosition(int xpos, int ypos) {
        return board[xpos][ypos];
    }

    public void setNumber(int xpos,int ypos, int number) {
        // can implement exception
        if (number > 0 && number < 10) {
            if (board[xpos][ypos] == 0) {
                board[xpos][ypos] = number;
            }
        }
    }

//    private void generateNumbersOnBoard() {
//        Random random = new Random();
//        for (int i = 1; i <= 9; i++) {
//            int[] positions = {random.nextInt(8), random.nextInt(8)};
//            if (board[positions[0]][positions[1]] == 0) {
//                board[positions[0]][positions[1]] = i;
//            } else {
//                i--;
//            }
//        }
//    }

    private boolean rowEligibility(int row, int number) {
        for (int i = 0; i < SIZE; i++) {
            //checking if the plausible number is already in row
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean colEligibility(int col, int number) {
        for (int i = 0; i < SIZE; i++) {
            //checking if the plausible number is already in column
            if (board[i][col] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean boxEligibility(int row, int col, int number) {
        int rows = row - row % 3;
        int columns = col - col % 3;
        for (int i = rows; i < rows + 3; i++) {
            for (int j = columns; j < columns + 3; j++) {
                //checking if the plausible number is already in column
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean sudokuRules(int row, int col, int number) {
        return !rowEligibility(row, number) && !colEligibility(col, number)
                && !boxEligibility(row, col, number);
    }

    public void fillBoard() {
        for (int[] row:board) {
            Arrays.fill(row, 0);
        }
        solve();
    }


    private boolean solve() {
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> randomizedNumbers = Arrays.asList(numbers);
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int i = 0; i < SIZE; i++) {  //inserting numbers
                        // zadeklarowac tablice mieszac i wpisywac za generowanie
                        Collections.shuffle(randomizedNumbers);
                        if (sudokuRules(row, col, randomizedNumbers.get(i))) {
                            board[row][col] = randomizedNumbers.get(i);
                            if (solve()) {
                                return true;
                            } else {
                                board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        final String border = "=========================\n";
        stringBuilder.append(border);
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                stringBuilder.append(border);
            }
            stringBuilder.append("| ");
            for (int j = 0; j < 9; j++) {
                stringBuilder.append(board[i][j])
                        .append(" ");
                if (j == 2 || j == 5) {
                    stringBuilder.append("| ");
                }
            }
            stringBuilder.append("|\n");

        }
        stringBuilder.append(border);
        return stringBuilder.toString();
    }
}
