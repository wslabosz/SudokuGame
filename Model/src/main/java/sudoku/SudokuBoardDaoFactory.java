package sudoku;

public class SudokuBoardDaoFactory {
    private SudokuBoardDaoFactory() {
    }

    public static Dao<SudokuBoard> getFileDao(String filename) {
        return new FileSudokuBoardDao(filename);
    }
}
