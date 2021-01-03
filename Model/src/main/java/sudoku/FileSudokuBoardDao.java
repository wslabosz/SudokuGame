package sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import sudoku.exceptions.OperationOnFileException;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, Serializable {

    private final String filename;

    public FileSudokuBoardDao(String filename) {
        this.filename = filename;
    }

    @Override
    public SudokuBoard read() throws OperationOnFileException {
        // try-with-resources
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
             return (SudokuBoard) ois.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            throw new OperationOnFileException(ex);
        }
    }

    @Override
    public void write(SudokuBoard object) throws OperationOnFileException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
             oos.writeObject(object);
        } catch (IOException ex) {
            throw new OperationOnFileException(ex);
        }
    }

    @Override
    public void close() {
    }


    //public void finalize() {}
}
