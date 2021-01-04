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

    @Test
    void writeTest() throws DaoException {
        try (Dao<SudokuBoard> fileDao = SudokuBoardDaoFactory.getFileDao("testFilename");
             Dao<SudokuBoard> invalidFileDao = SudokuBoardDaoFactory.getFileDao("")) {
            fileDao.write(board);
            assertTrue(new File("testFilename").length() != 0);
            assertThrows(OperationOnFileException.class, () -> invalidFileDao.write(invalidBoard));
        } catch (Exception e) {
            throw new DaoException(e.getLocalizedMessage(), e);
        }
    }

    @Test
    void readTest() throws DaoException {
        try (Dao<SudokuBoard> fileDao = SudokuBoardDaoFactory.getFileDao("testFilename");
             Dao<SudokuBoard> invalidFileDao = SudokuBoardDaoFactory.getFileDao("")) {
            assertEquals(fileDao.read(), board);
            assertThrows(OperationOnFileException.class, invalidFileDao::read);
        } catch (Exception e) {
            throw new DaoException(e.getLocalizedMessage(), e);
        }
    }
}