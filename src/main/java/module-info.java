module SisLogin3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Se estiver usando banco de dados

    // Isso aqui é o que resolve o seu erro:
    opens br.edu.ufersa.SistemaDeLogin.controller to javafx.fxml;

    // Provavelmente você também vai precisar abrir o pacote da View:
    opens br.edu.ufersa.SistemaDeLogin.view to javafx.fxml;

    exports br.edu.ufersa.SistemaDeLogin.view;
}