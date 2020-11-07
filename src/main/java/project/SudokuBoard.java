package project;

import java.util.HashSet;
import java.util.Set;

public class SudokuBoard {
    public static final int SIZE = 9;
    private final SudokuField [][] board = new SudokuField[SIZE][SIZE];
    private final SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver sudokuSolver) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = new SudokuField(0);
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

    private boolean checkRowRegularity() {
        Set<Integer> setRows = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setRows.add(getNumberFromPosition(i, j));
            }
            if (setRows.size() == SIZE) {
                setRows.clear();
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean checkColRegularity() {
        Set<Integer> setCols = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setCols.add(getNumberFromPosition(j, i));
            }
            if (setCols.size() == SIZE) {
                setCols.clear();
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean checkBoxRegularity() {
        Set<Integer> setBoxes = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    setBoxes.add(getNumberFromPosition(3 * (i / 3) + j, 3 * (i % 3) + k));
                }
            }
            if (setBoxes.size() == SIZE) {
                setBoxes.clear();
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean checkBoard() {
        return checkBoxRegularity() && checkColRegularity() && checkRowRegularity();
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
}
