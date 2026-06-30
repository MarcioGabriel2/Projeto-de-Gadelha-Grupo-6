package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.TipoDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarTipoController {
    @FXML private TextField txtNomeTipo;
    @FXML private ComboBox cbFormaVenda;

    // Essa variável vai guardar o tipo que chegou lá da tabela
    private Tipo tipoSelecionado;

    @FXML
    public void initialize() {
        // Preenche a caixinha de opções igual fizemos no de cadastrar
        cbFormaVenda.getItems().addAll("Unidade", "Peso");
    }

    // Este é o método mágico que vai receber o Tipo da tela anterior e preencher os campos
    public void preencherDados(Tipo tipo) {
        this.tipoSelecionado = tipo;
        txtNomeTipo.setText(tipo.getNome());
        cbFormaVenda.setValue(tipo.getFormaVenda());
    }

    @FXML
    public void handleFechar(ActionEvent event) {
        Stage stage = (Stage) txtNomeTipo.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleAtualizar(ActionEvent event) {
        String nome = txtNomeTipo.getText().trim();
        String formaVenda = (String) cbFormaVenda.getValue();

        if (nome.isEmpty() || formaVenda == null || formaVenda.trim().isEmpty()) {
            exibirAlerta("Erro", "Por favor, preencha todos os campos.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Cria um "novo" Tipo, mas mantendo o ID original para o banco saber quem atualizar
            Tipo tipoAtualizado = new Tipo(tipoSelecionado.getId(), nome, formaVenda);

            TipoDAO dao = new TipoDAO();
            dao.atualizar(tipoAtualizado);

            exibirAlerta("Sucesso", "Tipo atualizado com sucesso no banco de dados!", Alert.AlertType.INFORMATION);

            Stage stage = (Stage) txtNomeTipo.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            exibirAlerta("Erro no Banco", "Falha ao atualizar o tipo: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}