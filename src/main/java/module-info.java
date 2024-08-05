module com.example.projetest {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.projetest to javafx.fxml;
    exports com.example.projetest;
}