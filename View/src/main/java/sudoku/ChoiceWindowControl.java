package sudoku;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChoiceWindowControl implements Initializable {
    @FXML
    public ChoiceBox<String> difficultyChoiceBox;
    public RadioButton enLangButton;
    public RadioButton plLangButton;
    final private ToggleGroup group = new ToggleGroup();
    public Label diffLabel;
    public Button buttonStartGame;
    public Label authorsLabel;
    public AnchorPane Pane;

    private ResourceBundle resourceBundle;
    private ResourceBundle autorzy;
    private Difficulty diff;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        resourceBundle = bundle;
        enLangButton.setToggleGroup(group);
        plLangButton.setToggleGroup(group);
        radioButtonSet();
        autorzy = ResourceBundle.getBundle("sudoku.i18n.authors.authors", resourceBundle.getLocale());
        authorsLabel.setText(autorzy.getString("authors"));
        difficultyChoiceBox.getItems().setAll(FXCollections.observableArrayList(resourceBundle.getString("Easy"),
                resourceBundle.getString("Normal"),
                resourceBundle.getString("Hard")));
    }

    @FXML
    public void onActionButtonStartGame(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (difficultyChoiceBox.getSelectionModel().getSelectedItem() != null) {
            if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(resourceBundle.getString("Easy"))) {
                diff = Difficulty.Easy;
            } else if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(resourceBundle.getString("Normal"))) {
                diff = Difficulty.Normal;
            } else if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(resourceBundle.getString("Hard"))) {
                diff = Difficulty.Hard;
            }
            FXMLStageControl.passDifficulty("sudokuBoardWindow.fxml", resourceBundle, diff);
        }
    }

    private void switchLanguage(Locale locale) throws IOException {
        Scene scene = Pane.getScene();
        scene.setRoot(FXMLLoader.load(getClass().getResource("ChoiceWindow.fxml"), ResourceBundle.getBundle("sudoku/Language", locale)));
    }

    public void changeLangEn(ActionEvent actionEvent) throws IOException {
        enLangButton.setSelected(true);
        switchLanguage(Locale.ENGLISH);
    }

    public void changeLangPl(ActionEvent actionEvent) throws IOException {
        plLangButton.setSelected(true);
        switchLanguage(new Locale("pl", "PL"));
    }

    private void radioButtonSet() {
        switch(resourceBundle.getLocale().getLanguage()){
            case "en":
                enLangButton.setSelected(true);
                break;
            case "pl":
                plLangButton.setSelected(true);
                break;
            default:
                break;
        }
    }
}
