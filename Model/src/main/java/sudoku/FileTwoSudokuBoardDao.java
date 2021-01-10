package sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.OperationOnFileException;
import java.io.*;

public class FileTwoSudokuBoardDao implements Dao<SudokuBoard[]> {
    private static final Logger logger = LoggerFactory.getLogger(FileTwoSudokuBoardDao.class);
    private final String filename;

    public FileTwoSudokuBoardDao(String filename) {
        this.filename = filename;
    }

    @Override
    public SudokuBoard[] read() throws DaoException {
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            var board = new SudokuBoard[2];
            board[0] = (SudokuBoard) ois.readObject();
            board[1] = (SudokuBoard) ois.readObject();
            return board;
        } catch (IOException | ClassNotFoundException ex) {
            logger.error(ex.getLocalizedMessage(), ex);
            throw new OperationOnFileException(ex);
        }
    }

    @Override
    public void write(SudokuBoard[] obj) throws DaoException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj[0]);
            oos.writeObject(obj[1]);
        } catch (IOException ex) {
            logger.error(ex.getLocalizedMessage(), ex);
            throw new OperationOnFileException(ex);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
