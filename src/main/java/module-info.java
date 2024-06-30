module myModule {
    requires javafx.controls;
    requires javafx.fxml;

    opens airship to javafx.fxml;
    exports airship;
}