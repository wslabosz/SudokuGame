package project;

import java.util.Random;

public class SudokuBoard {
    private int [][] sudokuBoard = new int [9][9];

    public SudokuBoard() {

    }

    public SudokuBoard(int[][] board) {
        sudokuBoard = board;
    }

    public void start() {
        Random generator = new Random();
        for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 9; k++) {
                sudokuBoard[j][k] = 0;
            }
        }
        int rzad;
        int kolumna;
        for (int i = 0; i < 9; i++) {
            do {
                rzad = generator.nextInt(8);
                kolumna = generator.nextInt(8);
            } while (sudokuBoard[rzad][kolumna] != 0);
            sudokuBoard[rzad][kolumna] = i;
        }
    }

    public void fillBoard() {
        start();
        solve();
    }

    public int getCell(int w, int k) {
        return sudokuBoard[w][k];
    }

    private boolean isRowClear(int row, int nr) {
        for (int i = 0; i < 9; i++) {
            if (sudokuBoard[row][i] == nr) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnClear(int col, int nr) {
        for (int i = 0; i < 9; i++) {
            if (sudokuBoard[i][col] == nr) {
                return false;
            }
        }
        return true;
    }

    private boolean isBoxClear(int row, int col, int nr) {
        int rows = row - row % 3;
        int cols = col - col % 3;
        for (int i = rows; i < rows + 3; i++) {
            for (int j = cols; j < cols + 3; j++) {
                if (sudokuBoard[i][j] == nr) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isCellOk(int w, int k, int nr) {
        return isRowClear(w, nr) && isColumnClear(k, nr) && isBoxClear(w, k, nr);
    }

    private boolean solve() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuBoard[row][col] == 0) {
                    for (int number = 1; number <= 9; number++) {
                        if (isCellOk(row, col, number)) {
                            sudokuBoard[row][col] = number;

                            if (solve()) {
                                return true;
                            } else {
                                sudokuBoard[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public void display() {
        for (int rzad = 0; rzad < 9; rzad++) {
            if (rzad % 3 == 0 && !(rzad == 0 ||  rzad == 9)) {
                System.out.println();
            }
            for (int kolumna = 0; kolumna < 9; kolumna++) {
                if (kolumna % 3 == 0 && kolumna != 0) {
                    System.out.print(" ");
                }
                System.out.print(sudokuBoard[rzad][kolumna]);
                if (kolumna != 8) {
                    System.out.print("  ");
                }
            }
            if (rzad != 8) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("--------------------------------------");
    }
}