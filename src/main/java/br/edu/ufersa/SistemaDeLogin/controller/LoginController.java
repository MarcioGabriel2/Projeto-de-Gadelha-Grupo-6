package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.Conexao;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;

    @FXML
    private void handleEntrar(ActionEvent event) {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            exibirAlerta("Campos Vazios", "Por favor, preencha todos os campos.", AlertType.WARNING);
            return;
        }

        if (autenticarUsuario(email, senha)) {
            System.out.println("Login efetuado! Redirecionando para o Dashboard...");
            // IMPORTANTE: Renomeie o arquivo FXML real para não conter espaços
            Navegacao.trocarTela("/telas_fxml/dashboard_final.fxml", event);
        } else {
            exibirAlerta("Erro de Login", "Usuário ou senha inválidos.", AlertType.ERROR);
        }
    }

    private boolean autenticarUsuario(String email, String senha) {
        // Ajuste os nomes das colunas (ex: email, senha) conforme o seu banco tb_funcionario
        String sql = "SELECT * FROM tb_funcionario WHERE email = ? AND senha = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true se achar o funcionário correto
            }

        } catch (SQLException e) {
            exibirAlerta("Erro de Banco de Dados", "Falha ao conectar ao MariaDB: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleIrParaEsqueceuSenha(ActionEvent event) {
        // Certifique-se de usar "Telas_fxml" com T maiúsculo e o nome exato do arquivo
        Navegacao.trocarTela("/Telas_fxml/Tela de Alterar Senha.fxml", event);
    }

    @FXML
    private void handleIrParaCadastro(ActionEvent event) {
        // Veja se o arquivo se chama "Tela de Cadastro.fxml" ou "Tela de Cadastro 1 Final.fxml"
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