package app.core.forms;

import app.tools.forms.BrandingForm;
import app.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class WindowForm extends VBox {
    @FXML
    private SplitPane splitPane;

    public WindowForm() {
        FXUtils.loadFXML(this);
    }

    @FXML
    private void openBrandingForm() {
        openNewTab(new BrandingForm(), "Branding");
    }

    private void openNewTab(Node node, String name) {
        TabPane tabPane = getTabPane();

        Tab tab = new Tab();
        tab.setText(name);
        tab.setContent(node);
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
