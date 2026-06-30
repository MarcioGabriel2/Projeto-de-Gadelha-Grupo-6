package br.edu.ufersa.SistemaDeLogin.controller;
import br.edu.ufersa.SistemaDeLogin.model.DAO.FuncionarioDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CadastroController {
    @FXML private PasswordField txtSenhaAdmin;
    @FXML private TextField txtEmail;
    @FXML private ComboBox cbCargo;
    @FXML private PasswordField txtSenha;

    @FXML
    public void initialize() {
        cbCargo.setItems(FXCollections.observableArrayList(
                "Administrador",
                "Funcionário"
        ));
    }

    @FXML
    private void handleCriarConta(ActionEvent event) {
        String senhaAdmin = txtSenhaAdmin.getText();
        String email = txtEmail.getText().trim();
        String cargo = (String) cbCargo.getValue();
        String senha = txtSenha.getText();

        if (senhaAdmin.isEmpty() || email.isEmpty() || cargo == null || senha.isEmpty()) {
            exibirAlerta("Erro de Cadastro", "Por favor, preencha todos os campos e selecione um cargo.", AlertType.WARNING);
            return;
        }

        try {
            FuncionarioDAO dao = new FuncionarioDAO();

            // 1. Verifica se a pessoa sabe a senha do Administrador para autorizar a criação
            boolean adminAutorizado = dao.autenticar("admin@email.com", senhaAdmin);
            if (!adminAutorizado) {
                exibirAlerta("Acesso Negado", "Senha de administrador incorreta!", AlertType.ERROR);
                return;
            }

            // 2. Extrai um nome provisório do e-mail (tudo antes do @) já que não tem campo 'Nome' na tela
            String nome = email.contains("@") ? email.split("@")[0] : "Novo Usuário";

            // 3. Monta o objeto funcionário e manda o DAO salvar no banco
            Funcionario novoFuncionario = new Funcionario(nome, email, cargo, senha);
            dao.salvar(novoFuncionario);

            exibirAlerta("Sucesso", "Conta criada com sucesso no banco de dados!", AlertType.INFORMATION);
            Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);

        } catch (Exception e) {
            exibirAlerta("Erro no Banco", "Falha ao salvar conta: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoltarLogin(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
    }

    private void exibirAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}