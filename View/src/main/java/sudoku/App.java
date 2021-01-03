package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import sudoku.exceptions.ApplicationException;

import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {
    private final Locale locale = new Locale("pl", "PL");
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sudoku/Language", locale);


    @Override
    public void start(Stage stage) throws ApplicationException {
        FXMLStageControl.setStage(stage, "choiceWindow.fxml", resourceBundle);
    }
    public static void main(String[] args) {
        launch();
    }
}
