package sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoException;

import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    final static Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private String fileName;
    private static final String URL = "jdbc:postgresql://localhost:5432/Sudoku";
    private static final String DRIVER = "org.postgresql.Driver";
    private Statement JDBC_STATEMENT;
    private Connection connection;
    private ResourceBundle bundle = ResourceBundle.getBundle("exceptions");

    public String getFileName() {
        return fileName;
    }

    public JdbcSudokuBoardDao(String fileName) throws DaoException {
        this.fileName = fileName;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, "postgres", "postgres");
            logger.debug(bundle.getString("connection.positive"));
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(bundle.getString("connection.negative"), e);
            throw new DaoException("connection.negative", e);
        }
    }

    @Override
    public SudokuBoard read() {
        PreparedStatement preparedStatement;
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        String fields;
        ResultSet resultSet;
        ResultSet resultSetVal;
        int[] array = new int[81];
        int id;
        try {
            JDBC_STATEMENT = connection.createStatement();
            logger.debug(bundle.getString("connection.positive"));
            preparedStatement = connection.prepareStatement
                    ("SELECT BOARDS.BOARD_ID from BOARDS where BOARDS.BOARD_NAME=?");
            preparedStatement.setString(1, fileName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            } else {
                logger.error(bundle.getString("io.error"));
                throw new IOException(bundle.getString("io.error"));
            }
            logger.debug(bundle.getString("data.read"));
            preparedStatement = connection.prepareStatement
                    ("SELECT FIELDS.VAL from FIELDS where FIELDS.BOARD_ID=?");
            preparedStatement.setInt(1, id);
            resultSetVal = preparedStatement.executeQuery();
            if (resultSetVal.next()) {
                fields = resultSetVal.getString(1);
            } else {
                logger.error(bundle.getString("io.error"));
                throw new IOException(bundle.getString("io.error"));
            }
            logger.debug(bundle.getString("data.read"));
            for (int i = 0; i < 81; i++) {
                array[i] = (Character.getNumericValue(fields.charAt(i)));
            }
        } catch (SQLException | IOException e) {
            logger.error(bundle.getString("io.error"));
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard.setNumber(i, j, array[i * 9 + j]);
            }
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) {
        PreparedStatement preparedStatement;
        try {
            JDBC_STATEMENT = connection.createStatement();
            String createB = "Create Table BOARDS(BOARD_ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY, BOARD_NAME varchar(40) NOT NULL)";
            JDBC_STATEMENT.executeUpdate(createB);
            logger.debug(bundle.getString("tab.creation"));
            JDBC_STATEMENT = connection.createStatement();
            String createF = "Create Table FIELDS(VAL varchar(81) NOT NULL, BOARD_ID int," +
                    " FOREIGN KEY (BOARD_ID) REFERENCES BOARDS (BOARD_ID) ON UPDATE CASCADE ON DELETE CASCADE )";
            JDBC_STATEMENT.executeUpdate(createF);
            logger.debug(bundle.getString("tab.creation"));
        } catch (SQLException e) {
            logger.warn(bundle.getString("tab.error"));
        }
        try {
            JDBC_STATEMENT = connection.createStatement();
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
            JDBC_STATEMENT = connection.createStatement();
            String insert = "INSERT INTO FIELDS (VAL) VALUES(?)";
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, obj.boardConcatedValues());
            preparedStatement.executeUpdate();
            logger.debug(bundle.getString("data.insertion"));
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(bundle.getString("io.error"), e);
        }
    }

    @Override
    public void close() {
        try {
            JDBC_STATEMENT.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(bundle.getString("connection.error"));
        }
    }


}
