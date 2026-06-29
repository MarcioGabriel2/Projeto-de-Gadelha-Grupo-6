/*

package br.edu.ufersa.SistemaDeLogin.view;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // OPÇÃO 1: Iniciar o fluxo normal pela Tela de Login
            String fxmlPath = "/Telas_fxml/1. Tela de Login 1.fxml";

            // OPÇÃO 2: Se quiser testar DIRETO a tela de compras que corrigimos,
            // comente a linha de cima (coloque //) e tire o comentário da linha de baixo:
            // String fxmlPath = "/Telas_fxml/15. Tela de Compras.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            primaryStage.setTitle("Sistema de Supermercado - UFERSA");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Mantém o tamanho fixo do design do Figma
            primaryStage.show();

        } catch(Exception e) {
            System.err.println("Erro ao carregar a tela inicial do sistema!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

*/
