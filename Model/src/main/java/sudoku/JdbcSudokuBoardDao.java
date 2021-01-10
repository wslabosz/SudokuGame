package sudoku;

import sudoku.exceptions.DaoException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>{
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
