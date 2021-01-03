package sudoku.exceptions;

import java.io.IOException;

public class ApplicationExpection extends IOException {
    public ApplicationExpection(Throwable ex) {
        super(ex);
    }
}
