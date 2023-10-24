package app.core.forms;

import app.tools.forms.BrandingFormV1;
import app.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class MainWindowForm extends VBox {
    @FXML
    private SplitPane splitPane;

    public MainWindowForm() {
        FXUtils.loadFXML(this);
    }

    @FXML
    private void openBrandingFormV1() {
        openNewTab("Branding v1", new BrandingFormV1());
    }

    private void openNewTab(String title, Node node) {
        TabPane tabPane = getTabPane();

        Tab tab = new Tab(title, node);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private TabPane getTabPane() {
        for (Node item : splitPane.getItems()) {
            if (item instanceof TabPane) {
                return (TabPane) item;
            }
        }
        return null;
    }
}
