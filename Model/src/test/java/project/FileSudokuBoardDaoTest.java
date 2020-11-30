package project;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {
    // to w try-with-resources DAO MA BYC ZASOBEM TWORZYMY GO WEWNATRZ TRY

    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    SudokuBoard invalidBoard = null;

    @Test
    void writeTest() {
        try(FileSudokuBoardDao fileDao = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("testFilename");
            FileSudokuBoardDao invalidFileDao = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("")){
            fileDao.write(board);
            assertTrue(new File("testFilename").length() != 0);
            assertThrows(OperationOnFileException.class, () -> invalidFileDao.write(invalidBoard));
        }
    }

    @Test
    void readTest() {
        try(FileSudokuBoardDao fileDao = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("testFilename");
            FileSudokuBoardDao invalidFileDao = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("")){
            assertEquals(fileDao.read(), board);
            assertThrows(OperationOnFileException.class, invalidFileDao::read);
        }
    }
}