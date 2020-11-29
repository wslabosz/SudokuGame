package project;

import static project.SudokuBoard.SIZE;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {


    @Override
    public void solve(SudokuBoard board) {
        List<Integer> randomizedNumbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(randomizedNumbers);
        solution(board, randomizedNumbers);
    }

    private boolean solution(SudokuBoard board, List<Integer> randomizedNumbers) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getNumberFromPosition(row, col) == 0) {
                    for (int i = 0; i < SIZE; i++) {  //inserting numbers
                        // zadeklarowac tablice mieszac i wpisywac za generowanie
                        board.setNumber(row, col, randomizedNumbers.get(i));
                        if (board.getRow(row).verify() && board.getColumn(col).verify()
                                && board.getBox(row, col).verify()) {
                            if (solution(board, randomizedNumbers)) {
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