package sudoku;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.ApplicationExpection;

import java.io.IOException;
import java.util.ResourceBundle;

public class FXMLStageControl {

    private static Stage stage;
    private static final Logger logger = LoggerFactory.getLogger(FXMLStageControl.class);
    private static void setStage(Stage stage) {
        FXMLStageControl.stage = stage;
    }

    private static Parent loadFXML(String fxml, ResourceBundle resourceBundle) throws ApplicationExpection {
        try {
            return new FXMLLoader(FXMLStageControl.class.getResource(fxml), resourceBundle).load();
        } catch (IOException e) {
            logger.error("Unexpected error in application loading");
            throw new ApplicationExpection(e);
        }
    }

    public static void setScene(String filePath, ResourceBundle resourceBundle) throws ApplicationExpection {
        stage.setScene(new Scene(loadFXML(filePath, resourceBundle)));
        stage.titleProperty().setValue(resourceBundle.getString("windowTitle"));
        //scene.getStylesheets().add("sudoku/sudokuBoardWindow.css");
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static void setStage(Stage stage, String filePath, ResourceBundle resourceBundle) throws ApplicationExpection {
        setStage(stage);
        stage.setScene(new Scene(loadFXML(filePath, resourceBundle)));
        stage.titleProperty().setValue(resourceBundle.getString("windowTitle"));
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static void passDifficulty(String filePath, ResourceBundle resourceBundle, Difficulty diff) throws ApplicationExpection, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(FXMLStageControl.class.getResource(filePath), resourceBundle);
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            logger.error("Unexpected error in application loading");
            throw new ApplicationExpection(e);
        }
        stage.titleProperty().setValue(resourceBundle.getString("windowTitle"));
        stage.sizeToScene();
        stage.setResizable(false);
        SudokuBoardWindowControl controller = loader.getController();
        controller.initData(diff);
        stage.show();
    }

}
