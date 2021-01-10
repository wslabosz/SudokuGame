package sudoku;

import sudoku.exceptions.ApplicationException;
import sudoku.exceptions.DaoException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>{
    private final String boardname;

    public JdbcSudokuBoardDao(String boardname) throws ApplicationException {
        if (boardname == null) {
            throw new DaoException(DaoException.NULL_NAME);
        }
        this.boardname = boardname;
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
