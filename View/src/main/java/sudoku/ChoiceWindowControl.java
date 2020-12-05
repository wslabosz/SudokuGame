package sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;


import java.io.IOException;

public class ChoiceWindowControl {

    private static String diff;
    public ChoiceBox difficultyChoiceBox;

    public static String getDiff() {
        return diff;
    }

    @FXML
    public void onActionButtonStartGame(ActionEvent actionEvent) throws IOException {
        diff = difficultyChoiceBox.getSelectionModel().getSelectedItem().toString();
        if (!(diff == null)) {
            FXMLStageControl.setScene("sudokuBoardWindow.fxml");
        }
    }

}
