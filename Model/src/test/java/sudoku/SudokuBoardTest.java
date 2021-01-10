package sudoku;

import org.junit.jupiter.api.Test;
import sudoku.exceptions.ApplicationException;
import sudoku.exceptions.WrongFieldValueSudokuException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static sudoku.SudokuBoard.SIZE;

class SudokuBoardTest {
    SudokuBoard sudoku = new SudokuBoard(new BacktrackingSudokuSolver(), "testBoard");
    SudokuBoard sudoku2 = new SudokuBoard(new BacktrackingSudokuSolver(), "testBoard2");
    SudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard sudoku3 = new SudokuBoard(solver, "testBoard3");

    private int checkZeroValues(SudokuBoard board) {
        int counter = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getNumberFromPosition(i, j) == 0) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Test
    void checkingSudokuRegularity() {
        sudoku.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertTrue(sudoku.getColumn(j).verify());
            }
            assertTrue(sudoku.getRow(i).verify());
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                assertTrue(sudoku.getBox(i, j).verify());
            }
        }
    }

    @Test
    void randomizedBoardCheck() {
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
        //sudoku.setListening(true);
        try {
            sudoku.solveGame();
        } catch (WrongFieldValueSudokuException ex) {
            assertEquals(ex.getMessage(), "Inserted invalid value");
        }
        assertNotNull(sudoku.toString());
    }

    @Test
    void setNumber() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(sudoku.getNumberFromPosition(i,j), 0);
            }
        }

        sudoku.setNumber(3,3,9);
        assertEquals(sudoku.getNumberFromPosition(3,3), 9);

        for (int i = 0; i < 9; i++) {
            if (i == 3 ) { continue; }
            for (int j = 0; j < 9; j++) {
                if (j == 3) { continue; }
                assertEquals(sudoku.getNumberFromPosition(i,j), 0);
            }
        }
    }

    @Test
    void setNumberExceptions() {
        try {
            sudoku.setNumber(0, 0, 159);
        } catch (WrongFieldValueSudokuException ex) {
            assertEquals(ex.getMessage(), "Number must be in range from 0 to 9");
        }
        try {
            sudoku.setNumber(0,0, -159);
        } catch (WrongFieldValueSudokuException ex) {
            assertEquals(ex.getMessage(), "Number must be in range from 0 to 9");
        }
        try {
            sudoku.setNumber(100, 100, 5);
        } catch (WrongFieldValueSudokuException ex) {
            assertEquals(ex.getMessage(), "Index out of bounds");
        }
    }

    @Test
    void testEquals() {
        sudoku3.setNumber(0,0,1);
        assertNotEquals(sudoku, null);
        assertNotEquals(sudoku, solver);
        assertEquals(sudoku,sudoku);
        assertEquals(sudoku,sudoku2);
        assertNotEquals(sudoku,sudoku3);
    }

    @Test
    void testToString() {
        String toString = sudoku.toString();
        System.out.println(toString);
        assertTrue(toString.matches("SudokuBoard\\{board=\\[[a-zA-Z\\{a-z=0-9\\}\\,\\s]*?\\]\\}"));
    }

    @Test
    void testHashCode() {
        sudoku3.setNumber(0,0,1);
        assertEquals(sudoku.hashCode(),sudoku.hashCode());
        assertEquals(sudoku.hashCode(),sudoku2.hashCode());
        assertNotEquals(sudoku.hashCode(),sudoku3.hashCode());
    }

    @Test
    void getRow() {
        assertNotNull(sudoku.getRow(1));
    }

    @Test
    void getColumn() {
        assertNotNull(sudoku.getColumn(7));
    }

    @Test
    void getBox() {
        assertNotNull(sudoku.getBox(5,5));
    }

    @Test
    void testClone() throws ApplicationException, ClassNotFoundException {
        sudoku.solveGame();
        SudokuBoard clone = sudoku.deepClone();
        assertNotSame(sudoku, clone);
        assertEquals(sudoku.getClass(), clone.getClass());
        assertEquals(clone, sudoku);
        sudoku.setNumber(0,0,0);
        assertNotEquals(clone, sudoku);
    }

    @Test
    void BoardDifficultyTest() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver(), Difficulty.Easy, "1");
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver(), Difficulty.Normal, "2");
        SudokuBoard sudokuBoard3 = new SudokuBoard(new BacktrackingSudokuSolver(), Difficulty.Hard, "3");
        sudokuBoard1.solveGame();
        Difficulty easy = Difficulty.Easy;
        easy.eraseFields(sudokuBoard1);
        assertEquals(44, checkZeroValues(sudokuBoard1));
        sudokuBoard2.solveGame();
        Difficulty normal = Difficulty.Normal;
        normal.eraseFields(sudokuBoard2);
        assertEquals(51, checkZeroValues(sudokuBoard2));
        sudokuBoard3.solveGame();
        Difficulty hard = Difficulty.Hard;
        hard.eraseFields(sudokuBoard3);
        assertEquals(58, checkZeroValues(sudokuBoard3));
        assertEquals(sudokuBoard1.getDifficulty(), easy);
    }

    @Test
    void getSudokuField() throws ApplicationException, ClassNotFoundException {
        sudoku.solveGame();
        SudokuBoard clone = sudoku.deepClone();
        assertEquals(sudoku.getSudokuField(0, 0), clone.getSudokuField(0, 0));
    }
}