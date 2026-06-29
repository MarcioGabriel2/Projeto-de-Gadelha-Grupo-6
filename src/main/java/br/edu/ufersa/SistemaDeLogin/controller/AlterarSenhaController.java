package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.FuncionarioDAO;
import br.edu.ufersa.SistemaDeLogin.model.service.FuncionarioService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AlterarSenhaController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtNovaSenha;

    // conectando com o banco de dados
    private final FuncionarioService funcionarioService = new FuncionarioService(new FuncionarioDAO());

    @FXML
    private void handleAlterarSenha(ActionEvent event) {
        String email = txtEmail.getText();
        String novaSenha = txtNovaSenha.getText();

        if (email == null || email.isEmpty() || novaSenha == null || novaSenha.isEmpty()) {
            exibirAlerta("Erro", "Por favor, preencha todos os campos.", AlertType.WARNING);
            return;
        }

        try {
            // tenta efetivamente alterar a senha no banco de dados
            funcionarioService.alterarSenha(email, novaSenha);

            exibirAlerta("Sucesso", "Sua senha foi alterada com sucesso!", AlertType.INFORMATION);
            Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event);

        } catch (IllegalArgumentException e) {
            // se o email não existir, avisa o usuário sem quebrar o programa
            exibirAlerta("Aviso", e.getMessage(), AlertType.WARNING);
        } catch (Exception e) {
            exibirAlerta("Erro Crítico", "Falha ao alterar senha. Verifique o banco de dados.", AlertType.ERROR);
        }
    }

    @FXML
    private void handleVoltarLogin(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event);
    }

    @FXML
    private void handleIrParaCadastro(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/3. Tela de Cadastro.fxml", event);
    }

    private void exibirAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}