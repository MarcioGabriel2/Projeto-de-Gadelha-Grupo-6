package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.FuncionarioDAO;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AlterarSenhaController {
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtNovaSenha;

    @FXML
    private void handleAlterarSenha(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String novaSenha = txtNovaSenha.getText();

        if (email.isEmpty() || novaSenha.isEmpty()) {
            exibirAlerta("Erro", "Por favor, preencha todos os campos.", AlertType.WARNING);
            return;
        }

        try {
            FuncionarioDAO dao = new FuncionarioDAO();

            // Tenta atualizar no banco de dados
            boolean sucesso = dao.atualizarSenha(email, novaSenha);

            if (sucesso) {
                exibirAlerta("Sucesso", "Sua senha foi alterada com sucesso no banco de dados!", AlertType.INFORMATION);
                Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
            } else {
                exibirAlerta("Erro", "E-mail não encontrado no sistema. Verifique e tente novamente.", AlertType.ERROR);
            }

        } catch (Exception e) {
            exibirAlerta("Erro no Banco", "Falha ao tentar alterar a senha: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoltarLogin(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
    }

    @FXML
    private void handleIrParaCadastro(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Cadastro.fxml", event);
    }

    private void exibirAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}