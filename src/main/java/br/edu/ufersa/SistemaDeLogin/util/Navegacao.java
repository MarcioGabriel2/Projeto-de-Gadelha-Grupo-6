package br.edu.ufersa.SistemaDeLogin.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Navegacao {
    public static void trocarTela(String fxmlPath, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Navegacao.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
        }
    }
}