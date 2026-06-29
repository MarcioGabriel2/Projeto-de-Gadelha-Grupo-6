package br.edu.ufersa.SistemaDeLogin.view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class testandoTela extends Application {
    public void start(Stage primaryStage) throws Exception {
        Label mensagem = new Label();
//       mensagem.setTextFill(Color.RED);
//       tirando as // do comentário acima e do import, é pra exibir o texto na cor vermelha
        mensagem.setText("                                                       Marcio Gabriel Silva de Moura");
//       coloquei um espaço extra no texto, pra que nessa resolução de tela o texto fique bem centralizado (mas só em tela pequena, na tela cheia fica bagunçado)
        Scene cena = new Scene(mensagem, 500, 200);
        primaryStage.setTitle("Usuario");
        primaryStage.setScene(cena);
        primaryStage.show();


    }
    public static void main (String args[]) {
        launch();
    }

}