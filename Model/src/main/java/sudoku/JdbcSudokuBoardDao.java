package sudoku;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private final String fileName;
    private static final String URL = "jdbc:postgresql://localhost:5432/Sudoku";
    private static final String DRIVER = "org.postgresql.Driver";
    private Statement jdbcStatement;
    private final Connection connection;
    private final ResourceBundle bundle = ResourceBundle.getBundle("exceptions");
    private final Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);

    public String getFileName() {
        return fileName;
    }

    public JdbcSudokuBoardDao(String fileName) throws DaoException {
        this.fileName = fileName;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, "postgres", "postgres");
            logger.debug(bundle.getString("connection.positive"));
            setupTables();
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(bundle.getString("connection.negative"), e);
            throw new DaoException("connection.negative", e);
        }
    }

    private void setupTables() {
        try {
            jdbcStatement = connection.createStatement();
            String createB =
                    "Create Table BOARDS(BOARD_ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                            + "BOARD_NAME varchar(40) NOT NULL)";
            jdbcStatement.executeUpdate(createB);
            logger.debug(bundle.getString("tab.creation"));
            jdbcStatement = connection.createStatement();
            String createF =
                    "Create Table FIELDS(X int NOT NULL, Y int NOT NULL, VAL int NOT NULL,"
                            + " BOARD_ID int, FOREIGN KEY (BOARD_ID) "
                            + "REFERENCES BOARDS (BOARD_ID) ON UPDATE CASCADE ON DELETE CASCADE )";
            jdbcStatement.executeUpdate(createF);
            logger.debug(bundle.getString("tab.creation"));
        } catch (SQLException e) {
            logger.warn(bundle.getString("tab.error"));
        }
    }

    @Override
    public SudokuBoard read() {
        PreparedStatement preparedStatement;
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        ResultSet resultSet;
        ResultSet resultSetVal;
        int x;
        int y;
        int value;
        int id;
        try {
            jdbcStatement = connection.createStatement();
            preparedStatement = connection.prepareStatement(
                    "SELECT BOARDS.BOARD_ID from BOARDS where BOARDS.BOARD_NAME=?");
            preparedStatement.setString(1, fileName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            } else {
                logger.error(bundle.getString("io.error"));
                throw new IOException(bundle.getString("io.error"));
            }
            logger.debug(bundle.getString("data.read"));
            preparedStatement = connection.prepareStatement(
                    "SELECT FIELDS.X, FIELDS.Y, FIELDS.VAL from FIELDS where FIELDS.BOARD_ID=?");
            preparedStatement.setInt(1, id);
            resultSetVal = preparedStatement.executeQuery();
            while (resultSetVal.next()) {
                x = resultSetVal.getInt(1);
                y = resultSetVal.getInt(2);
                value = resultSetVal.getInt(3);
                sudokuBoard.setNumber(x, y, value);
            }
            logger.debug(bundle.getString("data.read"));
        } catch (SQLException | IOException e) {
            logger.error(bundle.getString("io.error"));
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) {
        ResultSet idSet;
        int id;
        PreparedStatement preparedStatement;
        try {
            jdbcStatement = connection.createStatement();
            String insert = "INSERT INTO BOARDS (BOARD_NAME) VALUES (?)";
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, fileName);
            preparedStatement.executeUpdate();
            logger.debug(bundle.getString("data.insertion"));
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(bundle.getString("io.error"), e);
        }
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT BOARDS.BOARD_ID from BOARDS where BOARDS.BOARD_NAME=?");
            preparedStatement.setString(1, fileName);
            idSet = preparedStatement.executeQuery();
            if (idSet.next()) {
                id = idSet.getInt(1);
            } else {
                logger.error(bundle.getString("io.error"));
                throw new IOException(bundle.getString("io.error"));
            }
            jdbcStatement = connection.createStatement();
            String insert = "INSERT INTO FIELDS VALUES(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(4, id);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    preparedStatement.setInt(1, i);
                    preparedStatement.setInt(2, j);
                    preparedStatement.setInt(3, obj.getNumberFromPosition(i, j));
                    preparedStatement.executeUpdate();
                }
            }
            logger.debug(bundle.getString("data.insertion"));
            preparedStatement.close();
        } catch (SQLException | IOException e) {
            logger.error(bundle.getString("io.error"), e);
        }
    }

    @Override
    public void close() {
        try {
            jdbcStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(bundle.getString("connection.error"));
        }
    }

}
