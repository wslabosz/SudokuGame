package sudoku;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class FXMLStageControl {

    private static Stage stage;

    private static void setStage(Stage stage) {
        FXMLStageControl.stage = stage;
    }

    private static Parent loadFXML(String fxml, ResourceBundle resourceBundle) throws IOException {
        return new FXMLLoader(FXMLStageControl.class.getResource(fxml), resourceBundle).load();
    }

    public static void setScene(String filePath, ResourceBundle resourceBundle) throws IOException {
        Scene scene = new Scene(loadFXML(filePath, resourceBundle));
        stage.setScene(scene);
        //scene.getStylesheets().add("sudoku/sudokuBoardWindow.css");
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static void setStage(Stage stage, String filePath, String title, ResourceBundle resourceBundle) throws IOException {
        setStage(stage);
        stage.setScene(new Scene(loadFXML(filePath, resourceBundle)));
        stage.setTitle(title);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }
}
