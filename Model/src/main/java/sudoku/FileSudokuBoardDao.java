package sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.ApplicationException;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.OperationOnFileException;

public class FileSudokuBoardDao extends AbstractDao implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class);
    private final String filename;

    public FileSudokuBoardDao(String filename) throws ApplicationException {
        if (filename == null) {
            throw new DaoException(DaoException.NULL_NAME);
        }
        this.filename = filename;
    }

    @Override
    public SudokuBoard read() throws OperationOnFileException {
        // try-with-resources
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
             return (SudokuBoard) ois.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            logger.error(getDaoMessage("read.failure"), ex);
            throw new OperationOnFileException(ex);
        }
    }

    @Override
    public void write(SudokuBoard object) throws OperationOnFileException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
             oos.writeObject(object);
        } catch (IOException ex) {
            logger.error(getDaoMessage("write.failure"), ex);
            throw new OperationOnFileException(ex);
        }
    }

    @Override
    public void close() {
    }


    //public void finalize() {}
}
