module SistemaSupermercado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Permite que o JavaFX leia as suas telas e controladores
    opens org.example to javafx.fxml;
    opens resources.Telas_fxml to javafx.fxml;

    // Exporta o pacote principal para o Java conseguir rodar o projeto
    exports org.example;
}
