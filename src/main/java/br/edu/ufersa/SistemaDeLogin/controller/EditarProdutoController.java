package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.DAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.SqlDAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditarProdutoController {

    @FXML private TextField txtMarca;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPreco;
    @FXML private MenuButton menuTipo;

    private final DAOFactory daoFactory = new SqlDAOFactory();
    private Produto produtoEmEdicao;

    // Método crucial para receber os dados do produto selecionado da linha da tabela
    public void preencherCampos(Produto produto) {
        this.produtoEmEdicao = produto;
        txtMarca.setText(produto.getMarca());
        txtCodigo.setText(produto.getCodigoBarras());
        txtQuantidade.setText(String.valueOf(produto.getQuantidadeEstoque()));
        txtPreco.setText(String.valueOf(produto.getPreco()));
        if (produto.getTipo() != null) {
            menuTipo.setText(produto.getTipo().getNome());
        }
    }

    @FXML
    private void handleAtualizar(ActionEvent event) {
        try {
            // Atualiza o objeto com as novas informações da tela
            produtoEmEdicao.setMarca(txtMarca.getText());
            produtoEmEdicao.setCodigoBarras(txtCodigo.getText());
            produtoEmEdicao.setPreco(Double.parseDouble(txtPreco.getText()));
            produtoEmEdicao.setQuantidadeEstoque(Double.parseDouble(txtQuantidade.getText()));

            // Executa o update usando a estrutura DAO existente
            daoFactory.criarProdutoDAO().alterar(produtoEmEdicao);

            // Retorna para a listagem principal
            Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao atualizar produto: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML private void handleCancelar(ActionEvent event) { handleIrParaProdutos(event); }
    @FXML private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event); }
    @FXML private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event); }
    @FXML private void handleIrParaVendas(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/TelaDeVendas.fxml", event); }
    @FXML private void handleIrParaCompras(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/TelaDeCompras.fxml", event); }
    @FXML private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event); }
}