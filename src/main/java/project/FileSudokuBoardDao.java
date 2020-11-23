package project;

import java.io.File;

public class FileSudokuBoardDao implements Dao {

    File file;

    public FileSudokuBoardDao(String filename) {
        this.file = new File(filename + ".txt");
    }

    @Override
    public Object read() {
        return null;
    }

    @Override
    public void write(Object obj) {

    }

    public void finalize() {}
}
