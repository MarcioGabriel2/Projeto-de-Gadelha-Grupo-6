package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class GerenciamentoTiposController {

    @FXML
    private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/Dashboard Final.fxml", event); }

    @FXML
    private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/Gerenciando Produtos 1 Final.fxml", event); }

    @FXML
    private void handleIrParaVendas(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/TelaDeVendas.fxml", event); }

    @FXML
    private void handleIrParaCompras(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/Tela de Compras.fxml", event); }

    @FXML
    private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event); }

    @FXML
    private void handleNovoTipo(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Criando novo Tipo.fxml", event);
    }

    @FXML
    private void handleEditarTipo() { System.out.println("Abrindo modal para editar Tipo..."); }

    @FXML
    private void handleApagarTipo() { System.out.println("Tipo apagado da base de dados!"); }
}