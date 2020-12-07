package sudoku;

import java.util.*;

public class Difficulty {

    private Random rand = new Random();
    private Set<Field> fieldsToRemove = new HashSet<>();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("sudoku/Language");

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

    public void difficultyChooser(SudokuBoard board, String difficulty) {
        if (difficulty.contentEquals(resourceBundle.getString("diffEasy"))) {
           randomFields(44);
        } else if (difficulty.contentEquals(resourceBundle.getString("diffNormal"))) {
           randomFields(51);
        } else if (difficulty.contentEquals(resourceBundle.getString("diffHard"))) {
           randomFields(58);
        }
        for (Field iter : fieldsToRemove) {
            board.setNumber(iter.getxCoordinate(), iter.getyCoordinate(), 0);
        }
    }
}

