package project;

import static project.SudokuBoard.SIZE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {

    private boolean rowEligibility(int row, int number, SudokuBoard board) {
        for (int i = 0; i < SIZE; i++) {
            //checking if the plausible number is already in row
            if (board.getNumberFromPosition(row, i) == number) {
                return true;
            }
        }
        return false;
    }

    private boolean colEligibility(int col, int number, SudokuBoard board) {
        for (int i = 0; i < SIZE; i++) {
            //checking if the plausible number is already in column
            if (board.getNumberFromPosition(i, col) == number) {
                return true;
            }
        }
        return false;
    }

    private boolean boxEligibility(int row, int col, int number, SudokuBoard board) {
        int rows = row - row % 3;
        int columns = col - col % 3;
        for (int i = rows; i < rows + 3; i++) {
            for (int j = columns; j < columns + 3; j++) {
                //checking if the plausible number is already in column
                if (board.getNumberFromPosition(i, j) == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean sudokuRules(int row, int col, int number, SudokuBoard board) {
        return !rowEligibility(row, number, board) && !colEligibility(col, number, board)
                && !boxEligibility(row, col, number, board);
    }

    @Override
    public void solve(SudokuBoard board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board.setNumber(i, j, 0);
            }
        }
        solution(board);
    }

    private boolean solution(SudokuBoard board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getNumberFromPosition(row, col) == 0) {
                    for (int i = 0; i < SIZE; i++) {  //inserting numbers
                        // zadeklarowac tablice mieszac i wpisywac za generowanie
                        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                        List<Integer> randomizedNumbers = Arrays.asList(numbers);
                        Collections.shuffle(randomizedNumbers);
                        if (sudokuRules(row, col, randomizedNumbers.get(i), board)) {
                            board.setNumber(row, col, randomizedNumbers.get(i));
                            if (solution(board)) {
                                return true;
                            } else {
                                board.setNumber(row, col, 0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}