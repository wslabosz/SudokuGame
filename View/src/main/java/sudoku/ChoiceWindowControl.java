package sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;


import java.io.IOException;
import java.util.ResourceBundle;

public class ChoiceWindowControl {

    private static String diff;
    public ChoiceBox<String> difficultyChoiceBox;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sudoku/Language");

    public static String getDiff() {
        return diff;
    }

    @FXML
    private void initialize() {
//        difficultyChoiceBox.getItems().addAll(
//                resourceBundle.getString("choiceBoxLang1"),
//                resourceBundle.getString("choiceBoxLang2")
//        );

        difficultyChoiceBox.getItems().addAll(
                resourceBundle.getString("diffEasy"),
                resourceBundle.getString("diffNormal"),
                resourceBundle.getString("diffHard")
        );
    }

    @FXML
    public void onActionButtonStartGame(ActionEvent actionEvent) throws IOException {
        if (difficultyChoiceBox.getSelectionModel().getSelectedItem() != null) {
            diff = difficultyChoiceBox.getSelectionModel().getSelectedItem();
            if (diff != null) {
                FXMLStageControl.setScene("sudokuBoardWindow.fxml", resourceBundle);
            }
        }
    }

}
