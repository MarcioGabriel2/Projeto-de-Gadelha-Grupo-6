package br.edu.ufersa.SistemaDeLogin.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Certifique-se de colocar o arquivo .fxml na pasta src/main/resources/telas_fxml/
            Parent root = FXMLLoader.load(getClass().getResource("/telas_fxml/Tela de Login a1 Final.fxml"));            Scene scene = new Scene(root);

            primaryStage.setTitle("Sistema Supermercado - Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Mantém o tamanho original do Figma
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}