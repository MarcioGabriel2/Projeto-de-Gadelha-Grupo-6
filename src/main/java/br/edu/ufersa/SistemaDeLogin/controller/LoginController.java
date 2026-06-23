package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;

    @FXML
    private void handleEntrar(ActionEvent event) {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        if (email.equals("admin@gmail.com") && senha.equals("123")) {
            System.out.println("Login efetuado! Redirecionando para o Dashboard...");
            Navegacao.trocarTela("/Telas fxml/Dashboard Final.fxml", event);
        } else {
            exibirAlerta("Erro de Login", "Usuário ou senha inválidos.", AlertType.ERROR);
        }
    }

    @FXML
    private void handleIrParaEsqueceuSenha(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/Tela de Alterar Senha.fxml", event);
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