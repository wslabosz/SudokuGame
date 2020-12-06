package sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;


import java.io.IOException;

public class ChoiceWindowControl {

    private static Difficulty.diff diff;
    public ChoiceBox difficultyChoiceBox;

    public static Difficulty.diff getDiff() {
        return diff;
    }

    @FXML
    public void onActionButtonStartGame(ActionEvent actionEvent) throws IOException {
        if (difficultyChoiceBox.getSelectionModel().getSelectedItem() != null) {
            diff = Difficulty.diff.valueOf(difficultyChoiceBox.getSelectionModel().getSelectedItem().toString());
            if (diff != null) {
                FXMLStageControl.setScene("sudokuBoardWindow.fxml");
            }
        }
    }

}
