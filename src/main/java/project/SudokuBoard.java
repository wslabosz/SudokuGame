package project;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements PropertyChangeListener {
    public static final int SIZE = 9;
    private final List<List<SudokuField>> board;
    private final SudokuSolver sudokuSolver;
    private boolean doListen = false;

    public SudokuBoard(SudokuSolver sudokuSolver) {
        board = Arrays.asList(new List[SIZE]);
        for (int i = 0; i < SIZE; i++) {
            board.set(i, Arrays.asList(new SudokuField[SIZE]));
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board.get(i).set(j, new SudokuField(0));
                this.board.get(i).get(j).addPropertyChangeListener(this);
            }
        }
        this.sudokuSolver = sudokuSolver;
    }

    public int getNumberFromPosition(int xpos, int ypos) {
        return board.get(xpos).get(ypos).getFieldValue();
    }

    public void setNumber(int xpos, int ypos, int number) {
        //exception
        try {
            board.get(xpos).get(ypos).setFieldValue(number);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public SudokuRow getRow(int row) {
        List<SudokuField> fields = Arrays.asList(new SudokuField(SIZE));
        for (int i = 0; i < SIZE; i++) {
            fields.set(i, board.get(row).get(i));
        }
        return new SudokuRow(fields);
    }

    public SudokuColumn getColumn(int column) {
        List<SudokuField> fields = Arrays.asList(new SudokuField(SIZE));
        for (int i = 0; i < SIZE; i++) {
            fields.set(i, board.get(i).get(column));
        }
        return new SudokuColumn(fields);
    }

    public SudokuBox getBox(int row, int column) {
        List<SudokuField> fields = Arrays.asList(new SudokuField(SIZE));
        int rows = row - row % 3;
        int columns = column - column % 3;
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields.set(index++, board.get(rows + 1).get(columns + j));
            }
        }
        return new SudokuBox(fields);
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
                stringBuilder.append(this.board.get(i).get(j).getFieldValue())
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
                System.out.println("Wrong number inserted!");
            }
        }
    }

}
