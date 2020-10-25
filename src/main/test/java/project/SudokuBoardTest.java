package project;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class SudokuBoardTest {
    int[][] board =
            {{0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
       /*5*/{0,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}};
    int[][] wrongBoard =
            {{0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
    /*5*/   {0,1,1,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}};

    @Test
    void startTest() {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.start();
        int m=0;
        int[] one, two;
        one = new int[81];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                one[m]= sudoku.getCell(i,j);
                m++;
            }
        }

        sudoku.start();
        m=0;
        two = new int[81];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                two[m]= sudoku.getCell(i,j);
                m++;
            }
        }
        assertFalse(Arrays.equals(one, two));
    }

    @Test
    void getCellTest() {
        SudokuBoard pom = new SudokuBoard(board);
        assertEquals(pom.getCell(5,1),1);
    }

    @Test
    void fillBoardTest() {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        Set<Integer> set_rows = new HashSet<>();
        Set<Integer> set_cols = new HashSet<>();
        Set<Integer> set_boxes = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                set_rows.add(sudoku.getCell(i,j));
                set_cols.add(sudoku.getCell(j,i));
            }
            assertEquals(set_rows.size(),9);
            assertEquals(set_cols.size(),9);
            set_rows.clear();
            set_cols.clear();
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    set_boxes.add(sudoku.getCell(3 * (i / 3) + j, 3 * (i % 3) + k ));
                }
            }
        }
        assertEquals(set_boxes.size(),9);
        set_boxes.clear();
    }
}