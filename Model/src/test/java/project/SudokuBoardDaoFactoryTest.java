package project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {
    @Test
    void daoFactoryTest() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        FileSudokuBoardDao test = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("testFile");
        assertNotNull(test);
    }

}