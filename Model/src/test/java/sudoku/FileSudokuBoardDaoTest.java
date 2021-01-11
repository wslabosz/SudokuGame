package sudoku;

import org.junit.jupiter.api.Test;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.OperationOnFileException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {
    // to w try-with-resources DAO MA BYC ZASOBEM TWORZYMY GO WEWNATRZ TRY

    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoard invalidBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    FileSudokuBoardDao fileSudokuBoardDao;

    @Test
    void filenameTest() {
        assertThrows(DaoException.class, () -> fileSudokuBoardDao = new FileSudokuBoardDao(null));
    }

    @Test
    void writeTest() throws Exception {
        try (Dao<SudokuBoard> fileDao = SudokuBoardDaoFactory.getFileDao("testFilename");
             Dao<SudokuBoard> invalidFileDao = SudokuBoardDaoFactory.getFileDao("")) {
            fileDao.write(board);
            assertTrue(new File("testFilename").length() != 0);
            assertThrows(OperationOnFileException.class, () -> invalidFileDao.write(invalidBoard));
        }
    }

    @Test
    void readTest() throws Exception {
        try (Dao<SudokuBoard> fileDao = SudokuBoardDaoFactory.getFileDao("testFilename");
             Dao<SudokuBoard> invalidFileDao = SudokuBoardDaoFactory.getFileDao("")) {
            assertEquals(fileDao.read(), board);
            assertThrows(OperationOnFileException.class, invalidFileDao::read);
        }
    }

    @Test
    void writeTwoTest() throws Exception {
        var sudokuBoardList = new SudokuBoard[2];
        sudokuBoardList[0] = board;
        sudokuBoardList[1] = new SudokuBoard(new BacktrackingSudokuSolver());
        try (Dao<SudokuBoard[]> fileTwoDao = SudokuBoardDaoFactory.getFileTwoDao("testFilename");
             Dao<SudokuBoard[]> invalidFileDao = SudokuBoardDaoFactory.getFileTwoDao("")) {
            fileTwoDao.write(sudokuBoardList);
            assertEquals(fileTwoDao.read()[0].getNumberFromPosition(0,0), sudokuBoardList[0].getNumberFromPosition(0,0));
            assertEquals(fileTwoDao.read()[1].getNumberFromPosition(0,0), sudokuBoardList[1].getNumberFromPosition(0,0));
            assertThrows(OperationOnFileException.class, invalidFileDao::read);
        }
    }
}