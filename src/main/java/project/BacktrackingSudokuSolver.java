package project;

import static project.SudokuBoard.SIZE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {

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
                        board.setNumber(row, col ,randomizedNumbers.get(i));
                        if (board.getRow(row).verify() && board.getColumn(col).verify() && board.getBox(row, col).verify()) {
                            if (solution(board)) {
                                return true;
                            }
                        } else {
                            board.setNumber(row, col, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}