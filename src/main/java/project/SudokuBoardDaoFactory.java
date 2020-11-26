package project;

public class SudokuBoardDaoFactory {
    public static FileSudokuBoardDao getFileDao(String filename) {
        return new FileSudokuBoardDao(filename);
    }
}
