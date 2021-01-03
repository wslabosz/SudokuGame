package sudoku.exceptions;

public class OperationOnFileException extends DaoException {
    public OperationOnFileException(Throwable ex) {
        super("Operation on file failed", ex);
    }
}
