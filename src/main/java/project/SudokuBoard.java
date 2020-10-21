package project;

import java.util.Random;

public class SudokuBoard {
    public static final int SIZE = 9;
    private final int[][] board = new int[SIZE][SIZE];

    public int getNumberFromPosition(int xPos, int yPos) {
        return board[xPos][yPos];
    }

    public void generateNumbersOnBoard() {
        Random random = new Random();
        for (int i = 1; i <= 8; i++) {
            int[] positions = {random.nextInt(8), random.nextInt(8)};
            if (board[positions[0]][positions[1]] == 0) {
                board[positions[0]][positions[1]] = i;
            }
            else {
                i--;
            }
        }
    }

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
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean sudokuRules(int row, int col, int number) {
        return !rowEligibility(row, number) && !colEligibility(col, number)
                && !boxEligibility(row, col, number);
    }

    public boolean fillBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int number = 1; number <= SIZE; number++) {  //inserting numbers
                        if (sudokuRules(row, col, number)) {
                            board[row][col] = number;
                            if (fillBoard()) {
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
        final String HORIZONTAL_BREAK = "-------------------------\n";
        stringBuilder.append(HORIZONTAL_BREAK);
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                stringBuilder.append(HORIZONTAL_BREAK);
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
        stringBuilder.append(HORIZONTAL_BREAK);
        return stringBuilder.toString();
    }
}
