package sudoku;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.ApplicationException;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChoiceWindowControl implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(ChoiceWindowControl.class);
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
    public void onActionButtonStartGame(ActionEvent actionEvent) throws ClassNotFoundException, ApplicationException {
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

    private void switchLanguage(Locale locale) throws ApplicationException {
        Scene scene = Pane.getScene();
        try {
            scene.setRoot(FXMLLoader.load(getClass().getResource("ChoiceWindow.fxml"), ResourceBundle.getBundle("sudoku/Language", locale)));
        } catch (IOException e) {
            logger.error("Unexpected error in application loading");
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }
    }

    public void changeLangEn(ActionEvent actionEvent) throws ApplicationException {
        enLangButton.setSelected(true);
        switchLanguage(Locale.ENGLISH);
    }

    public void changeLangPl(ActionEvent actionEvent) throws ApplicationException {
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
