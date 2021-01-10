package sudoku;

import org.junit.jupiter.api.Test;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.OperationOnFileException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {
    // to w try-with-resources DAO MA BYC ZASOBEM TWORZYMY GO WEWNATRZ TRY

    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver(), "correctBoard");
    private SudokuBoard invalidBoard = new SudokuBoard(new BacktrackingSudokuSolver(), "invalidBoard");
    private FileSudokuBoardDao fileSudokuBoardDao;

    @Test
    void filenameTest() throws DaoException {
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
}