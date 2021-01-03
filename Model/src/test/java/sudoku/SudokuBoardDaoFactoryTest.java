package sudoku;

import org.junit.jupiter.api.Test;
import sudoku.exceptions.ApplicationException;
import sudoku.exceptions.DaoException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {
    @Test
    void daoFactoryTest() throws ApplicationException {
        FileSudokuBoardDao test = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("testFile");
        assertNotNull(test);
    }

}