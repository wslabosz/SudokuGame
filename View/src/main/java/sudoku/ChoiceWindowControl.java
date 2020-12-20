package sudoku;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChoiceWindowControl implements Initializable {
    @FXML
    public ChoiceBox<String> difficultyChoiceBox = new ChoiceBox<>();
    public RadioButton enLangButton;
    public RadioButton plLangButton;

    private ResourceBundle resourceBundle;
    private Difficulty diff;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        final ToggleGroup group = new ToggleGroup();
        enLangButton.setToggleGroup(group);
        plLangButton.setToggleGroup(group);
        plLangButton.setSelected(true);
        resourceBundle = bundle;
        difficultyChoiceBox.itemsProperty().set(FXCollections.observableArrayList(
                ResourceController.get(Difficulty.Easy.toString()),
                ResourceController.get(Difficulty.Normal.toString()),
                ResourceController.get(Difficulty.Hard.toString())));
        //difficultyChoiceBox.itemsProperty().bind(

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

    public void changeLangEn(ActionEvent actionEvent) {
        if (!enLangButton.isSelected()) {
            enLangButton.setSelected(true);
            ResourceController.setLocale(Locale.ENGLISH);
        }
    }

    public void changeLangPl(ActionEvent actionEvent) {
        if (!plLangButton.isSelected()) {
            plLangButton.setSelected(true);
            ResourceController.setLocale(new Locale("pl","PL"));
        }
    }
}
