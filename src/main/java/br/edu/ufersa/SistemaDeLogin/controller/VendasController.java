package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.NotaDAO;
import br.edu.ufersa.SistemaDeLogin.model.DAO.ProdutoDAO;
import br.edu.ufersa.SistemaDeLogin.model.DAO.SqlProdutoDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import br.edu.ufersa.SistemaDeLogin.model.entities.ItemNota;
import br.edu.ufersa.SistemaDeLogin.model.entities.Nota;
import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.model.service.NotaService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import br.edu.ufersa.SistemaDeLogin.util.Sessao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import br.edu.ufersa.SistemaDeLogin.model.strategy.EstrategiaPagamento;
import br.edu.ufersa.SistemaDeLogin.model.strategy.PagamentoCredito;
import br.edu.ufersa.SistemaDeLogin.model.strategy.PagamentoDinheiro;
import br.edu.ufersa.SistemaDeLogin.model.strategy.PagamentoPix;

public class VendasController {

    @FXML private TextField txtPesquisarProduto;
    @FXML private TextField txtQuantidade;
    @FXML private ImageView imgFundo;
    @FXML private Button btnFinalizarVenda;
    @FXML private ComboBox<String> cbFormaPagamento;

    @FXML private TableView<ItemNota> tabelaItensNota;
    @FXML private TableColumn<ItemNota, String> colProdutoNota;
    @FXML private TableColumn<ItemNota, String> colTipoNota;
    @FXML private TableColumn<ItemNota, Integer> colQtdNota;
    @FXML private TableColumn<ItemNota, String> colPrecoNota;
    @FXML private TableColumn<ItemNota, String> colSubtotalNota;

    @FXML private TableView<?> tabelaHistorico;
    @FXML private TableColumn<?, ?> colDataHistorico;
    @FXML private TableColumn<?, ?> colTotalHistorico;
    @FXML private TableColumn<?, ?> colAcoesHistorico;

    @FXML private Label lblQtdItens;
    @FXML private Label lblQtdTotal;
    @FXML private Label lblValorTotal;
    @FXML private Label lblUsuarioNome;
    @FXML private Label lblUsuarioCargo;

    // Dependências e Estado
    private final NotaService notaService = new NotaService(new NotaDAO());
    private final ProdutoDAO produtoDAO = new SqlProdutoDAO();
    private Nota notaCorrente = new Nota();
    private ObservableList<ItemNota> obsItensNota = FXCollections.observableArrayList();
    private ContextMenu autoCompletionPopup = new ContextMenu();

    // Guardando os caminhos das duas imagens na memória
    private Image imgSemItens;
    private Image imgComItens;

    @FXML
    public void initialize() {
        carregarImagensDeFundo();
        configurarTabelaItens();
        configurarMenuSuspenso();
        atualizarResumoEInterface();
        carregarPerfilUsuario();

        if (cbFormaPagamento != null) {
            cbFormaPagamento.getItems().addAll(
                    "Dinheiro (Sem Juros)",
                    "PIX (5% de Desconto)",
                    "Crédito (2% de Juros)"
            );
            cbFormaPagamento.getSelectionModel().selectFirst();
        }
    }

    private void carregarImagensDeFundo() {
        try {
            // Como 'resources' é a raiz, começamos direto da pasta /Imagens/
            imgSemItens = new Image(getClass().getResourceAsStream("/Imagens/11_Vendas_1.png"));
            imgComItens = new Image(getClass().getResourceAsStream("/Imagens/12_Vendas_2.png"));

            if (imgSemItens == null || imgComItens == null) {
                System.out.println("Erro: Uma das imagens de vendas não foi encontrada dentro de resources/Imagens!");
            }
        } catch (Exception e) {
            System.out.println("Aviso: Falha catastrófica ao carregar imagens de fundo: " + e.getMessage());
        }
    }

    private void configurarTabelaItens() {
        colProdutoNota.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().getMarca()));
        colTipoNota.setCellValueFactory(cellData -> {
            Produto p = cellData.getValue().getProduto();
            return new SimpleStringProperty(p.getTipo() != null ? p.getTipo().getNome() : "Sem Tipo");
        });
        colQtdNota.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantidade()));
        colPrecoNota.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("R$ %.2f", cellData.getValue().getValorUnitario())));
        colSubtotalNota.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("R$ %.2f", cellData.getValue().calcularSubtotal())));

        tabelaItensNota.setItems(obsItensNota);
    }

    @FXML
    private void handleAdicionarNota(ActionEvent event) {
        String termoPesquisa = txtPesquisarProduto.getText().trim();
        String qtdTexto = txtQuantidade.getText().trim();

        if (termoPesquisa.isEmpty() || qtdTexto.isEmpty()) {
            exibirAlerta(AlertType.WARNING, "Campos vazios", "Preencha o código/nome do produto e a quantidade.");
            return;
        }

        try {
            int quantidadeInformada = Integer.parseInt(qtdTexto);
            if (quantidadeInformada <= 0) {
                exibirAlerta(AlertType.WARNING, "Quantidade Inválida", "A quantidade deve ser maior que zero.");
                return;
            }

            Produto produtoEncontrado = null;

            // 1ª TENTATIVA: Busca por Código de Barras ou Nome EXATO (Resolve o bug do Candy/DarkerCandy)
            for (Produto p : produtoDAO.listarTodos()) {
                if (p.getCodigoBarras().equals(termoPesquisa) || p.getMarca().equalsIgnoreCase(termoPesquisa)) {
                    produtoEncontrado = p;
                    break;
                }
            }

            // 2ª TENTATIVA: Se não achou exato, busca se "contém" a palavra digitada
            if (produtoEncontrado == null) {
                for (Produto p : produtoDAO.listarTodos()) {
                    if (p.getMarca().toLowerCase().contains(termoPesquisa.toLowerCase())) {
                        produtoEncontrado = p;
                        break;
                    }
                }
            }

            if (produtoEncontrado == null) {
                exibirAlerta(AlertType.WARNING, "Não Encontrado", "Nenhum produto encontrado com este código ou nome.");
                return;
            }

            // Verificação do Estoque do carrinho atual
            int qtdJaNoCarrinho = 0;
            for (ItemNota item : notaCorrente.getItens()) {
                if (item.getProduto().getId() == produtoEncontrado.getId()) {
                    qtdJaNoCarrinho += item.getQuantidade();
                }
            }

            int totalSolicitado = qtdJaNoCarrinho + quantidadeInformada;

            if (totalSolicitado > produtoEncontrado.getQuantidadeEstoque()) {
                exibirAlerta(AlertType.ERROR, "Estoque Insuficiente",
                        "Não é possível adicionar essa quantidade.\nDisponível: " + (int)produtoEncontrado.getQuantidadeEstoque() + "\nJá na nota: " + qtdJaNoCarrinho);
                return;
            }

            // JUNTAR ITENS REPETIDOS (Soma a quantidade na mesma linha)
            boolean jaExisteNaNota = false;
            for (ItemNota item : notaCorrente.getItens()) {
                if (item.getProduto().getId() == produtoEncontrado.getId()) {
                    item.setQuantidade(item.getQuantidade() + quantidadeInformada);
                    jaExisteNaNota = true;
                    break;
                }
            }

            // Se for um produto novo, cria a linha. Se não, já foi somado acima.
            if (!jaExisteNaNota) {
                ItemNota novoItem = new ItemNota(produtoEncontrado, quantidadeInformada, produtoEncontrado.getPreco());
                notaCorrente.adicionarItem(novoItem);
            }

            // Atualiza Interface e dá um "refresh" na tabela para mostrar o valor somado
            obsItensNota.setAll(notaCorrente.getItens());
            tabelaItensNota.refresh();
            atualizarResumoEInterface();

            txtPesquisarProduto.clear();
            txtQuantidade.setText("1");
            txtPesquisarProduto.requestFocus();

        } catch (NumberFormatException e) {
            exibirAlerta(AlertType.ERROR, "Erro de Formatação", "A quantidade deve ser um número inteiro válido.");
        }
    }

    @FXML
    private void handleFinalizarVenda(ActionEvent event) {
        if (notaCorrente.getItens().isEmpty()) {
            exibirAlerta(AlertType.WARNING, "Nota Vazia", "Adicione pelo menos um produto antes de finalizar a venda.");
            return;
        }

        try {
            String formaEscolhida = cbFormaPagamento.getValue();
            EstrategiaPagamento estrategia;

            if (formaEscolhida.contains("PIX")) {
                estrategia = new PagamentoPix();
            } else if (formaEscolhida.contains("Crédito")) {
                estrategia = new PagamentoCredito();
            } else {
                estrategia = new PagamentoDinheiro();
            }

            notaService.finalizarVenda(notaCorrente, estrategia);

            exibirAlerta(AlertType.INFORMATION, "Sucesso",
                    "Venda finalizada!\nValor Final: " + String.format("R$ %.2f", notaCorrente.getValorTotal()));

            handleCancelarNota(null);

        } catch (Exception e) {
            exibirAlerta(AlertType.ERROR, "Erro", "Erro ao fechar nota: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelarNota(ActionEvent event) {
        notaCorrente = new Nota();
        obsItensNota.clear();
        atualizarResumoEInterface();
        txtPesquisarProduto.clear();
        txtQuantidade.setText("1");
    }

    // Auxiliares e Controle Dinâmico

    private void atualizarResumoEInterface() {
        int qtdItensDistintos = notaCorrente.getItens().size();
        int qtdTotalProdutos = 0;

        for (ItemNota item : notaCorrente.getItens()) {
            qtdTotalProdutos += item.getQuantidade();
        }

        lblQtdItens.setText(String.valueOf(qtdItensDistintos));
        lblQtdTotal.setText(String.valueOf(qtdTotalProdutos));
        lblValorTotal.setText(String.format("R$ %.2f", notaCorrente.calcularTotal()));

        // Mágica da troca de cor do fundo/botão baseado na existência de itens
        if (qtdItensDistintos > 0) {
            if (imgFundo != null && imgComItens != null && imgFundo.getImage() != imgComItens) {
                imgFundo.setImage(imgComItens);
            }
        } else {
            if (imgFundo != null && imgSemItens != null && imgFundo.getImage() != imgSemItens) {
                imgFundo.setImage(imgSemItens);
            }
        }
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

    private void configurarMenuSuspenso() {
        txtPesquisarProduto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                autoCompletionPopup.hide();
                return;
            }

            autoCompletionPopup.getItems().clear();

            // Filtra os produtos
            for (Produto p : produtoDAO.listarTodos()) {
                if (p.getMarca().toLowerCase().contains(newValue.toLowerCase()) || p.getCodigoBarras().contains(newValue)) {
                    MenuItem item = new MenuItem(p.getMarca());
                    item.setOnAction(e -> {
                        txtPesquisarProduto.setText(p.getMarca());
                        autoCompletionPopup.hide();
                    });
                    autoCompletionPopup.getItems().add(item);
                }
            }

            // Exibe o menu se tiver resultados
            if (!autoCompletionPopup.getItems().isEmpty()) {
                autoCompletionPopup.show(txtPesquisarProduto, javafx.geometry.Side.BOTTOM, 0, 0);
            } else {
                autoCompletionPopup.hide();
            }
        });
    }

    // Navegação
    @FXML private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event); }
    @FXML private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event); }
    @FXML private void handleIrParaVendas(ActionEvent event) { /* Contexto Atual */ }
    @FXML private void handleIrParaCompras(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/15. Tela de Compras.fxml", event); }
    @FXML private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event); }
    @FXML private void handleRemoverItemSelecionado(ActionEvent event) {
        // Pega o item que o usuário clicou na tabela
        ItemNota itemSelecionado = tabelaItensNota.getSelectionModel().getSelectedItem();

        if (itemSelecionado != null) {
            // Remove da nota e atualiza a tabela
            notaCorrente.getItens().remove(itemSelecionado);
            obsItensNota.setAll(notaCorrente.getItens());

            // Recalcula os totais e atualiza a tela
            atualizarResumoEInterface();
        } else {
            exibirAlerta(AlertType.WARNING, "Ação Inválida", "Selecione um produto na tabela primeiro para poder removê-lo/trocá-lo.");
        }
    }

}