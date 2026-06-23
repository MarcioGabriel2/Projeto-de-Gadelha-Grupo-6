package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class ComprasController {

    // --- Navegação do Menu Superior ---
    @FXML
    private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/views/Dashboard Final.fxml", event); }

    @FXML
    private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/views/Gerenciando Produtos 1 Final.fxml", event); }

    @FXML
    private void handleIrParaVendas(ActionEvent event) { Navegacao.trocarTela("/views/Tela de Vendas.fxml", event); }

    @FXML
    private void handleIrParaCompras(ActionEvent event) {
        // Já está na secção de Compras
    }

    @FXML
    private void handleSair(ActionEvent event) { Navegacao.trocarTela("/views/Tela de Login 1 Final.fxml", event); }

    // --- Ações da Tela de Compras ---
    @FXML
    private void handleAdicionarCompra() {
        System.out.println("Produto adicionado à compra atual!");
    }

    @FXML
    private void handleFinalizarCompra() {
        System.out.println("Compra finalizada com sucesso!");
    }

    @FXML
    private void handleCancelarNota() {
        System.out.println("Nota cancelada e limpa.");
    }

    // Ações extra para a Tela de Compras 2
    @FXML
    private void handleEditarItem() {
        System.out.println("Editando o item selecionado na tabela de compras...");
    }

    @FXML
    private void handleExcluirItem() {
        System.out.println("Item removido da nota de compra atual.");
    }
}