package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.DAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.SqlDAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.model.service.ProdutoService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    @FXML private TableColumn<Produto, Void> colAcoes; // Injetando a nova coluna

    private DAOFactory daoFactory = new SqlDAOFactory();
    private ProdutoService produtoService = new ProdutoService(daoFactory.criarProdutoDAO());

    @FXML
    public void initialize() {
        configurarTabela();
        carregarProdutosDoBanco();
    }

    private void configurarTabela() {
        // 1. Colunas de Texto (esperam String)
        colMarca.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMarca())
        );
        colCodigo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCodigoBarras())
        );
        colTipo.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTipo() != null) {
                return new SimpleStringProperty(cellData.getValue().getTipo().getNome());
            }
            return new SimpleStringProperty("");
        });

        // 2. Coluna de Quantidade (espera Double)
        // Usamos SimpleObjectProperty<Double> para envelopar o número perfeitamente
        colEstoque.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getQuantidadeEstoque())
        );

        // 3. Coluna de Preço (espera Double)
        colPreco.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPreco())
        );

        // Formatação visual para a coluna de Preço (exibir com R$) sem quebrar o tipo Double
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

                        // Ação do Botão Editar (Ainda vamos programar a tela de edição depois)
                        // Localize essa ação dentro de configurarColunaAcoes() no GerenciamentoProdutosController.java
                        btnEditar.setOnAction((ActionEvent event) -> {
                            Produto produtoSelecionado = getTableView().getItems().get(getIndex());

                            try {
                                // Carrega o FXML manualmente para acessar o Controller dele
                                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/Telas_fxml/7. Editando produto.fxml"));
                                javafx.scene.Parent root = loader.load();

                                // Pega a instância do controller da tela de edição
                                EditarProdutoController controller = loader.getController();

                                // Envia o produto da linha selecionada para preencher os inputs!
                                controller.preencherCampos(produtoSelecionado);

                                // Troca o cenário visual usando as classes agora importadas corretamente
                                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (Exception e) {
                                System.out.println("Erro ao abrir a tela de edição: " + e.getMessage());
                                e.printStackTrace();
                            }
                        });

                        // ==========================================================
                        // AQUI ESTÁ O PASSO 2 EXPLICADO E CORRIGIDO:
                        // ==========================================================
                        btnDeletar.setOnAction((ActionEvent event) -> {
                            // 1. Pega o produto que está exatamente na linha clicada
                            Produto produtoSelecionado = getTableView().getItems().get(getIndex());

                            // 2. Abre a caixinha de pergunta na tela
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente excluir o produto " + produtoSelecionado.getMarca() + "?", ButtonType.YES, ButtonType.NO);
                            alert.setHeaderText("Excluir Registro");
                            alert.showAndWait();

                            // 3. Se o usuário clicou em SIM/YES:
                            // Procure por essa linha dentro do btnDeletar.setOnAction no GerenciamentoProdutosController:
                            if (alert.getResult() == ButtonType.YES) {
                                try {
                                    // Chamando o novo método passando o id real do banco!
                                    daoFactory.criarProdutoDAO().deletar(produtoSelecionado.getId());

                                    System.out.println("Apagando do banco o produto de ID: " + produtoSelecionado.getId());
                                    carregarProdutosDoBanco(); // Recarrega a tabela limpa

                                } catch (Exception e) {
                                    Alert alertErro = new Alert(Alert.AlertType.ERROR, "Erro ao excluir do banco de dados: " + e.getMessage());
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
                            setGraphic(container);
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

    // ==========================================
    // NAVEGAÇÃO
    // ==========================================
    @FXML
    private void handleSubmenuTipos(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/TelaDeTipos.fxml", event);
    }

    @FXML
    private void handleCriarNovoProduto(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/6. Criando novo Produto.fxml", event);
    }

    @FXML
    private void handleIrParaDashboard(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event);
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