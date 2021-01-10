package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {

    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    @Test
    void filenameTest() {
        try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao("testFilename")) {
            jdbcDao.write(board);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void fTest() {
        try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao("testFilename")) {
            jdbcDao.write(board);
            assertEquals(board, jdbcDao.read());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
