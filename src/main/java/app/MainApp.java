package app;

import app.core.forms.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        WindowManager.getInstance().addExistingWindow(stage);
        //WindowManager.getInstance().createNewWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}
