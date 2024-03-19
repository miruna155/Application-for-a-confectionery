module com.example.lab4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.xerial.sqlitejdbc;
    requires junit;
    requires java.sql;
    requires java.desktop;

    opens com.example.Domain.GUI to javafx.fxml;
    exports com.example.Domain.GUI;
    exports com.example.Domain.Tests;
}