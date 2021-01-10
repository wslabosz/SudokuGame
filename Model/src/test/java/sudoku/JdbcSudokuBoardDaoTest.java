package sudoku;

import org.junit.jupiter.api.Test;
import sudoku.exceptions.ApplicationException;

public class JdbcSudokuBoardDaoTest {

    @Test
    void filenameTest() {
        try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao("testFilename")) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
