package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class DashboardController {

    @FXML
    private void handleIrParaProdutos(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/Gerenciando Produtos 1 Final.fxml", event);
    }

    @FXML
    private void handleIrParaVendas(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/TelaDeVendas.fxml", event);
    }

    @FXML
    private void handleIrParaCompras(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/TelaDeCompras.fxml", event);
    }

    @FXML
    private void handleSair(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/Tela de Login 1 Final.fxml", event);
    }
}