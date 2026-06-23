package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class CadastroController {

    @FXML private TextField txtEmailCadastro;
    @FXML private PasswordField txtSenhaCadastro;
    @FXML private MenuButton menuCargo;

    @FXML
    private void handleCriarConta(ActionEvent event) {
        System.out.println("Criando conta para: " + txtEmailCadastro.getText());
        Navegacao.trocarTela("/views/Tela de Login 1 Final.fxml", event);
    }

    @FXML
    private void handleAlterarSenha(ActionEvent event) {
        System.out.println("Senha alterada com sucesso!");
        Navegacao.trocarTela("/views/Tela de Login 1 Final.fxml", event);
    }

    @FXML
    private void handleVoltarParaLogin(ActionEvent event) {
        Navegacao.trocarTela("/views/Tela de Login 1 Final.fxml", event);
    }
}