package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GerenciamentoTiposController {
    @FXML
    public void initialize() { }

    @FXML
    public void handleIrParaDashboard(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Dashboard Final.fxml", event);
    }

    @FXML
    public void handleIrParaProdutos(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Gerenciando Produtos 1 Final.fxml", event);
    }

    @FXML
    public void handleNovoTipo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Telas_fxml/Criando novo Tipo.fxml"));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            modalStage.setScene(scene);

            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modalStage.initOwner(parentStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.TRANSPARENT);

            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}