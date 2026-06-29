package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;

public class ComprasController {
    @FXML
    private TextField txtBuscaProduto;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TextField txtPrecoCusto;

    @FXML
    private TableView<?> tabelaItens;

    @FXML
    public void initialize() {
        // Futuramente: Lógica para carregar os dados nas colunas da tabela
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
    public void handleIrParaHistorico(ActionEvent event) {
        // Navega para a aba de Histórico
        Navegacao.trocarTela("/Telas_fxml/Tela de Compras 2.fxml", event);
    }

    @FXML
    public void handleSair(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
    }

    @FXML
    public void handleAdicionarItem(ActionEvent event) {
        System.out.println("Adicionando produto à compra: " + txtBuscaProduto.getText());
        // Lógica para listar o produto na tabela
    }

    @FXML
    public void handleCancelarCompra(ActionEvent event) {
        System.out.println("Compra cancelada. Limpando os campos...");
        txtBuscaProduto.clear();
        txtQuantidade.clear();
        txtPrecoCusto.clear();
        // Lógica para limpar a TableView
    }

    @FXML
    public void handleFinalizarCompra(ActionEvent event) {
        System.out.println("Compra finalizada e estoque atualizado com sucesso!");
        // Lógica para salvar a entrada no Banco de Dados
    }
}