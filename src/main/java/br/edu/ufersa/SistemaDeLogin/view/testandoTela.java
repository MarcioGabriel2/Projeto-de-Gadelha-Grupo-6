package br.edu.ufersa.SistemaDeLogin.view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
// import javafx.scene.paint.Color;
// Nesse import em comentário acima, ele serve pra mudar a cor do texto
public class testandoTela extends Application {
    public void start(Stage primaryStage) throws Exception {
        Label mensagem = new Label();
//       mensagem.setTextFill(Color.RED);
//       Tirando as // do comentário acima e do import, é pra exibir o texto na cor vermelha
        mensagem.setText("                                                       Marcio Gabriel Silva de Moura");
//       Coloquei um espaço extra no texto, pra que nessa resolução de tela o texto fique bem centralizado (mas só em tela pequena, na tela cheia fica bagunçado)
        Scene cena = new Scene(mensagem, 500, 200);
        primaryStage.setTitle("Usuario");
        primaryStage.setScene(cena);
        primaryStage.show();


    }
    public static void main (String args[]) {
        launch();
    }

}