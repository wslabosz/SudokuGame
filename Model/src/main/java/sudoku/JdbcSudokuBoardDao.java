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
        int[] array = new int[81];
        try {
            JDBC_STATEMENT = connection.createStatement();
            logger.debug(bundle.getString("data.read"));
            preparedStatement = connection.prepareStatement
                    ("SELECT SudokuBoard.name, SudokuBoard.fields from Boards where SudokuBoard.name=?");
            preparedStatement.setString(1, fileName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fields = resultSet.getString(2);
            } else {
                logger.error(bundle.getString("io.error"));
                throw new IOException(bundle.getString("io.error"));
            }
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
            JDBC_STATEMENT.execute("CREATE TABLE Boards(identity number(4) primary key increment, name varchar (22))");
            JDBC_STATEMENT.execute("CREATE TABLE Fields(x varchar (81), y varchar (81), value varchar (81), board number(4) references Boards(identity))");
            logger.debug(bundle.getString("tab.creation"));
        } catch (SQLException e) {
            logger.warn(bundle.getString("tab.error"));
        }
        /*try {
            JDBC_STATEMENT = connection.createStatement();
            preparedStatement = connection.prepareStatement("UPDATE Boards SET fields =? WHERE name=?");
            preparedStatement.s(1, );
        }*/
    }

    @Override
    public void close() throws Exception {

    }


}
