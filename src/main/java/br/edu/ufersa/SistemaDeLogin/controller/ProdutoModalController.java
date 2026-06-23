package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class ProdutoModalController {

    @FXML private TextField txtMarca;
    @FXML private TextField txtCodigoBarras;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPreco;

    @FXML
    private void handleSalvarProduto(ActionEvent event) {
        System.out.println("Produto salvo/atualizado na base de dados!");
        Navegacao.trocarTela("/views/Gerenciando Produtos 1 Final.fxml", event);
    }

    @FXML
    private void handleFechar(ActionEvent event) {
        // O botão do X para fechar a janela e voltar
        Navegacao.trocarTela("/views/Gerenciando Produtos 1 Final.fxml", event);
    }
}