package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.DAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.SqlDAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.ProdutoDAO;
import br.edu.ufersa.SistemaDeLogin.model.DAO.NotaDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import br.edu.ufersa.SistemaDeLogin.util.Sessao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;

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

    @FXML private Label lblUsuarioNome;
    @FXML private Label lblUsuarioCargo;

    // Instâncias de persistência utilizando a Fábrica
    private final DAOFactory daoFactory = new SqlDAOFactory();
    private final ProdutoDAO produtoDAO = daoFactory.criarProdutoDAO();
    private final NotaDAO notaDAO = daoFactory.criarNotaDAO();

    @FXML
    public void initialize() {
        carregarPerfilUsuario();
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

    @FXML private void handleIrParaVendas(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/11. Tela de Vendas.fxml", event);
    }

    @FXML
    private void handleIrParaCompras(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/15. Tela de Compras.fxml", event);
    }

    @FXML
    private void handleSair(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event);
    }

    private void carregarPerfilUsuario() {
        Funcionario usuarioLogado = Sessao.getUsuarioLogado();

        if (usuarioLogado != null) {
            lblUsuarioNome.setText(usuarioLogado.getNome());
            String cargo = usuarioLogado.getTipo();
            lblUsuarioCargo.setText(cargo);

            // Ajusta as cores do "badge" dependendo se é Gerente ou Funcionário
            if (cargo != null && cargo.equalsIgnoreCase("Funcionário")) {
                lblUsuarioCargo.setStyle("-fx-background-color: #E0F2FE; -fx-background-radius: 15px; -fx-padding: 2px 10px; -fx-font-weight: bold;");
                lblUsuarioCargo.setTextFill(Color.web("#0369A1"));
            } else {
                lblUsuarioCargo.setStyle("-fx-background-color: #E2E0FA; -fx-background-radius: 15px; -fx-padding: 2px 10px; -fx-font-weight: bold;");
                lblUsuarioCargo.setTextFill(Color.web("#432dd7"));
            }
        }
    }

}