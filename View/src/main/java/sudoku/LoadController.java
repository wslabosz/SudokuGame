package sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoException;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoadController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(SaveController.class);
    public ListView listSavesView;
    public Button loadButton;
    ResourceBundle resourceBundle;
    private ResourceBundle bundle = ResourceBundle.getBundle("exceptions");
    private static final String URL = "jdbc:postgresql://localhost:5432/Sudoku";
    private static final String DRIVER = "org.postgresql.Driver";
    private Statement JDBC_STATEMENT;
    private Connection connection;
    private List<String> nameList = new ArrayList<>();
    private boolean loaded = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;
        try {
            getFilenames();
        } catch (DaoException | SQLException e) {
            logger.error(bundle.getString("connection.error"), e);
        }
        nameList.removeIf(s -> s.contains("initial"));
        listSavesView.getItems().addAll(nameList);
        listSavesView.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                loadButton.fire();
            }
        });
    }

    private void getFilenames() throws DaoException, SQLException {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, "postgres", "postgres");
            logger.debug(bundle.getString("connection.positive"));
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(bundle.getString("connection.negative"), e);
            throw new DaoException("connection.negative", e);
        }
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            JDBC_STATEMENT = connection.createStatement();
            preparedStatement = connection.prepareStatement
                    ("SELECT BOARDS.BOARD_NAME from BOARDS");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                nameList.add(resultSet.getString(1));
            }
            logger.debug(bundle.getString("data.read"));
        } catch (SQLException e) {
            logger.error(bundle.getString("io.error"));
        }
        connection.close();
        JDBC_STATEMENT.close();
    }
    @FXML
    public void load(ActionEvent actionEvent) {
        loaded = true;
        listSavesView.getScene().getWindow().hide();
    }

    public String getFilename() {
        if (!listSavesView.getSelectionModel().getSelectedIndices().isEmpty()) {
            return String.valueOf(listSavesView.getSelectionModel().getSelectedItem());
        }
        logger.error(resourceBundle.getString("loadFailed"));
        return null;
    }
}
