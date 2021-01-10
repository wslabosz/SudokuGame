package sudoku;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class savesController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(savesController.class);
    ResourceBundle bundle;
    @FXML
    private TextField inputField;
    private boolean input = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
    }

    @FXML
    private void submit() {
        input = true;
        inputField.getScene().getWindow().hide();
    }

    public String getInput() {
        if (!inputField.getText().isEmpty()) {
            return inputField.getText();
        }
        logger.error(bundle.getString("saveFailed"));
        return null;
    }
}
