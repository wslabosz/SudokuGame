package project;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static project.SudokuBoard.SIZE;

class SudokuBoardTest {

    @Test
    void fillBoard() {
        SudokuBoard sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        sudoku.solveGame();
        Set<Integer> set_rows = new HashSet<>();
        Set<Integer> set_cols = new HashSet<>();
        Set<Integer> set_boxes = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                set_rows.add(sudoku.getNumberFromPosition(i,j));
                set_cols.add(sudoku.getNumberFromPosition(j,i));
            }
            assertEquals(set_rows.size(),9);
            assertEquals(set_cols.size(),9);
            set_rows.clear();
            set_cols.clear();
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                   set_boxes.add(sudoku.getNumberFromPosition(3 * (i / 3) + j, 3 * (i % 3) + k ));
                }
            }
        }
        assertEquals(set_boxes.size(),9);
        set_boxes.clear();
    }

    @Test
    void randomizedBoardCheck() {
        SudokuBoard sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudoku2 = new SudokuBoard(new BacktrackingSudokuSolver());
        sudoku.solveGame();
        sudoku2.solveGame();

        int[] su1 = new int [81];
        int[] su2 = new int [81];
        int m = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                su1[m] = sudoku.getNumberFromPosition(i, j);
                su2[m] = sudoku2.getNumberFromPosition(i, j);
                m++;
            }
        }
        assertFalse(Arrays.equals(su1, su2));
    }
    @Test
    void SudokuCheck() {
        SudokuBoard sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        sudoku.solveGame();
        System.out.println(sudoku.toString());
    }

    @Test
    void setNumber() {
        SudokuBoard s = new SudokuBoard(new BacktrackingSudokuSolver());

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(s.getNumberFromPosition(i,j), 0);
            }
        }

        s.setNumber(3,3,9);
        assertEquals(s.getNumberFromPosition(3,3), 9);

        for (int i = 0; i < 9; i++) {
            if (i == 3 ) { continue; }
            for (int j = 0; j < 9; j++) {
                if (j == 3) { continue; }
                assertEquals(s.getNumberFromPosition(i,j), 0);
            }
        }
    }
}