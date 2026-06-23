package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class GerenciamentoProdutosController {
    @FXML
    private void handleSubmenuProdutos() {
        System.out.println("Exibindo a lista de Produtos na tabela...");
    }

    @FXML
    private void handleSubmenuTipos() {
        System.out.println("Exibindo a lista de Categorias/Tipos na tabela...");
    }

    @FXML
    private void handleEditarProduto(ActionEvent event) {
        System.out.println("Abrindo modal ou tela para editar dados do produto...");
        Navegacao.trocarTela("/Telas fxml/CriarProduto.fxml", event);
    }

    @FXML
    private void handleApagarProduto() {
        System.out.println("Executando DELETE no banco de dados...");
    }

    @FXML
    private void handleIrParaDashboard(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/Dashboard Final.fxml", event);
    }

    @FXML
    private void handleSair(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/Tela de Login 1 Final.fxml", event);
    }
}