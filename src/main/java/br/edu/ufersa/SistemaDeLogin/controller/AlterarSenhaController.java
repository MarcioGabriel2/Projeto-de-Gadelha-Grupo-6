package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AlterarSenhaController {
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtNovaSenha;

    @FXML
    private void handleAlterarSenha(ActionEvent event) {
        String email = txtEmail.getText();
        String novaSenha = txtNovaSenha.getText();

        if (email.isEmpty() || novaSenha.isEmpty()) {
            exibirAlerta("Erro", "Por favor, preencha todos os campos.", AlertType.WARNING);
        } else {
            System.out.println("Senha alterada com sucesso para o email: " + email);
            exibirAlerta("Sucesso", "Sua senha foi alterada com sucesso!", AlertType.INFORMATION);

            Navegacao.trocarTela("/Telas fxml/Tela de Login 1 Final.fxml", event);
        }
    }

    @FXML
    private void handleVoltarLogin(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/Tela de Login 1 Final.fxml", event);
    }

    @FXML
    private void handleIrParaCadastro(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/Tela de Cadastro.fxml", event);
    }

    private void exibirAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}