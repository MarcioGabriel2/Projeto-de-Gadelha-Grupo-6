package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class ComprasController {

    @FXML
    private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event); }

    @FXML
    private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event); }

    @FXML
    private void handleIrParaVendas(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/TelaDeVendas.fxml", event); }

    @FXML
    private void handleIrParaCompras(ActionEvent event) { /* Contexto Atual */ }

    @FXML
    private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event); }

    @FXML
    private void handleAdicionarCompra() { System.out.println("Agregando item à lista de compras de fornecedor..."); }

    @FXML
    private void handleFinalizarCompra() { System.out.println("Estoque atualizado e nota de compra fechada."); }

    @FXML
    private void handleCancelarNota() { System.out.println("Pedido de compra cancelado."); }

    @FXML
    private void handleEditarItem() { System.out.println("Modificando valores de custo do item..."); }

    @FXML
    public void handleExcluirItem(ActionEvent event) {
    }
}