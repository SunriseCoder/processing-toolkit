package app.core.forms;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowManager {
    private static WindowManager instance;

    private List<Stage> stages;

    private WindowManager() {
        // Singleton
        stages = new ArrayList<>();
    }

    public static WindowManager getInstance() {
        if (instance == null) {
            synchronized (WindowManager.class) {
                if (instance == null) {
                    instance = new WindowManager();
                }
            }
        }
        return instance;
    }

    public Stage createNewWindow() {
        Stage stage = new Stage();
        createNewWindow(stage);
        stages.add(stage);
        return stage;
    }

    public void addExistingWindow(Stage stage) {
        createNewWindow(stage);
        stages.add(stage);
    }

    private void createNewWindow(Stage stage) {
        stage.setTitle("Processing Toolkit");
        stage.setWidth(800);
        stage.setHeight(600);

        MainWindowForm window = new MainWindowForm();
        Scene scene = new Scene(window);
        stage.setScene(scene);
        stage.show();
    }
}
