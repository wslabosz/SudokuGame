package project;

public class WrongFieldValueSudokuException extends IllegalArgumentException {
    public WrongFieldValueSudokuException(final String message) {
        super(message);
    }
}
