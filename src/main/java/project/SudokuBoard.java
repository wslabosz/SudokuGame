package project;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements Serializable, PropertyChangeListener  {
    public static final int SIZE = 9;
    private final List<SudokuField> board;
    private final transient SudokuSolver sudokuSolver;
    private boolean doListen = false;

    public SudokuBoard(SudokuSolver sudokuSolver) {
        board = Arrays.asList(new SudokuField[SIZE * SIZE]);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board.set(i * 9 + j, new SudokuField(0));
                board.get(i * 9 + j).addPropertyChangeListener(this);
            }
        }
        this.sudokuSolver = sudokuSolver;
    }

    public int getNumberFromPosition(int xpos, int ypos) {
        return board.get(xpos * 9 + ypos).getFieldValue();
    }

    public void setNumber(int xpos, int ypos, int number) {
        //exception
        try {
            board.get(xpos * 9 + ypos).setFieldValue(number);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public SudokuRow getRow(int row) {
        SudokuField[] fields = new SudokuField[SIZE];
        for (int i = 0; i < SIZE; i++) {
            fields[i] = board.get(9 * row + i);
        }
        return new SudokuRow(Arrays.asList(fields));
    }

    public SudokuColumn getColumn(int column) {
        SudokuField[] fields = new SudokuField[SIZE];
        for (int i = 0; i < SIZE; i++) {
            fields[i] = board.get(column + 9 * i);
        }
        return new SudokuColumn(Arrays.asList(fields));
    }

    public SudokuBox getBox(int row, int column) {
        SudokuField[] fields = new SudokuField[SIZE];
        int rows = row - row % 3;
        int columns = column - column % 3;
        int index = 0;
        for (int i = rows; i < rows + 3; i++) {
            for (int j = columns; j < columns + 3; j++) {
                fields[index++] = board.get(i * 9 + j);
            }
        }
        return new SudokuBox(Arrays.asList(fields));
    }

    public void setListening(boolean val) {
        doListen = val;
    }

    private boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!getColumn(i).verify() || !getRow(i).verify()) {
                return false;
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!getBox(i, j).verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void solveGame() {
        sudokuSolver.solve(this);
        this.checkBoard();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("board", board)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SudokuBoard other = (SudokuBoard) obj;
        return Objects.equal(this.board, other.board);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.board);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (doListen) {
            try {
                if (!checkBoard()) {
                    throw new WrongFieldValueSudokuException("Inserted invalid value");
                }
            } catch (WrongFieldValueSudokuException ex) {
                ex.getMessage();
            }
        }
    }

}
