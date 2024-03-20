module com.airshipg {
    requires javafx.controls;
    requires javafx.fxml;
//    requires javafx.base;

//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;

  opens com.airshipg to javafx.fxml;
    exports com.airshipg;
}