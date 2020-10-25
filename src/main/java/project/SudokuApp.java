package project;

public class SudokuApp {

    public static void main(final String[] args) {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.fillBoard();
        sudoku.display();
    }
}