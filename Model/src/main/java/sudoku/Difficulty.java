package sudoku;

import java.util.Random;

public enum Difficulty {
    Easy(44),
    Normal(51),
    Hard(58);

    private final int elements;

    Difficulty(int elements) {
        this.elements = elements;
    }

    public void eraseFields(SudokuBoard board) {
        Random rand = new Random();
        int loops = 0;
        while (loops < this.elements) {
            int[] positions = {rand.nextInt(8), rand.nextInt(8)};
            if (board.getNumberFromPosition(positions[0], positions[1]) != 0) {
                board.setNumber(positions[0], positions[1], 0);
                loops++;
            }
        }
    }
}
