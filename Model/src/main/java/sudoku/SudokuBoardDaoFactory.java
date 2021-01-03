package sudoku;

import sudoku.exceptions.ApplicationException;

public class SudokuBoardDaoFactory {
    private SudokuBoardDaoFactory() {
    }

    public static Dao<SudokuBoard> getFileDao(String filename) throws ApplicationException {
        return new FileSudokuBoardDao(filename);
    }
}
