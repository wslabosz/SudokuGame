package sudoku;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLStageControl {

    private static Stage stage;

    private static void setStage(Stage stage) {
        FXMLStageControl.stage = stage;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        return new FXMLLoader(FXMLStageControl.class.getResource(fxml)).load();
    }

    public static void setScene(String filePath) throws IOException {
        Scene scene = new Scene(loadFXML(filePath));
        stage.setScene(scene);
        //scene.getStylesheets().add("sudoku/sudokuBoardWindow.css");
        stage.sizeToScene();
        stage.show();
    }

    public static void buildStage(Stage stage, String filePath, String title) throws IOException {
        setStage(stage);
        stage.setScene(new Scene(loadFXML(filePath)));
        stage.setTitle(title);
        stage.sizeToScene();
        stage.show();
    }
}
