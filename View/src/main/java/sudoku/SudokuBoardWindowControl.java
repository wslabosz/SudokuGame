package sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


public class SudokuBoardWindowControl {
    @FXML
    private GridPane sudokuBoardGrid;

    private SudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoard board = new SudokuBoard(solver);
    private SudokuBoard boardCopy = new SudokuBoard(solver);
    private Difficulty difficulty = new Difficulty();

    @FXML
    private void initialize() throws CloneNotSupportedException {
        board.solveGame();
        boardCopy = (SudokuBoard) board.clone();
        difficulty.difficultyChooser(board, ChoiceWindowControl.getDiff());
        fillGrid();
    }


    private void fillGrid() {
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                TextField textField = new TextField();
                textField.setMinSize(50, 50);
                textField.setFont(Font.font(16));
                if (board.getNumberFromPosition(i, j) != 0) {
                    textField.setDisable(true);
                    textField.setText(String.valueOf(board.getNumberFromPosition(i, j)));
                }
                sudokuBoardGrid.add(textField, i, j);
            }
        }
    }
}
