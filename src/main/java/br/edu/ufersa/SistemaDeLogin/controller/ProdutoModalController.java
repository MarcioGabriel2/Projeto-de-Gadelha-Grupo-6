package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

public class ProdutoModalController {

    @FXML private TextField txtMarca;
    @FXML private TextField txtCodigoBarras;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPreco;
    @FXML private ComboBox<String> cbTipo;

    @FXML
    public void initialize() {
        // Depois você pode adicionar itens no ComboBox aqui, ex:
        // cbTipo.getItems().addAll("Bebidas", "Limpeza", "Grãos");
    }

    @FXML
    public void handleCadastrar(ActionEvent event) {
        // Futuramente, aqui você vai pegar os textos digitados e salvar no Banco de Dados

        System.out.println("Produto cadastrado com sucesso!");

        // Após salvar, volta para a tabela de produtos
        Navegacao.trocarTela("/Telas_fxml/Gerenciando Produtos 1 Final.fxml", event);
    }

    @FXML
    private void handleFechar(ActionEvent event) {
        // O botão do X para fechar a janela e voltar
        Navegacao.trocarTela("/Telas_fxml/Gerenciando Produtos 1 Final.fxml", event);
    }
}