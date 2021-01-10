package sudoku;

import sudoku.exceptions.ApplicationException;

public class SudokuBoardDaoFactory {
    private SudokuBoardDaoFactory() {
    }

    public static Dao<SudokuBoard> getFileDao(String filename) throws ApplicationException {
        return new FileSudokuBoardDao(filename);
    }

    public static Dao<SudokuBoard[]> getFileTwoDao(String filename) {
        return new FileTwoSudokuBoardDao(filename);
    }

    public static Dao<SudokuBoard> getDBDao(String boardname) throws ApplicationException {
        return new JdbcSudokuBoardDao(boardname);
    }
}
