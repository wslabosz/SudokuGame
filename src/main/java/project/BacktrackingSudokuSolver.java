package project;

import static project.SudokuBoard.SIZE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {

    private static final Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static final List<Integer> randomizedNumbers = Arrays.asList(numbers);

    @Override
    public void solve(SudokuBoard board) {
        Collections.shuffle(randomizedNumbers);
        solution(board);
    }

    private boolean solution(SudokuBoard board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getNumberFromPosition(row, col) == 0) {
                    for (int i = 0; i < SIZE; i++) {  //inserting numbers
                        // zadeklarowac tablice mieszac i wpisywac za generowanie
                        board.setNumber(row, col, randomizedNumbers.get(i));
                        if (board.getRow(row).verify() && board.getColumn(col).verify()
                                && board.getBox(row, col).verify()) {
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