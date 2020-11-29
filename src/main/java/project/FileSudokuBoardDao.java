package project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private final String filename;

    public FileSudokuBoardDao(String filename) {
        this.filename = filename + ".txt";
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard object = null;
        // try-with-resources
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
             object = (SudokuBoard) ois.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            throw new OperationOnFileException(ex);
        }
        return object;
    }

    @Override
    public void write(SudokuBoard object) {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream ous = new ObjectOutputStream(fos)) {
             ous.writeObject(object);
        } catch (IOException ex) {
            throw new OperationOnFileException(ex);
        }
    }


    //public void finalize() {}
}
