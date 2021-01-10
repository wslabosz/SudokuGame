package sudoku;

import org.apache.derby.iapi.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    final static Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private String fileName;
    private static final String URL = "jdbc:derby:Sudoku;create=true";
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
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
            connection = DriverManager.getConnection(URL);
            logger.debug(bundle.getString("connection.positive"));
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(bundle.getString("connection.negative"), e);
            throw new DaoException(bundle.getString("connection.negative"), e);
        }
    }

    @Override
    public SudokuBoard read() throws DaoException {
        return null;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {

    }

    @Override
    public void close() throws Exception {

    }


}
