package br.edu.ufersa.SistemaDeLogin.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class  TesteTela extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inicializa o teste abrindo a nova tela de Login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Telas_fxml/Tela de Login 1 Final.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Testando Sistema - Mercadinho do Sr. Pedrinho");
        primaryStage.setScene(new Scene(root));

        // Mantém a janela com tamanho fixo para preservar o design planejado
        primaryStage.setResizable(false);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}