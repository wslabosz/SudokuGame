package project;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    FileSudokuBoardDao fileDao = new FileSudokuBoardDao("testFilename");
    FileSudokuBoardDao invalidFileDao = new FileSudokuBoardDao("");
    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    SudokuBoard invalidBoard = null;

    @Test
    void readAndWriteTest() {
        assertThrows(OperationOnFileException.class, () -> invalidFileDao.read());
        fileDao.write(board);
        assertEquals(board, fileDao.read());
        assertThrows(OperationOnFileException.class, () -> invalidFileDao.write(invalidBoard));
    }
}