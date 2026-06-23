package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AlterarSenhaController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtNovaSenha;

    @FXML
    private void handleAlterarSenha(ActionEvent event) {
        System.out.println("Palavra-passe alterada para o email inserido!");
        Navegacao.trocarTela("/Telas fxml/Tela de Login 1 Final.fxml", event);
    }

    @FXML
    private void handleVoltarLogin(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/Tela de Login 1 Final.fxml", event);
    }

    @FXML
    private void handleIrParaCadastro(ActionEvent event) {
        Navegacao.trocarTela("/Telas fxml/Tela de Cadastro.fxml", event);
    }
}