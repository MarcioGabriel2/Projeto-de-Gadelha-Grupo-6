package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.DAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.SqlDAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.model.service.ProdutoService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import br.edu.ufersa.SistemaDeLogin.util.Sessao;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.List;

public class GerenciamentoProdutosController {

    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, String> colMarca;
    @FXML private TableColumn<Produto, String> colCodigo;
    @FXML private TableColumn<Produto, String> colTipo;
    @FXML private TableColumn<Produto, Double> colEstoque;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, Void> colAcoes;

    @FXML private Label lblUsuarioNome;
    @FXML private Label lblUsuarioCargo;
    @FXML private Button btnNovoProduto;

    private DAOFactory daoFactory = new SqlDAOFactory();
    private ProdutoService produtoService = new ProdutoService(daoFactory.criarProdutoDAO());

    @FXML
    public void initialize() {
        configurarTabela();
        carregarProdutosDoBanco();
        configurarPerfilEPermissoes();
    }

    private void configurarPerfilEPermissoes() {
        Funcionario usuarioLogado = Sessao.getUsuarioLogado();

        if (usuarioLogado != null) {
            lblUsuarioNome.setText(usuarioLogado.getNome());
            String cargo = usuarioLogado.getTipo();
            lblUsuarioCargo.setText(cargo);

            if (cargo != null && cargo.equalsIgnoreCase("Funcionário")) {
                lblUsuarioCargo.setStyle("-fx-background-color: #E0F2FE; -fx-background-radius: 15px; -fx-padding: 2px 10px; -fx-font-weight: bold;");
                lblUsuarioCargo.setTextFill(Color.web("#0369A1"));

                btnNovoProduto.setOpacity(1.0);
                btnNovoProduto.setText("🔒 Restrito");
                btnNovoProduto.setStyle("-fx-background-color: rgba(209, 213, 219, 0.9); -fx-text-fill: #4B5563; -fx-background-radius: 6px; -fx-font-weight: bold; -fx-cursor: hand; -fx-alignment: center;");

                btnNovoProduto.setOnAction((ActionEvent event) -> {
                    exibirAvisoAcessoNegado("cadastrar novos produtos");
                });
            } else {
                lblUsuarioCargo.setStyle("-fx-background-color: #E2E0FA; -fx-background-radius: 15px; -fx-padding: 2px 10px; -fx-font-weight: bold;");
                lblUsuarioCargo.setTextFill(Color.web("#432dd7"));

                btnNovoProduto.setOpacity(0.0);
                btnNovoProduto.setText("+ Novo Produto");
                btnNovoProduto.setStyle("-fx-cursor: hand;");
                btnNovoProduto.setOnAction(this::handleCriarNovoProduto);
            }
        }
    }

    private void exibirAvisoAcessoNegado(String acao) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Acesso Restrito");
        alert.setHeaderText("Operação Não Permitida");
        alert.setContentText("Desculpe, contas com o cargo de Funcionário não possuem permissão para " + acao + ".\nContate um Gerente do sistema.");
        alert.showAndWait();
    }

    private void configurarTabela() {
        colMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
        colCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigoBarras()));
        colTipo.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTipo() != null) {
                return new SimpleStringProperty(cellData.getValue().getTipo().getNome());
            }
            return new SimpleStringProperty("");
        });
        colEstoque.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantidadeEstoque()));
        colPreco.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPreco()));

        colPreco.setCellFactory(column -> new TableCell<Produto, Double>() {
            @Override
            protected void updateItem(Double preco, boolean empty) {
                super.updateItem(preco, empty);
                if (empty || preco == null) {
                    setText(null);
                } else {
                    setText(String.format("R$ %.2f", preco));
                }
            }
        });

        configurarColunaAcoes();
    }

    private void configurarColunaAcoes() {
        Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>> cellFactory = new Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>>() {
            @Override
            public TableCell<Produto, Void> call(final TableColumn<Produto, Void> param) {
                final TableCell<Produto, Void> cell = new TableCell<Produto, Void>() {

                    private final Button btnEditar = new Button("📝");
                    private final Button btnDeletar = new Button("🗑️");
                    private final HBox container = new HBox(10, btnEditar, btnDeletar);

                    {
                        btnEditar.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-font-size: 14px;");
                        btnDeletar.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-font-size: 14px;");
                        container.setStyle("-fx-alignment: center;");

                        btnEditar.setOnAction((ActionEvent event) -> {
                            Produto produtoSelecionado = getTableView().getItems().get(getIndex());
                            try {
                                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/Telas_fxml/7. Editando produto.fxml"));
                                javafx.scene.Parent root = loader.load();
                                EditarProdutoController controller = loader.getController();
                                controller.preencherCampos(produtoSelecionado);
                                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.show();
                            } catch (Exception e) {
                                System.out.println("Erro ao abrir a tela de edição: " + e.getMessage());
                                e.printStackTrace();
                            }
                        });

                        btnDeletar.setOnAction((ActionEvent event) -> {
                            Produto produtoSelecionado = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente excluir o produto " + produtoSelecionado.getMarca() + "?", ButtonType.YES, ButtonType.NO);
                            alert.setHeaderText("Excluir Registro");
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                try {
                                    daoFactory.criarProdutoDAO().deletar(produtoSelecionado.getId());
                                    carregarProdutosDoBanco();
                                } catch (Exception e) {
                                    Alert alertErro = new Alert(Alert.AlertType.ERROR, "Erro ao excluir: " + e.getMessage());
                                    alertErro.showAndWait();
                                }
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Funcionario user = Sessao.getUsuarioLogado();
                            if (user != null && user.getTipo().equalsIgnoreCase("Funcionário")) {
                                Button btnCadeado = new Button("🔒");
                                btnCadeado.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-font-size: 16px;");
                                btnCadeado.setOnAction((ActionEvent event) -> {
                                    exibirAvisoAcessoNegado("editar ou excluir produtos");
                                });
                                setGraphic(btnCadeado);
                            } else {
                                setGraphic(container);
                            }
                        }
                    }
                };
                return cell;
            }
        };

        colAcoes.setCellFactory(cellFactory);
    }

    private void carregarProdutosDoBanco() {
        List<Produto> listaBanco = daoFactory.criarProdutoDAO().listarTodos();
        ObservableList<Produto> produtosObservable = FXCollections.observableArrayList(listaBanco);
        tabelaProdutos.setItems(produtosObservable);
    }

    @FXML private void handleSubmenuTipos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/8. Gerenciamento de Tipos.fxml", event); }
    @FXML private void handleCriarNovoProduto(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/6. Criando novo Produto.fxml", event); }
    @FXML private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event); }
    @FXML private void handleIrParaVendas(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/11. Tela de Vendas.fxml", event); }
    @FXML private void handleIrParaCompras(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/15. Tela de Compras.fxml", event); }

    @FXML
    private void handleSair(ActionEvent event) {
        if (Sessao.getUsuarioLogado() != null) {
            Sessao.encerrarSessao();
        }
        Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event);
    }
}