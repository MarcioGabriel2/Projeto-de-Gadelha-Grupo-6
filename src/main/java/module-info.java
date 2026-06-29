module br.edu.ufersa.SistemaDeLogin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens br.edu.ufersa.SistemaDeLogin.controller to javafx.fxml;
    opens br.edu.ufersa.SistemaDeLogin.view to javafx.fxml;

    exports br.edu.ufersa.SistemaDeLogin.controller;
    exports br.edu.ufersa.SistemaDeLogin.view;
}
