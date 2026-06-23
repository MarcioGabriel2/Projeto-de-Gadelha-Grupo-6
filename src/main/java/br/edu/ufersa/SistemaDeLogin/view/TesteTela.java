package br.edu.ufersa.SistemaDeLogin.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TesteTela extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Ajuste o caminho abaixo conforme a pasta que definimos antes
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Telas fxml/Tela de Login 1 Final.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Teste da Tela de Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}