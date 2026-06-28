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

            String fxmlPath = "/Telas_fxml/1. Tela de Login 1.fxml";



            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

           
            Scene scene = new Scene(root);

            primaryStage.setTitle("Sistema de Supermercado - UFERSA");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Impede de redimensionar e quebrar o layout do Figma
            primaryStage.show();

            System.out.println("Aplicação inicializada com sucesso usando a tela: " + fxmlPath);

        } catch(Exception e) {
            System.err.println("Erro crítico ao carregar o arquivo FXML. Verifique os caminhos e maiúsculas/minúsculas!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}