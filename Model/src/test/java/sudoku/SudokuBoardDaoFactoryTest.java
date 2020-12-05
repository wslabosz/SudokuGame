package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {
    @Test
    void daoFactoryTest() {
        FileSudokuBoardDao test = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("testFile");
        assertNotNull(test);
    }

}