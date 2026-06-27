package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CadastroController {
    @FXML private PasswordField txtSenhaAdmin;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cbCargo;
    @FXML private PasswordField txtSenha;

    @FXML
    public void initialize() {
        // Preenchendo a caixinha de opções com os cargos desejados
        cbCargo.setItems(FXCollections.observableArrayList(
                "Administrador",
                "Funcionário"
        ));
    }

    @FXML
    private void handleCriarConta(ActionEvent event) {
        String senhaAdmin = txtSenhaAdmin.getText();
        String email = txtEmail.getText();
        String cargo = cbCargo.getValue();
        String senha = txtSenha.getText();

        // Validação simples para ver se o usuário preencheu tudo
        if (senhaAdmin.isEmpty() || email.isEmpty() || cargo == null || senha.isEmpty()) {
            exibirAlerta("Erro de Cadastro", "Por favor, preencha todos os campos e selecione um cargo.", AlertType.WARNING);
            return;
        }

        // Simulação de criação de conta
        System.out.println("Criando conta para: " + email + " no cargo de: " + cargo);
        exibirAlerta("Sucesso", "Conta criada com sucesso!", AlertType.INFORMATION);

        // Após criar a conta, volta para a tela de Login
        Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
    }

    @FXML
    private void handleVoltarLogin(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
    }

    @FXML
    private void handleIrParaLogin(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
    }

    private void exibirAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}