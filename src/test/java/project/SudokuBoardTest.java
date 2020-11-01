package project;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;
import static project.SudokuBoard.SIZE;

class SudokuBoardTest {

    @Test
    void checkingSudokuRegularity() {
        SudokuBoard sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        sudoku.solveGame();
        assertTrue(sudoku.checkRowRegularity());
        assertTrue(sudoku.checkColRegularity());
        assertTrue(sudoku.checkBoxRegularity());

        SudokuBoard sudoku2 = new SudokuBoard(new BacktrackingSudokuSolver());
        sudoku2.setNumber(5,5,1);
        sudoku2.setNumber(6,5,1);
        assertFalse(sudoku2.checkRowRegularity());

        SudokuBoard sudoku3 = new SudokuBoard(new BacktrackingSudokuSolver());
        sudoku3.setNumber(0,5,1);
        sudoku3.setNumber(0,6,1);
        assertFalse(sudoku3.checkColRegularity());

        SudokuBoard sudoku4 = new SudokuBoard(new BacktrackingSudokuSolver());
        sudoku3.setNumber(0,0,1);
        sudoku3.setNumber(2,2,1);
        assertFalse(sudoku3.checkBoxRegularity());
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

    @Test
    void setNumberExceptions(){
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            board.setNumber(0, 0, 159);
        } catch (InputMismatchException ex) {
            assertEquals(ex.getMessage(), "Number must be in range from 0 to 9");
        }
        try {
            board.setNumber(0,0, -159);
        } catch (InputMismatchException ex) {
            assertEquals(ex.getMessage(), "Number must be in range from 0 to 9");
        }
        try {
            board.setNumber(100, 100, 5);
        } catch (IndexOutOfBoundsException ex) {
            assertEquals(ex.getMessage(), "Index out of bounds");
        }
    }
}