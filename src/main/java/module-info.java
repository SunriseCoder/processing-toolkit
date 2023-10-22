module app {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;

    opens app.core.forms to javafx.fxml;
    exports app;
}
