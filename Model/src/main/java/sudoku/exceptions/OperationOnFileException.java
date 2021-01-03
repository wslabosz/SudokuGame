package sudoku.exceptions;

public class OperationOnFileException extends DaoException {
    public OperationOnFileException(Throwable ex) {
        super(ex.getLocalizedMessage(), ex);
    }
}
