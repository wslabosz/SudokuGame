package project;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static project.SudokuBoard.SIZE;

public class BacktrackingSudokuSolver implements SudokuSolver {

    private void fillBoard(SudokuBoard board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board.setNumber(i, j, 0);
            }
        }
        solve(board);
    }

    @Override
    public void solve(SudokuBoard board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getNumberFromPosition(row, col) == 0) {
                    for (int i = 0; i < SIZE; i++) {  //inserting numbers
                        // zadeklarowac tablice mieszac i wpisywac za generowanie
                        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                        List<Integer> randomizedNumbers = Arrays.asList(numbers);
                        Collections.shuffle(randomizedNumbers);
                        if (board.sudokuRules(row, col, randomizedNumbers.get(i))) {
                            board.setNumber(row, col, randomizedNumbers.get(i));
//                            if (solve(board)) {
//                                return true;
//                            } else {
                            board.setNumber(row, col, 0);
//                            }
                        }
                    }
//                    return false;
                }
            }
        }
//        return true;
    }
}
