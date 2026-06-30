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

        try {
            System.out.println("Tentando alcançar o MySQL na porta configurada...");
            java.sql.Connection testeCon = br.edu.ufersa.SistemaDeLogin.model.DAO.Conexao.getConnection();

            if (testeCon != null) {
                System.out.println("O Java conseguiu abrir a porta do MySQL com sucesso!");
                testeCon.close();
            }

            if (autenticarUsuario(email, senha)) {
                System.out.println("Login efetuado! Redirecionando para o Dashboard...");
                Navegacao.trocarTela("/Telas_fxml/Dashboard Final.fxml", event);
            } else {
                exibirAlerta("Erro de Login", "O banco respondeu, mas o usuário ou senha estão incorretos.", AlertType.ERROR);
            }

        } catch (Exception e) {
            System.out.println("--- ERRO DE CONEXÃO DETECTADO ---");
            e.printStackTrace();
            exibirAlerta("Falha de Conexão", "O Java não se conectou com o banco pelo seguinte motivo:\n" + e.getMessage(), AlertType.ERROR);
        }
    }

    private boolean autenticarUsuario(String email, String senha) {
        // CORRIGIDO AQUI: O Java continua recebendo a variável 'email' da tela, mas pede para o banco procurar na coluna 'nome'
        String sql = "SELECT * FROM tb_funcionario WHERE email = ? AND senha = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            exibirAlerta("Erro de Banco de Dados", "Falha ao conectar ao banco: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleIrParaEsqueceuSenha(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Alterar Senha.fxml", event);
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