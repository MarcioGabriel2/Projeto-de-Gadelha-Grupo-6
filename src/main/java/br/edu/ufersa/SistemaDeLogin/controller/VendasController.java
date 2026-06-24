package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class VendasController {

    // --- Navegação do Menu Superior ---
    @FXML
    private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/Dashboard Final.fxml", event); }

    @FXML
    private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/Gerenciando Produtos 1 Final.fxml", event); }

    @FXML
    private void handleIrParaVendas(ActionEvent event) {
        // Já está na secção de Vendas
    }

    @FXML
    private void handleIrParaCompras(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/Tela de Compras.fxml", event); }

    @FXML
    private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event); }

    // --- Ações da Tela de Vendas ---
    @FXML
    private void handleAdicionarNota() {
        System.out.println("Produto adicionado à nota de venda!");
    }

    @FXML
    private void handleFinalizarVenda() {
        System.out.println("Venda finalizada com sucesso e guardada na base de dados!");
    }

    @FXML
    private void handleCancelarNota() {
        System.out.println("Nota de venda cancelada. Limpando os campos...");
    }

    // Ações extra para as Telas de Vendas 3 e 4
    @FXML
    private void handleEditarItem() {
        System.out.println("Editando a quantidade/valor do item selecionado...");
    }

    @FXML
    private void handleExcluirItem() {
        System.out.println("Item removido da nota atual.");
    }

    @FXML
    private void handleVisualizarNota() {
        System.out.println("Abrindo detalhes da nota de venda (ícone do olho)...");
    }
}