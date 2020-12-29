package sudoku;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class SudokuBoardWindowControl implements Initializable {
    @FXML
    private Button guzior;
    @FXML
    private GridPane sudokuBoardGrid;
    @FXML
    private AnchorPane anchorPane;

    private static final String REGEX_VALID_NUMBER = "[1-9]?";
    private final SudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoard board;
    private SudokuBoard initialState;
    private ResourceBundle resourceBundle;

    public SudokuBoardWindowControl() { }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        resourceBundle = bundle;
        if(board != null) {
            fillGrid();
        }
    }

    public void initData(Difficulty diff) throws IOException, ClassNotFoundException {
        board = new SudokuBoard(solver, diff);
        board.solveGame();
        diff.eraseFields(board);
        initialState = board.deepClone();
        fillGrid();
    }

    private void fillGrid() {
        StringConverter<Number> converter = new NumberStringConverter();
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                TextField textField = new TextField();
                textField.setAlignment(Pos.CENTER);
                textField.setMinSize(50, 58);
                textField.setFont(Font.font(20));
                textField.setOpacity(1);
                sudokuBoardGrid.add(textField, j, i);
                try {
                    JavaBeanIntegerPropertyBuilder builder = JavaBeanIntegerPropertyBuilder.create();
                    JavaBeanIntegerProperty integerProperty = builder.bean(board.getSudokuField(i, j)).name("number").build();
                    textField.textProperty().bindBidirectional(integerProperty, converter);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                if (board.getNumberFromPosition(i, j) != 0) {
                    textField.setDisable(true);
                } else {
                    textField.textProperty().set("");
                }
                textField.setTextFormatter(new TextFormatter<>(this::filter));
            }
        }
    }

    public void resetGame(ActionEvent actionEvent) {
        for (Node node : sudokuBoardGrid.getChildren().subList(1, 82)) {
            if(initialState.getNumberFromPosition(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)) == 0) {
                ((TextField) node).setText("");
            }
        }
    }

    private void clearGrid() {
        for (Node c : sudokuBoardGrid.getChildren().subList(1, 82)) {
            TextField field = (TextField) c;
            field.setDisable(false);
            if (board.getNumberFromPosition(GridPane.getRowIndex(c), GridPane.getColumnIndex(c)) != 0) {
                field.setText(String.valueOf(board.getNumberFromPosition(GridPane.getRowIndex(c), GridPane.getColumnIndex(c))));
            } else {
                field.setText("");
            }
            if (board.getNumberFromPosition(GridPane.getRowIndex(c), GridPane.getColumnIndex(c)) ==
                    initialState.getNumberFromPosition(GridPane.getRowIndex(c), GridPane.getColumnIndex(c)) &&
                    board.getNumberFromPosition(GridPane.getRowIndex(c), GridPane.getColumnIndex(c)) != 0) {
                field.setDisable(true);
            }
        }
    }

    public void saveSudokuToFile(ActionEvent actionEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz plik");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Sudoku", "*.sud")
        );
        File selectedFile = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
        if (selectedFile != null) {
            try (Dao<SudokuBoard> fileDao = SudokuBoardDaoFactory.getFileDao(selectedFile.getAbsolutePath());
                    FileOutputStream fos = new FileOutputStream(selectedFile, true);
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                fileDao.write(board);
                oos.writeObject(initialState);
                System.out.println(initialState.toString());
            }
        }
    }

    public void readSudokuFromFile(ActionEvent actionEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj plik");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Sudoku", "*.sud")
        );
        File selectedFile = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        if (selectedFile != null) {
            try (Dao<SudokuBoard> fileDao = SudokuBoardDaoFactory.getFileDao(selectedFile.getAbsolutePath());
                 FileInputStream fis = new FileInputStream(selectedFile);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                board = (SudokuBoard) ois.readObject();
                initialState = (SudokuBoard) ois.readObject();
                System.out.println(initialState.toString());
            }
        }
        clearGrid();
    }

    private TextFormatter.Change filter(TextFormatter.Change change) {
        if (!change.getControlNewText().matches(REGEX_VALID_NUMBER)) {
            change.setText("");
        }
        return change;
    }

}
