package project;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SudokuBoard implements PropertyChangeListener {
    public static final int SIZE = 9;
    private final SudokuField [][] board = new SudokuField[SIZE][SIZE];
    private final SudokuSolver sudokuSolver;
    private boolean doListen = false;

    public SudokuBoard(SudokuSolver sudokuSolver) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = new SudokuField(0);
                this.board[i][j].addPropertyChangeListener(this);
            }
        }
        this.sudokuSolver = sudokuSolver;
    }

    public int getNumberFromPosition(int xpos, int ypos) {
        return board[xpos][ypos].getFieldValue();
    }

    public void setNumber(int xpos, int ypos, int number) {
        //exception
        try {
            board[xpos][ypos].setFieldValue(number);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public SudokuRow getRow(int row) {
        SudokuField[] fields = new SudokuField[SIZE];
        System.arraycopy(board[row], 0, fields, 0, SIZE);
        return new SudokuRow(fields);
    }

    public SudokuColumn getColumn(int column) {
        SudokuField[] fields = new SudokuField[SIZE];
        for (int i = 0; i < SIZE; i++) {
            fields[i] = board[i][column];
        }
        return new SudokuColumn(fields);
    }

    public SudokuBox getBox(int row, int column) {
        SudokuField[] fields = new SudokuField[SIZE];
        int rows = row - row % 3;
        int columns = column - column % 3;
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[index++] = board[rows + i][columns + j];
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
                stringBuilder.append(this.board[i][j].getFieldValue())
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
