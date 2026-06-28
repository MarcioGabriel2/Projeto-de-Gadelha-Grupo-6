package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.DAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.SqlDAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.ProdutoDAO;
import br.edu.ufersa.SistemaDeLogin.model.DAO.NotaDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.util.List;

public class DashboardController {

    @FXML private Label lblTotalProdutos;
    @FXML private Label lblSubProdutos;

    @FXML private Label lblTotalVendas;
    @FXML private Label lblSubVendas;

    @FXML private Label lblTotalCompras;
    @FXML private Label lblSubCompras;

    @FXML private Label lblSaldo;
    @FXML private Label lblSubSaldo;

    // Instâncias de persistência utilizando a Fábrica
    private final DAOFactory daoFactory = new SqlDAOFactory();
    private final ProdutoDAO produtoDAO = daoFactory.criarProdutoDAO();
    private final NotaDAO notaDAO = daoFactory.criarNotaDAO();

    @FXML
    public void initialize() {
        carregarDadosIndicadores();
    }

    private void carregarDadosIndicadores() {
        try {
            // 1. Busca todos os produtos para calcular a soma cumulativa do estoque real
            List<Produto> produtos = produtoDAO.listarTodos();

            double totalUnidadesEstoque = 0;
            for (Produto p : produtos) {
                totalUnidadesEstoque += p.getQuantidadeEstoque();
            }

            // 2. Busca os dados de contagem e financeiros das DAOs
            int totalProdutos = ((br.edu.ufersa.SistemaDeLogin.model.DAO.SqlProdutoDAO) produtoDAO).contarTiposDeProdutos();
            double totalVendas = notaDAO.buscarTotalVendas();
            double totalCompras = notaDAO.buscarTotalCompras();
            double saldo = totalVendas - totalCompras;

            // 3. Atualiza os elementos visuais na tela com os valores reais do MySQL
            lblTotalProdutos.setText(String.valueOf(totalProdutos));

            // CORRIGIDO: Agora usa o lblSubProdutos correto e exibe a soma dinâmica real
            lblSubProdutos.setText(String.format("%.0f unidades em estoque", totalUnidadesEstoque));

            lblTotalVendas.setText(String.format("R$ %.2f", totalVendas));
            lblSubVendas.setText(String.format("Hoje: R$ %.2f", totalVendas));

            lblTotalCompras.setText(String.format("R$ %.2f", totalCompras));
            lblSubCompras.setText(String.format("Hoje: R$ %.2f", totalCompras));

            // Atualiza o saldo dinamicamente caso queira exibir o valor calculado
            lblSaldo.setText(String.format("R$ %.2f", saldo));

        } catch (Exception e) {
            System.out.println("Erro ao carregar dados dos indicadores: " + e.getMessage());
        }
    }

    @FXML
    private void handleIrParaProdutos(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event);
    }

    @FXML
    private void handleIrParaVendas(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/TelaDeVendas.fxml", event);
    }

    @FXML
    private void handleIrParaCompras(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/TelaDeCompras.fxml", event);
    }

    @FXML
    private void handleSair(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event);
    }
}