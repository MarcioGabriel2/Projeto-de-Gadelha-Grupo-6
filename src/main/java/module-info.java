module SisLogin3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Banco de dados no mySql

    opens br.edu.ufersa.SistemaDeLogin.controller to javafx.fxml;

    opens br.edu.ufersa.SistemaDeLogin.model.entities to javafx.base, javafx.fxml;

    opens br.edu.ufersa.SistemaDeLogin.view to javafx.fxml;

    exports br.edu.ufersa.SistemaDeLogin.view;
}