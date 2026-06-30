package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.TipoDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TipoModalController {
    @FXML
    private TextField txtNomeTipo;
    @FXML
    private ComboBox<String> cbFormaVenda;

    @FXML
    public void initialize() {
        cbFormaVenda.getItems().addAll("Unidade", "Peso");
    }

    @FXML
    public void handleFechar(ActionEvent event) {
        Stage stage = (Stage) txtNomeTipo.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCadastrar(ActionEvent event) {
        String nome = txtNomeTipo.getText();
        String formaVenda = cbFormaVenda.getValue();

        // Verifica se o usuário não deixou nada em branco
        if (nome == null || nome.trim().isEmpty() || formaVenda == null || formaVenda.trim().isEmpty()) {
            exibirAlerta("Erro", "Por favor, preencha o nome e selecione a forma de venda.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // 1. Cria o objeto Tipo com os dados da tela
            Tipo novoTipo = new Tipo(nome.trim(), formaVenda.trim());

            // 2. Chama o DAO que você já criou para salvar no banco
            TipoDAO dao = new TipoDAO();
            dao.salvar(novoTipo);

            // 3. Avisa que deu certo e fecha a janelinha
            exibirAlerta("Sucesso", "Novo tipo cadastrado com sucesso no banco de dados!", Alert.AlertType.INFORMATION);

            Stage stage = (Stage) txtNomeTipo.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            exibirAlerta("Erro no Banco", "Falha ao salvar o tipo: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // Método para facilitar a criação da janelinha de aviso
    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}