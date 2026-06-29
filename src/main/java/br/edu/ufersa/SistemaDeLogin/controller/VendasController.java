package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;

public class VendasController {
    @FXML
    private TextField txtBuscaProduto;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TableView<?> tabelaItens;

    @FXML
    public void initialize() {
        // Futuramente: Configurar colunas da tabela e carregar sugestões de produtos
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
    public void handleIrParaHistorico(ActionEvent event) {
        // Substitua pelo nome exato do seu FXML de histórico de vendas!
        Navegacao.trocarTela("/Telas_fxml/Tela de Vendas 2.fxml", event);
    }

    @FXML
    public void handleSair(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
    }

    @FXML
    public void handleAdicionarItem(ActionEvent event) {
        System.out.println("Adicionando produto: " + txtBuscaProduto.getText());
        // Lógica para puxar o produto do banco e jogar na TableView
    }

    @FXML
    public void handleCancelarVenda(ActionEvent event) {
        System.out.println("Venda cancelada. Limpando a nota...");
        txtBuscaProduto.clear();
        txtQuantidade.clear();
        // Lógica para limpar a TableView e zerar o Total
    }

    @FXML
    public void handleFinalizarVenda(ActionEvent event) {
        System.out.println("Venda finalizada com sucesso!");
        // Lógica para salvar a venda no Banco de Dados e gerar a Nota Fiscal
    }
}