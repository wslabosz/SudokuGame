package sudoku;

import java.util.*;

public class Difficulty {

    private Random rand = new Random();
    private Set<Field> fieldsToRemove = new HashSet<>();

    private void randomFields(int elements) {
        for (int i = 0; i < elements; i++) {
            boolean isElementAdded = false;
            while (!isElementAdded) {
                int x = rand.nextInt(9);
                int y = rand.nextInt(9);
                isElementAdded = fieldsToRemove.add(new Field(x, y));
            }
        }
    }

    public SudokuBoard difficultyChooser(SudokuBoard board, String diff) {
        switch (diff) {
            case "Easy" -> {
                randomFields(44);
            }
            case "Normal" -> {
                randomFields(51);
            }
            case "Hard" -> {
                randomFields(58);
            }
        }
        for (Field iter : fieldsToRemove) {
            board.setNumber(iter.getxCoordinate(), iter.getyCoordinate(), 0);
        }
        return board;
    }
}

