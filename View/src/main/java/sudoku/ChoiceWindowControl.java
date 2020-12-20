package sudoku;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

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

    private ResourceBundle resourceBundle;
    private final ResourceBundle autorzy = ResourceBundle.getBundle("sudoku.i18n.authors.authors", ResourceController.getLocale());
    private Difficulty diff;
    public ObservableValue<Locale> locale;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        resourceBundle = bundle;
        locale = ResourceController.getValueLocale();
        enLangButton.textProperty().bind(ResourceController.createStringBinding(resourceBundle.getBaseBundleName(), "radioButtonEn"));
        plLangButton.textProperty().bind(ResourceController.createStringBinding(resourceBundle.getBaseBundleName(), "radioButtonPl"));
        diffLabel.textProperty().bind(ResourceController.createStringBinding(resourceBundle.getBaseBundleName(), "ChooseDiff"));
        buttonStartGame.textProperty().bind(ResourceController.createStringBinding(resourceBundle.getBaseBundleName(), "startButton"));
        enLangButton.setToggleGroup(group);
        plLangButton.setToggleGroup(group);
        plLangButton.setSelected(true);
        authorsLabel.textProperty().bind(ResourceController.createStringBinding(autorzy.getBaseBundleName(), "authors"));
        difficultyChoiceBox.itemsProperty().bind(Bindings.createObjectBinding(() ->
                FXCollections.observableArrayList(ResourceController.get(resourceBundle.getBaseBundleName(), "Easy")
                        , ResourceController.get(resourceBundle.getBaseBundleName(), "Normal"),
                        ResourceController.get(resourceBundle.getBaseBundleName(), "Hard")), locale));


//        difficultyChoiceBox.getItems().addAll(
//                Label l1 = new Label();
//
//                resourceBundle.getString(Difficulty.Easy.toString()),
//                resourceBundle.getString(Difficulty.Normal.toString()),
//                resourceBundle.getString(Difficulty.Hard.toString())
//        );

    }

    @FXML
    public void onActionButtonStartGame(ActionEvent actionEvent) throws IOException {
        if (difficultyChoiceBox.getSelectionModel().getSelectedItem() != null) {
            if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(ResourceController.get(resourceBundle.getBaseBundleName(), "Easy"))) {
                diff = Difficulty.Easy;
            } else if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(ResourceController.get(resourceBundle.getBaseBundleName(), "Normal"))) {
                diff = Difficulty.Normal;
            } else if (difficultyChoiceBox.getSelectionModel().getSelectedItem().equals(ResourceController.get(resourceBundle.getBaseBundleName(), "Hard"))) {
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
