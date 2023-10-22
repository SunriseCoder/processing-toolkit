package app.utils;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class FXUtils {

    public static void loadFXML(Node node) {
        String resourceName = node.getClass().getSimpleName() + ".fxml";
        URL resource = node.getClass().getResource(resourceName);
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setController(node);
        loader.setRoot(node);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
