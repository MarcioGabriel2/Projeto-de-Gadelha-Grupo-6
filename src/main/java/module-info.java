module SistemaSupermercado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Permite que o JavaFX leia as suas telas e controladores
    opens org.example to javafx.fxml;
    exports br.edu.ufersa.SistemaDeLogin.view;
    opens br.edu.ufersa.SistemaDeLogin.controller to javafx.fxml;
    // Exporta o pacote principal para o Java conseguir rodar o projeto
    exports org.example;
}
