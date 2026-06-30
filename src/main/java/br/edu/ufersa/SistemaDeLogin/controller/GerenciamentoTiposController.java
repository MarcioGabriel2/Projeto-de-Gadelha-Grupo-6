package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.TipoDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import java.util.List;

public class GerenciamentoTiposController {

    @FXML private TableView<Tipo> tabelaTipos;
    @FXML private TableColumn<Tipo, String> colunaNome;
    @FXML private TableColumn<Tipo, String> colunaFormaVenda;
    @FXML private TableColumn<Tipo, String> colunaAcoes;

    @FXML
    public void initialize() {
        // Jeito moderno e seguro que burla o bloqueio de segurança do JavaFX
        colunaNome.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));
        colunaFormaVenda.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFormaVenda()));

        // Chama o método que desenha os botões na coluna de Ações
        configurarColunaAcoes();

        carregarTabela();
    }

    private void configurarColunaAcoes() {
        Callback<TableColumn<Tipo, String>, TableCell<Tipo, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Tipo, String> call(final TableColumn<Tipo, String> param) {
                return new TableCell<>() {
                    final Button btnEditar = new Button("Editar");

                    {
                        btnEditar.setStyle("-fx-background-color: #1230f3; -fx-text-fill: white; -fx-cursor: hand;");

                        btnEditar.setOnAction(event -> {
                            Tipo tipoSelecionado = getTableView().getItems().get(getIndex());
                            abrirModalEditar(tipoSelecionado);
                        });
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnEditar);
                            // O segredo para centralizar está nesta linha abaixo:
                            setAlignment(javafx.geometry.Pos.CENTER);
                        }
                    }
                };
            }
        };
        colunaAcoes.setCellFactory(cellFactory);
    }

    private void abrirModalEditar(Tipo tipoSelecionado) {
        try {
            // ATENÇÃO: Coloque aqui o nome exato do seu arquivo FXML de editar o tipo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Telas_fxml/EditarTipo.fxml"));
            Parent root = loader.load();

// Aqui as linhas já estão "ligadas" (sem as barras na frente) para a ponte funcionar:
            EditarTipoController controller = loader.getController();
            controller.preencherDados(tipoSelecionado);

            Stage modalStage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            modalStage.setScene(scene);

            Stage parentStage = (Stage) tabelaTipos.getScene().getWindow();
            modalStage.initOwner(parentStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.TRANSPARENT);

            modalStage.showAndWait();

            // Recarrega a tabela para mostrar o nome novo caso tenha sido alterado
            carregarTabela();

        } catch (Exception e) {
            System.out.println("Erro ao abrir tela de edição: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarTabela() {
        try {
            TipoDAO dao = new TipoDAO();
            List<Tipo> listaDeTipos = dao.listarTodos();

            ObservableList<Tipo> obsTipos = FXCollections.observableArrayList(listaDeTipos);
            tabelaTipos.setItems(obsTipos);
        } catch (Exception e) {
            System.out.println("Erro ao carregar a tabela: " + e.getMessage());
        }
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
    public void handleIrParaCompras(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Compras.fxml", event);
    }

    @FXML
    public void handleNovoTipo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Telas_fxml/Criando novo Tipo.fxml"));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            modalStage.setScene(scene);

            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modalStage.initOwner(parentStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.TRANSPARENT);

            modalStage.showAndWait();
            carregarTabela();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}