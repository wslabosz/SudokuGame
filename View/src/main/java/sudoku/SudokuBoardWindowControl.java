package sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import java.util.ResourceBundle;


public class SudokuBoardWindowControl {

    public SudokuBoardWindowControl() {
    }

    @FXML
    private GridPane sudokuBoardGrid;

    private SudokuSolver solver = new BacktrackingSudokuSolver();
    private Difficulty diff;
    private SudokuBoard board;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("sudoku/Language");

    @FXML
    private void initialize() {
    }

    public void initData(Difficulty diff) throws CloneNotSupportedException {
        this.diff = diff;
        board = new SudokuBoard(solver, diff);
        board.solveGame();
        SudokuBoard boardCopy = board.clone();
        diff.eraseFields(board);
        fillGrid();
    }

    private void fillGrid() {
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                TextField textField = new TextField();
                textField.setMinSize(30, 30);
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
