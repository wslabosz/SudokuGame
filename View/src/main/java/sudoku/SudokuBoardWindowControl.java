package sudoku;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.ApplicationException;
import sudoku.exceptions.DatabaseException;
import sudoku.exceptions.OperationOnFileException;

import java.io.*;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class SudokuBoardWindowControl implements Initializable {
    @FXML
    private Button resetButton;
    @FXML
    private GridPane sudokuBoardGrid;
    @FXML
    private AnchorPane anchorPane;

    private static final Logger logger = LoggerFactory.getLogger(SudokuBoardWindowControl.class);
    private static final String REGEX_VALID_NUMBER = "[1-9]?";
    private final SudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoard board;
    private SudokuBoard initialState;
    private ResourceBundle resourceBundle;
    private final List<JavaBeanIntegerProperty> integerProperties =
            new ArrayList<>();

    public SudokuBoardWindowControl() { }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        resourceBundle = bundle;
    }

    public void initData(Difficulty diff) throws ApplicationException, ClassNotFoundException {
        board = new SudokuBoard(solver, diff);
        board.solveGame();
        diff.eraseFields(board);
        initialState = board.deepClone();
        fillGrid();
    }

    private void fillGrid() {
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                TextField textField = new TextField();
                textField.setAlignment(Pos.CENTER);
                textField.setMinSize(50, 58);
                textField.setFont(Font.font(20));
                textField.setOpacity(1);
                textField.setTextFormatter(new TextFormatter<>(this::filter));
                sudokuBoardGrid.add(textField, j, i);
                textField.setOnKeyPressed(e -> {
                    if (e.getText().matches("[1-9]")) {
                        textField.setText(e.getText());
                    }
                });
                if (board.getNumberFromPosition(i, j) == initialState.getNumberFromPosition(i, j) && board.getNumberFromPosition(i, j) != 0) {
                    textField.setDisable(true);
                }
            }
        }
        binding();
    }

    public void binding() {
        if (integerProperties.size() > 0) {
            integerProperties.clear();
        }
        StringConverter<Number> converter = new NumberStringConverter() {
            @Override
            public String toString(Number var1) {
                if (var1 == null) {
                    return "";
                } else if (var1.equals(0)) {
                    return "";
                } else {
                    NumberFormat var2 = this.getNumberFormat();
                    return var2.format(var1);
                }
            }
            @Override
            public Number fromString(String var1) {
                try {
                    if (var1 == null) {
                        return null;
                    } else {
                        var1 = var1.trim();
                        if (var1.length() < 1) {
                            return 0;
                        } else {
                            NumberFormat var2 = this.getNumberFormat();
                            return var2.parse(var1);
                        }
                    }
                } catch (ParseException var3) {
                    throw new RuntimeException(var3);
                }
            }
        };
        JavaBeanIntegerPropertyBuilder builder = JavaBeanIntegerPropertyBuilder.create();
        JavaBeanIntegerProperty integerProperty = null;
        for (Node node : sudokuBoardGrid.getChildren().subList(1, 82)) {
            TextField field = (TextField) node;
            field.setDisable(false);
            try {
                integerProperty = builder.bean(board.getSudokuField(GridPane.getRowIndex(field), GridPane.getColumnIndex(field))).name("value").build();
                field.textProperty().bindBidirectional(integerProperty, converter);
            } catch (NoSuchMethodException e) {
                logger.debug(e.getLocalizedMessage(), e);
            }
            if (board.getNumberFromPosition(GridPane.getRowIndex(field), GridPane.getColumnIndex(field))
                    == initialState.getNumberFromPosition(GridPane.getRowIndex(field), GridPane.getColumnIndex(field)) &&
                    board.getNumberFromPosition(GridPane.getRowIndex(field), GridPane.getColumnIndex(field)) != 0) {
                field.setDisable(true);
            }
            integerProperties.add(integerProperty);
        }
    }

    public void resetGame(ActionEvent actionEvent) {
        for (Node node : sudokuBoardGrid.getChildren().subList(1, 82)) {
            if(initialState.getNumberFromPosition(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)) == 0) {
                ((TextField) node).setText("");
            }
        }
    }

    public void saveSudokuToFile(ActionEvent actionEvent) throws OperationOnFileException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz plik");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Sudoku", "*.sud")
        );
        File selectedFile = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
        var sudokuBoardList = new SudokuBoard[2];
        sudokuBoardList[0] = board;
        sudokuBoardList[1] = initialState;
        if (selectedFile != null) {
            try {
                try (Dao<SudokuBoard[]> fileDao = SudokuBoardDaoFactory.getFileTwoDao(selectedFile.getAbsolutePath())) {
                    fileDao.write(sudokuBoardList);
                }
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
                throw new OperationOnFileException(e);
            }
        }
    }

    public void readSudokuFromFile(ActionEvent actionEvent) throws OperationOnFileException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj plik");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Sudoku", "*.sud")
        );
        File selectedFile = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        if (selectedFile != null) {
            try (Dao<SudokuBoard[]> fileDao = SudokuBoardDaoFactory.getFileTwoDao(selectedFile.getAbsolutePath())) {
                var sudokuBoardList = fileDao.read();
                board = sudokuBoardList[0];
                initialState = sudokuBoardList[1];
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
                throw new OperationOnFileException(e);
            }
        }
        binding();
    }

    private TextFormatter.Change filter(TextFormatter.Change change) {
        if (!change.getControlNewText().matches(REGEX_VALID_NUMBER)) {
            change.setText("");
        }
        return change;
    }

    public void saveGame(ActionEvent actionEvent) throws DatabaseException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("input.fxml"), resourceBundle);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }
        Stage inputStage = new Stage();
        inputStage.setTitle(resourceBundle.getString("saveButton"));
        inputStage.initOwner(anchorPane.getScene().getWindow());
        inputStage.setScene(scene);
        inputStage.showAndWait();
        String filename = loader.<SaveController>getController().getInput();
        if (filename != null) {
            try {
                try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao(filename);
                     Dao<SudokuBoard> jdbcDaoInitial = SudokuBoardDaoFactory.getJdbcDao("initial" + filename)) {
                    jdbcDao.write(board);
                    jdbcDaoInitial.write(initialState);
                }
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
                throw new DatabaseException(e.getLocalizedMessage());
            }
            logger.debug(resourceBundle.getString("saveCompleted"));
        }
    }

    public void loadGame(ActionEvent actionEvent) throws DatabaseException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("savesList.fxml"), resourceBundle);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }
        Stage readStage = new Stage();
        readStage.setTitle(resourceBundle.getString("loadButton"));
        readStage.initOwner(anchorPane.getScene().getWindow());
        readStage.setScene(scene);
        readStage.showAndWait();
        String filename = loader.<LoadController>getController().getFilename();
        if (filename != null) {
            try {
                try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao(filename);
                     Dao<SudokuBoard> jdbcDaoInitial = SudokuBoardDaoFactory.getJdbcDao("initial" + filename)) {
                    board = jdbcDao.read();
                    initialState = jdbcDaoInitial.read();
                }
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
                throw new DatabaseException(e.getLocalizedMessage());
            }
            binding();
        }
    }
}
