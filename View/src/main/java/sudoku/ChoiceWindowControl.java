package sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChoiceWindowControl implements Initializable {
    @FXML
    public ChoiceBox<String> difficultyChoiceBox = new ChoiceBox<>();
    public RadioButton enLangButton;
    public RadioButton plLangButton;
    final private ToggleGroup group = new ToggleGroup();
    public Label diffLabel;
    public Button buttonStartGame;
    public Label authorsLabel;

    private ResourceBundle resourceBundle;
    private ResourceBundle autorzy = ResourceBundle.getBundle("sudoku.i18n.authors.authors", ResourceController.getLocale());
    private Difficulty diff;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        resourceBundle = bundle;
        enLangButton.textProperty().bind(ResourceController.createStringBinding(resourceBundle.getBaseBundleName(), "radioButtonEn"));
        plLangButton.textProperty().bind(ResourceController.createStringBinding(resourceBundle.getBaseBundleName(), "radioButtonPl"));
        diffLabel.textProperty().bind(ResourceController.createStringBinding(resourceBundle.getBaseBundleName(), "ChooseDiff"));
        buttonStartGame.textProperty().bind(ResourceController.createStringBinding(resourceBundle.getBaseBundleName(), "startButton"));
        enLangButton.setToggleGroup(group);
        plLangButton.setToggleGroup(group);
        plLangButton.setSelected(true);
        //difficultyChoiceBox.getItems().set();
        authorsLabel.textProperty().bind(ResourceController.createStringBinding(autorzy.getBaseBundleName(), "authors"));
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

    private void switchLanguage(Locale locale) {
        ResourceController.setLocale(locale);
    }

    public void changeLangEn(ActionEvent actionEvent) {
        switchLanguage(Locale.ENGLISH);
        enLangButton.setSelected(true);
    }

    public void changeLangPl(ActionEvent actionEvent) {
        switchLanguage(new Locale("pl", "PL"));
        plLangButton.setSelected(true);
    }
}
