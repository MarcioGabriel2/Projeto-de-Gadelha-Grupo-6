package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GerenciamentoProdutosController {
    @FXML
    public void initialize() {
        // Aqui no futuro será feito o link das colunas com o banco de dados
    }

    @FXML
    public void handleIrParaDashboard(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Dashboard Final.fxml", event);
    }

    @FXML
    public void handleNovoProduto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Telas_fxml/CriarProduto.fxml"));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            Scene scene = new Scene(root);

            // O grande segredo: Diz para o Java deixar o fundo da janela invisível
            scene.setFill(Color.TRANSPARENT);
            modalStage.setScene(scene);

            // Pega a janela atual (pai) para o modal nascer exatamente em cima dela
            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modalStage.initOwner(parentStage);

            // Trava a tela de trás (Modality) e tira a barra feia do Windows (Style)
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.TRANSPARENT);

            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleApagarProduto() {
        System.out.println("Executando DELETE no banco de dados...");
    }

    @FXML
    private void handleSair(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
    }
    @FXML
    public void handleIrParaTipos(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Gerenciando Produtos (Tipos).fxml", event);
    }
}