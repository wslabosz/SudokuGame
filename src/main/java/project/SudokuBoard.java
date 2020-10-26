package project;

public class SudokuBoard {
    public static final int SIZE = 9;
    private final int[][] board = new int[SIZE][SIZE];
    private final SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
    }

    public int getNumberFromPosition(int xpos, int ypos) {
        return board[xpos][ypos];
    }

    public void setNumber(int xpos, int ypos, int number) {
        // can implement exception
        if (number >= 0 && number < 10) {
            board[xpos][ypos] = number;
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

    public void solveGame() {
        sudokuSolver.solve(this);
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
