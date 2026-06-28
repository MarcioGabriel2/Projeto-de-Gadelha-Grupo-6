package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.FuncionarioDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import br.edu.ufersa.SistemaDeLogin.model.service.FuncionarioService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class CadastroController {

    // Nomes corrigidos para bater exatamente com os fx:id do FXML
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private PasswordField txtSenhaAdmin; // Antigo txtSenha1
    @FXML private MenuButton menuCargo;

    private final FuncionarioService funcionarioService = new FuncionarioService(new FuncionarioDAO());

    @FXML
    public void initialize() {
        // Faz o MenuButton atualizar o texto principal quando você seleciona uma opção
        if (menuCargo != null) {
            for (MenuItem item : menuCargo.getItems()) {
                item.setOnAction(event -> menuCargo.setText(item.getText()));
            }
        }
    }

    @FXML
    private void handleCriarConta(ActionEvent event) {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();
        String senhaAdmin = txtSenhaAdmin.getText();
        String cargo = menuCargo.getText();

        // 1. Validação da Senha de Administrador (Você pode mudar "admin123" para o que quiser)
        if (senhaAdmin == null || !senhaAdmin.equals("admin123")) {
            exibirAlerta("Acesso Negado", "Senha de administrador incorreta!", AlertType.ERROR);
            return;
        }

        // 2. Validação de campos vazios ou cargo não selecionado
        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty() || cargo.equals("Escolha um cargo...")) {
            exibirAlerta("Campos Vazios", "Por favor, preencha todos os campos e selecione um cargo.", AlertType.WARNING);
            return;
        }

        // 3. Tenta cadastrar no banco
        try {
            Funcionario novoFuncionario = new Funcionario(email.trim(), cargo, senha);
            funcionarioService.cadastrar(novoFuncionario);

            exibirAlerta("Sucesso", "Conta criada com sucesso!", AlertType.INFORMATION);
            Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event);
        } catch (IllegalArgumentException e) {
            exibirAlerta("Erro de Cadastro", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void handleAlterarSenha(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event);
    }

    @FXML
    private void handleVoltarLogin(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event);
    }

    private void exibirAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}