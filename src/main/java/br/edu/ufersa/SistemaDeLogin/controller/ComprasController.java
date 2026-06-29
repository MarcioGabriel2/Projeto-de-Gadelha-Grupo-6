package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.*;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import br.edu.ufersa.SistemaDeLogin.model.entities.ItemNota;
import br.edu.ufersa.SistemaDeLogin.model.entities.Nota;
import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.model.service.NotaService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import br.edu.ufersa.SistemaDeLogin.util.Sessao;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

import java.util.List;

public class ComprasController {

    @FXML private Label lblUsuarioNome;
    @FXML private Label lblUsuarioCargo;

    @FXML private ComboBox<String> cbProduto;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPrecoUnitario;

    @FXML private TableView<ItemNota> tabelaItensCompra;
    @FXML private TableColumn<ItemNota, String> colProdutoCompra;
    @FXML private TableColumn<ItemNota, Integer> colQtdCompra;
    @FXML private TableColumn<ItemNota, String> colPrecoCompra;
    @FXML private TableColumn<ItemNota, String> colSubtotalCompra;

    @FXML private Label lblQtdItens;
    @FXML private Label lblQtdTotal;
    @FXML private Label lblValorTotal;

    // Dependências e Estado
    private final NotaService notaService = new NotaService(new NotaDAO());
    private List<Produto> listaProdutosBanco;

    // Instâncias de persistência
    private final DAOFactory daoFactory = new SqlDAOFactory();
    private final ProdutoDAO produtoDAO = daoFactory.criarProdutoDAO();
    private final NotaDAO notaDAO = daoFactory.criarNotaDAO();

    private Nota notaCompra = new Nota();
    private ObservableList<ItemNota> obsItensCompra = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarPerfilUsuario();
        configurarTabelaEInputs();
        carregarProdutosNoComboBox();
    }

    private void carregarProdutosNoComboBox() {
        try {
            listaProdutosBanco = produtoDAO.listarTodos();
            for (Produto p : listaProdutosBanco) {
                cbProduto.getItems().add(p.getMarca());
            }

            // Listener: Quando selecionar um produto, preenche o preço unitário com o preço atual dele
            cbProduto.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    for (Produto p : listaProdutosBanco) {
                        if (p.getMarca().equals(newVal)) {
                            txtPrecoUnitario.setText(String.valueOf(p.getPreco()));
                            break;
                        }
                    }
                }
            });
        } catch (Exception e) {
            exibirAlerta(AlertType.ERROR, "Erro", "Não foi possível carregar os produtos do banco.");
        }
    }

    private void configurarTabelaEInputs() {
        // Bloqueia digitação de letras na quantidade
        txtQuantidade.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) { txtQuantidade.setText(newVal.replaceAll("[^\\d]", "")); }
        });

        // Configura as colunas da Tabela
        colProdutoCompra.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().getMarca()));
        colQtdCompra.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantidade()));
        colPrecoCompra.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("R$ %.2f", cellData.getValue().getValorUnitario())));
        colSubtotalCompra.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("R$ %.2f", cellData.getValue().calcularSubtotal())));

        tabelaItensCompra.setItems(obsItensCompra);
    }

    @FXML
    private void handleAdicionarCompra() {
        String marcaSelecionada = cbProduto.getValue();
        String qtdTexto = txtQuantidade.getText();
        String precoTexto = txtPrecoUnitario.getText().replace(",", "."); // Aceita vírgula ou ponto

        if (marcaSelecionada == null || qtdTexto.isEmpty() || precoTexto.isEmpty()) {
            exibirAlerta(AlertType.WARNING, "Campos Incompletos", "Selecione um produto, digite a quantidade e o custo unitário.");
            return;
        }

        try {
            int qtd = Integer.parseInt(qtdTexto);
            double precoUnitario = Double.parseDouble(precoTexto);

            if (qtd <= 0 || precoUnitario < 0) {
                exibirAlerta(AlertType.WARNING, "Valores Inválidos", "A quantidade deve ser maior que zero e o preço não pode ser negativo.");
                return;
            }

            // Acha o produto completo na lista que já puxamos do banco
            Produto produtoSelecionado = null;
            for (Produto p : listaProdutosBanco) {
                if (p.getMarca().equals(marcaSelecionada)) {
                    produtoSelecionado = p;
                    break;
                }
            }

            // Lógica para JUNTAR produtos repetidos na mesma nota
            boolean jaExiste = false;
            for (ItemNota item : notaCompra.getItens()) {
                if (item.getProduto().getId() == produtoSelecionado.getId()) {
                    item.setQuantidade(item.getQuantidade() + qtd);
                    item.setValorUnitario(precoUnitario); // Atualiza pelo custo mais recente digitado
                    jaExiste = true;
                    break;
                }
            }

            if (!jaExiste) {
                ItemNota novoItem = new ItemNota(produtoSelecionado, qtd, precoUnitario);
                notaCompra.adicionarItem(novoItem);
            }

            // Atualiza tela
            obsItensCompra.setAll(notaCompra.getItens());
            tabelaItensCompra.refresh();
            atualizarResumo();

            // Limpa os campos para o próximo item
            cbProduto.getSelectionModel().clearSelection();
            txtQuantidade.setText("0");
            txtPrecoUnitario.clear();

        } catch (NumberFormatException e) {
            exibirAlerta(AlertType.ERROR, "Erro", "Digite valores numéricos válidos para quantidade e preço.");
        }
    }

    @FXML
    public void handleExcluirItem(ActionEvent event) {
        ItemNota itemSelecionado = tabelaItensCompra.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            notaCompra.getItens().remove(itemSelecionado);
            obsItensCompra.setAll(notaCompra.getItens());
            atualizarResumo();
        } else {
            exibirAlerta(AlertType.WARNING, "Nenhum item selecionado", "Selecione um item na tabela para remover.");
        }
    }

    @FXML
    private void handleFinalizarCompra() {
        if (notaCompra.getItens().isEmpty()) {
            exibirAlerta(AlertType.WARNING, "Nota Vazia", "Adicione produtos antes de finalizar a compra.");
            return;
        }

        try {
            // Calcula o total da nota antes de mandar para o banco
            notaCompra.calcularTotal();

            // CORRIGIDO: O DAO faz a mágica toda sozinho (salva itens, soma o estoque e a nota)
            notaDAO.registrarNota(notaCompra, "COMPRA");

            exibirAlerta(AlertType.INFORMATION, "Sucesso", "Compra registrada e estoque atualizado com sucesso!");

            // Limpa a tela e zera a nota atual
            handleCancelarNota();

        } catch (Exception e) {
            exibirAlerta(AlertType.ERROR, "Erro", "Falha ao registrar compra no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelarNota() {
        notaCompra = new Nota();
        obsItensCompra.clear();
        atualizarResumo();
        cbProduto.getSelectionModel().clearSelection();
        txtQuantidade.setText("0");
        txtPrecoUnitario.clear();
    }

    private void atualizarResumo() {
        int qtdItensDistintos = notaCompra.getItens().size();
        int qtdTotalProdutos = 0;

        for (ItemNota item : notaCompra.getItens()) {
            qtdTotalProdutos += item.getQuantidade();
        }

        lblQtdItens.setText(String.valueOf(qtdItensDistintos));
        lblQtdTotal.setText(String.valueOf(qtdTotalProdutos));
        lblValorTotal.setText(String.format("R$ %.2f", notaCompra.calcularTotal()));
    }

    private void exibirAlerta(AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void carregarPerfilUsuario() {
        Funcionario usuarioLogado = Sessao.getUsuarioLogado();
        if (usuarioLogado != null) {
            lblUsuarioNome.setText(usuarioLogado.getNome());
            String cargo = usuarioLogado.getTipo();
            lblUsuarioCargo.setText(cargo);

            if (cargo != null && cargo.equalsIgnoreCase("Funcionário")) {
                lblUsuarioCargo.setStyle("-fx-background-color: #E0F2FE; -fx-background-radius: 15px; -fx-padding: 2px 10px; -fx-font-weight: bold;");
                lblUsuarioCargo.setTextFill(Color.web("#0369A1"));
            } else {
                lblUsuarioCargo.setStyle("-fx-background-color: #E2E0FA; -fx-background-radius: 15px; -fx-padding: 2px 10px; -fx-font-weight: bold;");
                lblUsuarioCargo.setTextFill(Color.web("#432dd7"));
            }
        }
    }

    @FXML
    public void handleEditarItem(ActionEvent event) {
        // Pega o item que o usuário selecionou na tabela
        ItemNota itemSelecionado = tabelaItensCompra.getSelectionModel().getSelectedItem();

        if (itemSelecionado != null) {
            // Joga os valores de volta para os campos de preenchimento
            cbProduto.setValue(itemSelecionado.getProduto().getMarca());
            txtQuantidade.setText(String.valueOf(itemSelecionado.getQuantidade()));
            txtPrecoUnitario.setText(String.valueOf(itemSelecionado.getValorUnitario()));

            // Remove o item temporariamente da nota e da tabela
            notaCompra.getItens().remove(itemSelecionado);
            obsItensCompra.setAll(notaCompra.getItens());

            // Atualiza o resumo
            atualizarResumo();
        } else {
            exibirAlerta(AlertType.WARNING, "Ação Inválida", "Selecione um produto na tabela primeiro para poder editá-lo.");
        }
    }

    @FXML private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event); }
    @FXML private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event); }
    @FXML private void handleIrParaVendas(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/11. Tela de Vendas.fxml", event); } // Confirme o nome!
    @FXML private void handleIrParaCompras(ActionEvent event) { /* Contexto Atual */ }
    @FXML private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event); }
}