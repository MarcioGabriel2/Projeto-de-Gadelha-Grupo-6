module SisLogin3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example to javafx.fxml, javafx.graphics;
    exports org.example;
    exports br.edu.ufersa.SistemaDeLogin.view;

}