package sudoku;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {
    // to w try-with-resources DAO MA BYC ZASOBEM TWORZYMY GO WEWNATRZ TRY

    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    SudokuBoard invalidBoard = null;

    @Test
    void writeTest() {
        try(Dao<SudokuBoard> fileDao = SudokuBoardDaoFactory.getFileDao("testFilename");
            Dao<SudokuBoard> invalidFileDao = SudokuBoardDaoFactory.getFileDao("")){
            fileDao.write(board);
            assertTrue(new File("testFilename").length() != 0);
            assertThrows(OperationOnFileException.class, () -> invalidFileDao.write(invalidBoard));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void readTest() {
        try(Dao<SudokuBoard> fileDao = SudokuBoardDaoFactory.getFileDao("testFilename");
            Dao<SudokuBoard> invalidFileDao = SudokuBoardDaoFactory.getFileDao("")){
            assertEquals(fileDao.read(), board);
            assertThrows(OperationOnFileException.class, invalidFileDao::read);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}