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
        if (fileName.length() > 1) {
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
        } else {
            logger.warn(bundle.getString("filename.err"));
            throw new DaoException(bundle.getString("filename.err"));
        }
    }

    private void setupTables() {
        try {
            jdbcStatement = connection.createStatement();
            String createB =
                    "Create Table IF NOT EXISTS BOARDS(BOARD_ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                            + "BOARD_NAME varchar(40) UNIQUE NOT NULL)";
            jdbcStatement.executeUpdate(createB);
            logger.debug(bundle.getString("tab.creation"));
            jdbcStatement = connection.createStatement();
            String createF =
                    "Create Table IF NOT EXISTS FIELDS(X int NOT NULL, Y int NOT NULL, VAL int NOT NULL,"
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
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        int x;
        int y;
        int value;
        int id;
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(
                "SELECT BOARDS.BOARD_ID from BOARDS where BOARDS.BOARD_NAME=?");
             PreparedStatement preparedStatement2 = connection.prepareStatement(
                     "SELECT FIELDS.X, FIELDS.Y, FIELDS.VAL from FIELDS where FIELDS.BOARD_ID=?")) {
            jdbcStatement = connection.createStatement();
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "SELECT BOARDS.BOARD_ID from BOARDS where BOARDS.BOARD_NAME=?");
            preparedStatement1.setString(1, fileName);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            } else {
                logger.error(bundle.getString("io.error"));
                throw new IOException(bundle.getString("io.error"));
            }
            logger.debug(bundle.getString("data.read"));
//            preparedStatement = connection.prepareStatement(
//                    "SELECT FIELDS.X, FIELDS.Y, FIELDS.VAL from FIELDS where FIELDS.BOARD_ID=?");
            preparedStatement2.setInt(1, id);
            ResultSet resultSetVal = preparedStatement2.executeQuery();
            while (resultSetVal.next()) {
                x = resultSetVal.getInt(1);
                y = resultSetVal.getInt(2);
                value = resultSetVal.getInt(3);
                sudokuBoard.setNumber(x, y, value);
            }
            // zamykac resultSety
            logger.debug(bundle.getString("data.read"));
            resultSetVal.close();
            resultSet.close();
            jdbcStatement.close();
            connection.close();
        } catch (SQLException | IOException e) {
            logger.error(bundle.getString("io.error"));
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) {
        int id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO BOARDS (BOARD_NAME) VALUES (?)")) {
            jdbcStatement = connection.createStatement();
            preparedStatement.setString(1, fileName);
            preparedStatement.executeUpdate();
            logger.debug(bundle.getString("data.insertion"));
        } catch (SQLException e) {
            logger.error(bundle.getString("io.error"), e);
        }
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(
                "SELECT BOARDS.BOARD_ID from BOARDS where BOARDS.BOARD_NAME=?");
            PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "INSERT INTO FIELDS VALUES(?, ?, ?, ?)")) {
//            preparedStatement = connection.prepareStatement(
//                    "SELECT BOARDS.BOARD_ID from BOARDS where BOARDS.BOARD_NAME=?");
            preparedStatement1.setString(1, fileName);
            ResultSet idSet = preparedStatement1.executeQuery();
            if (idSet.next()) {
                id = idSet.getInt(1);
            } else {
                logger.error(bundle.getString("io.error"));
                throw new IOException(bundle.getString("io.error"));
            }
            jdbcStatement = connection.createStatement();
            preparedStatement2.setInt(4, id);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    preparedStatement2.setInt(1, i);
                    preparedStatement2.setInt(2, j);
                    preparedStatement2.setInt(3, obj.getNumberFromPosition(i, j));
                    preparedStatement2.executeUpdate();
                }
            }
            logger.debug(bundle.getString("data.insertion"));
            preparedStatement1.close();
            preparedStatement2.close();
            jdbcStatement.close();
            connection.close();
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
