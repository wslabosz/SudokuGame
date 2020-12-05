package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private FXMLStageControl stageControl;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLStageControl.buildStage(stage, "choiceWindow.fxml", "Sudoku");
    }
    public static void main(String[] args) {
        launch();
    }
}
