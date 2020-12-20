package sudoku;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChoiceWindowControl implements Initializable {
    @FXML
    public ChoiceBox<String> difficultyChoiceBox = new ChoiceBox<>();

    private ResourceBundle resourceBundle;
    private Difficulty diff;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        resourceBundle = bundle;
//        difficultyChoiceBox.getItems().addAll(
//                resourceBundle.getString("choiceBoxLang1"),
//                resourceBundle.getString("choiceBoxLang2")
//        );
        difficultyChoiceBox.itemsProperty().set(FXCollections.observableArrayList(resourceBundle.getString(Difficulty.Easy.toString()),
                resourceBundle.getString(Difficulty.Normal.toString()),
                resourceBundle.getString(Difficulty.Hard.toString())));
        difficultyChoiceBox.valueProperty().bind(ResourceController.createStringBinding("windowTitle"));
//        difficultyChoiceBox.getItems().addAll(
//                resourceBundle.getString(Difficulty.Easy.toString()),
//                resourceBundle.getString(Difficulty.Normal.toString()),
//                resourceBundle.getString(Difficulty.Hard.toString())
//        );
    }

    @FXML
    public void onActionButtonStartGame(ActionEvent actionEvent) throws IOException {
        if (difficultyChoiceBox.getSelectionModel().getSelectedItem() != null) {
            if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(resourceBundle.getString(Difficulty.Easy.toString()))) {
                diff = Difficulty.Easy;
            } else if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(resourceBundle.getString(Difficulty.Normal.toString()))) {
                diff = Difficulty.Normal;
            } else if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(resourceBundle.getString(Difficulty.Hard.toString()))) {
                diff = Difficulty.Hard;
            }
            FXMLStageControl.passDifficulty("sudokuBoardWindow.fxml", resourceBundle, diff);
        }
    }

}
