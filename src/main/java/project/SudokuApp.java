package project;

public class SudokuApp {

    public static void main(final String[] args) {
        SudokuBoard sudoku = new SudokuBoard();
        sudoku.generateNumbersOnBoard();
        System.out.println(sudoku.toString());
        sudoku.fillBoard();
        System.out.println(sudoku.toString());
    }
}