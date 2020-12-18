package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class App extends Application {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sudoku/Language");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLStageControl.setStage(stage, "choiceWindow.fxml", resourceBundle.getString("windowTitle"), resourceBundle);
    }
    public static void main(String[] args) {
        launch();
    }
}
