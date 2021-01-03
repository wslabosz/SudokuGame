package sudoku;

import org.junit.jupiter.api.*;
import sudoku.exceptions.ApplicationExpection;
import sudoku.exceptions.WrongFieldValueSudokuException;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;
import static sudoku.SudokuBoard.SIZE;

class SudokuBoardTest {

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
        SudokuBoard sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
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
        sudoku.setListening(true);
        try {
            sudoku.solveGame();
        } catch (WrongFieldValueSudokuException ex) {
            assertEquals(ex.getMessage(), "Inserted invalid value");
        }
        assertNotNull(sudoku.toString());
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
        } catch (WrongFieldValueSudokuException ex) {
            assertEquals(ex.getMessage(), "Number must be in range from 0 to 9");
        }
        try {
            board.setNumber(0,0, -159);
        } catch (WrongFieldValueSudokuException ex) {
            assertEquals(ex.getMessage(), "Number must be in range from 0 to 9");
        }
        try {
            board.setNumber(100, 100, 5);
        } catch (WrongFieldValueSudokuException ex) {
            assertEquals(ex.getMessage(), "Index out of bounds");
        }
    }

    @Test
    void testEquals() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        SudokuBoard board2 = new SudokuBoard(solver);
        SudokuBoard board3 = new SudokuBoard(solver);
        board3.setNumber(0,0,1);
        assertNotEquals(board1, null);
        assertNotEquals(board1, solver);
        assertEquals(board1,board1);
        assertEquals(board1,board2);
        assertNotEquals(board1,board3);
    }

    @Test
    void testToString() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        String toString = board.toString();
        System.out.println(toString);
        assertTrue(toString.matches("SudokuBoard\\{board=\\[[a-zA-Z\\{a-z=0-9\\}\\,\\s]*?\\]\\}"));
    }

    @Test
    void testHashCode() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        SudokuBoard board2 = new SudokuBoard(solver);
        SudokuBoard board3 = new SudokuBoard(solver);
        board3.setNumber(0,0,1);
        assertEquals(board1.hashCode(),board1.hashCode());
        assertEquals(board1.hashCode(),board2.hashCode());
        assertNotEquals(board1.hashCode(),board3.hashCode());
    }

    @Test
    void getRow() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        assertNotNull(board.getRow(1));
    }

    @Test
    void getColumn() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        assertNotNull(board.getColumn(7));
    }

    @Test
    void getBox() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        assertNotNull(board.getBox(5,5));
    }

    @Test
    void testClone() throws ApplicationExpection, ClassNotFoundException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoard clone = board.deepClone();
        assertNotSame(board, clone);
        assertEquals(board.getClass(), clone.getClass());
        assertEquals(clone, board);
        board.setNumber(0,0,0);
        assertNotEquals(clone, board);
    }

    @Test
    void BoardDifficultyTest() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver(), Difficulty.Easy);
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver(), Difficulty.Normal);
        SudokuBoard sudokuBoard3 = new SudokuBoard(new BacktrackingSudokuSolver(), Difficulty.Hard);
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
    void getSudokuField() throws ApplicationExpection, ClassNotFoundException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoard clone = board.deepClone();
        assertEquals(board.getSudokuField(0, 0), clone.getSudokuField(0, 0));
    }
}