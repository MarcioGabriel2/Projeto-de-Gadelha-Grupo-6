package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.TipoDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import br.edu.ufersa.SistemaDeLogin.util.Sessao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

public class GerenciamentoTiposController {

    @FXML private TableView<Tipo> tabelaTipos;
    @FXML private TableColumn<Tipo, String> colNome;
    @FXML private TableColumn<Tipo, String> colFormaVenda;
    @FXML private TableColumn<Tipo, Void> colAcoes;
    @FXML private Label lblUsuarioNome;
    @FXML private Label lblUsuarioCargo;
    @FXML private Button btnNovoTipo;

    private TipoDAO tipoDAO = new TipoDAO();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarTiposDoBanco();
        configurarPerfilEPermissoes();
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colFormaVenda.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormaVenda()));
        configurarColunaAcoes();
    }

    private void configurarColunaAcoes() {
        Callback<TableColumn<Tipo, Void>, TableCell<Tipo, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Tipo, Void> call(final TableColumn<Tipo, Void> param) {
                return new TableCell<>() {
                    private final Button btnEditar = new Button("📝");
                    private final Button btnDeletar = new Button("🗑️");
                    private final HBox container = new HBox(10, btnEditar, btnDeletar);

                    {
                        btnEditar.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-font-size: 14px;");
                        btnDeletar.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-font-size: 14px;");
                        container.setStyle("-fx-alignment: center;");

                        btnEditar.setOnAction(event -> {
                            Tipo tipoSelecionado = getTableView().getItems().get(getIndex());
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Telas_fxml/10. Editando Tipo.fxml"));
                                Parent root = loader.load();
                                EditarTipoController controller = loader.getController();
                                controller.preencherCampos(tipoSelecionado);
                                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.show();
                            } catch (Exception e) {
                                System.out.println("Erro ao abrir a tela de edição de tipo: " + e.getMessage());
                                e.printStackTrace();
                            }
                        });

                        btnDeletar.setOnAction(event -> {
                            Tipo tipoSelecionado = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente excluir o tipo " + tipoSelecionado.getNome() + "?", ButtonType.YES, ButtonType.NO);
                            alert.setHeaderText("Excluir Categoria");
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                try {
                                    tipoDAO.deletar(tipoSelecionado.getId());
                                    carregarTiposDoBanco();
                                } catch (Exception e) {
                                    Alert alertErro = new Alert(Alert.AlertType.ERROR, "Não foi possível excluir. Talvez existam produtos vinculados a este tipo.\nErro: " + e.getMessage());
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
                                    exibirAvisoAcessoNegado("editar ou excluir tipos de produtos");
                                });
                                setGraphic(btnCadeado);
                            } else {
                                setGraphic(container);
                            }
                        }
                    }
                };
            }
        };
        colAcoes.setCellFactory(cellFactory);
    }

    private void carregarTiposDoBanco() {
        List<Tipo> listaBanco = tipoDAO.listarTodos();
        ObservableList<Tipo> tiposObservable = FXCollections.observableArrayList(listaBanco);
        tabelaTipos.setItems(tiposObservable);
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

                // Transforma o botão perfeitamente na caixa cinza bloqueada
                btnNovoTipo.setOpacity(1.0);
                btnNovoTipo.setText("🔒 Restrito");
                btnNovoTipo.setStyle("-fx-background-color: rgba(209, 213, 219, 0.9); -fx-text-fill: #4B5563; -fx-background-radius: 6px; -fx-font-weight: bold; -fx-cursor: hand; -fx-alignment: center;");

                btnNovoTipo.setOnAction((ActionEvent event) -> {
                    exibirAvisoAcessoNegado("cadastrar novos tipos de produto");
                });
            } else {
                lblUsuarioCargo.setStyle("-fx-background-color: #E2E0FA; -fx-background-radius: 15px; -fx-padding: 2px 10px; -fx-font-weight: bold;");
                lblUsuarioCargo.setTextFill(Color.web("#432dd7"));

                btnNovoTipo.setOpacity(0.0);
                btnNovoTipo.setText("+ Novo Tipo");
                btnNovoTipo.setStyle("-fx-cursor: hand;");
                btnNovoTipo.setOnAction(this::handleNovoTipo);
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

    @FXML private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event); }
    @FXML private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event); }
    @FXML private void handleIrParaVendas(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/11. Tela de Vendas.fxml", event); }
    @FXML private void handleIrParaCompras(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/15. Tela de Compras.fxml", event); }
    @FXML private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event); }
    @FXML private void handleNovoTipo(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/9. Criando novo Tipo.fxml", event); }
}