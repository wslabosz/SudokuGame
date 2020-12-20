package sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.util.ResourceBundle;

public class ChoiceWindowControl {
    @FXML
    public ChoiceBox<String> difficultyChoiceBox = new ChoiceBox<>();

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sudoku/Language");
    private Difficulty diff;
    private final String easy = resourceBundle.getString("Easy");
    private final String normal = resourceBundle.getString("Normal");
    private final String hard = resourceBundle.getString("Hard");

    @FXML
    private void initialize() {
//        difficultyChoiceBox.getItems().addAll(
//                resourceBundle.getString("choiceBoxLang1"),
//                resourceBundle.getString("choiceBoxLang2")
//        );

        difficultyChoiceBox.getItems().addAll(
                resourceBundle.getString(Difficulty.Easy.toString()),
                resourceBundle.getString(Difficulty.Normal.toString()),
                resourceBundle.getString(Difficulty.Hard.toString())
        );
    }

    @FXML
    public void onActionButtonStartGame(ActionEvent actionEvent) throws IOException {
        if (difficultyChoiceBox.getSelectionModel().getSelectedItem() != null) {
            if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(easy)) {
                diff = Difficulty.Easy;
            } else if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(normal)) {
                diff = Difficulty.Normal;
            } else if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(hard)) {
                diff = Difficulty.Hard;
            }
            FXMLStageControl.passDifficulty("sudokuBoardWindow.fxml", resourceBundle, diff);
        }
    }

}
