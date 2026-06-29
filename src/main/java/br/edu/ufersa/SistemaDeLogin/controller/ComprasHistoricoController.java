package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ComprasHistoricoController {
    @FXML
    private TableView<?> tabelaHistorico;

    @FXML
    public void initialize() {
        // Futuramente: Ligação das colunas para gerar PDF / Visualizar
    }

    @FXML
    public void handleIrParaDashboard(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Dashboard Final.fxml", event);
    }

    @FXML
    public void handleIrParaProdutos(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Gerenciando Produtos 1 Final.fxml", event);
    }

    @FXML
    public void handleIrParaVendas(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Vendas.fxml", event);
    }

    @FXML
    public void handleIrParaNovaCompra(ActionEvent event) {
        // Volta para a tela de adicionar produtos na compra
        Navegacao.trocarTela("/Telas_fxml/Tela de Compras.fxml", event);
    }

    @FXML
    public void handleSair(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
    }
}