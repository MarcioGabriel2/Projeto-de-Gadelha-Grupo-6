package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.FuncionarioDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import br.edu.ufersa.SistemaDeLogin.model.service.FuncionarioService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;

    // Conectando com a camada de serviço real
    private final FuncionarioService funcionarioService = new FuncionarioService(new FuncionarioDAO());

    @FXML
    private void handleEntrar(ActionEvent event) {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            exibirAlerta("Campos Vazios", "Por favor, preencha todos os campos.", AlertType.WARNING);
            return;
        }

        try {
            // Valida as credenciais comparando com os registros do banco de dados
            Funcionario funcionario = funcionarioService.login(email.trim(), senha);
            System.out.println("Login efetuado com sucesso para: " + funcionario.getNome());

            // Redireciona para o Dashboard principal
            Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event);
        } catch (IllegalArgumentException e) {
            exibirAlerta("Erro de Autenticação", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void handleIrParaEsqueceuSenha(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/2. Tela de Alterar Senha.fxml", event);
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