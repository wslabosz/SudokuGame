package project;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements PropertyChangeListener {
    public static final int SIZE = 9;
    private final List<SudokuField> board;
    private final SudokuSolver sudokuSolver;
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[index++] = board.get(9 * rows + i + columns + j);
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
        StringBuilder stringBuilder = new StringBuilder();
        final String border = "=========================\n";
        stringBuilder.append(border);
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                stringBuilder.append(border);
            }
            stringBuilder.append("| ");
            for (int j = 0; j < 9; j++) {
                stringBuilder.append(board.get(i * 9 + j).getFieldValue())
                        .append(" ");
                if (j == 2 || j == 5) {
                    stringBuilder.append("| ");
                }
            }
            stringBuilder.append("|\n");

        }
        stringBuilder.append(border);
        return stringBuilder.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (doListen) {
            if (!checkBoard()) {
                throw new WrongFieldValueSudokuException("Inserted invalid value");
            }
        }
    }

}
