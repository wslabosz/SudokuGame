package sudoku;

import java.util.ResourceBundle;
import sudoku.exceptions.ApplicationException;

public abstract class AbstractDao implements Dao<SudokuBoard>, AutoCloseable {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("exceptions");

    public AbstractDao() throws ApplicationException {
        if (bundle == null) {
            throw new ApplicationException(ApplicationException.RESOURCE_BUNDLE_IS_NULL);
        }
    }

    public String getDaoMessage(String key) {
        return bundle.getString(key);
    }
}
