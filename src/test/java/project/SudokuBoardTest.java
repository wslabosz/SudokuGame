package project;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    @Test
    void fillBoard() {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        Set<Integer> set_rows = new HashSet<>();
        Set<Integer> set_cols = new HashSet<>();
        Set<Integer> set_boxes = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                set_rows.add(sudoku.getNumberFromPosition(i,j));
                set_cols.add(sudoku.getNumberFromPosition(j,i));
                set_boxes.add(sudoku.getNumberFromPosition(j / 3, j % 3));
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
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        String first = sudoku.toString();
        sudoku.fillBoard();
        String second = sudoku.toString();

        assertNotEquals(first,second);
    }
}