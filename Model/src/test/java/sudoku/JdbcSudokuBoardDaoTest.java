package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {
    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    SudokuBoard boardtwo;

    @Test
    void writeAndReadTest() {
        try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao("testFilename")) {
            board.solveGame();
            jdbcDao.write(board);
            boardtwo = jdbcDao.read();
            assertEquals(board, boardtwo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void filenameTest() {
        try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao("testFilename")) {
            assertEquals(((JdbcSudokuBoardDao) jdbcDao).getFileName(), "testFilename");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
