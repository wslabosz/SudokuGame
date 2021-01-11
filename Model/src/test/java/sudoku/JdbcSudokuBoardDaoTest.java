package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcSudokuBoardDaoTest {
    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    SudokuBoard boardtwo;

    @Test
    void writeAndReadTestPositive() throws Exception {
        try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao("testFilename")) {
            board.solveGame();
            jdbcDao.write(board);
            boardtwo = jdbcDao.read();
            assertEquals(board, boardtwo);
        }
    }

    @Test
    public void filenameTest() throws Exception {
        try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao("testFilename")) {
            assertEquals(((JdbcSudokuBoardDao) jdbcDao).getFileName(), "testFilename");
        }
    }
}
