package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.TipoDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import br.edu.ufersa.SistemaDeLogin.model.service.TipoService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import br.edu.ufersa.SistemaDeLogin.util.Sessao;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class CriandoTipoController {

    @FXML private TextField txtNomeTipo;
    @FXML private MenuButton menuFormaVenda;
    @FXML private Label lblUsuarioNome;
    @FXML private Label lblUsuarioCargo;

    @FXML
    public void initialize() {
        carregarPerfilUsuario();
    }

    private final TipoService tipoService = new TipoService(new TipoDAO());
    private String formaVendaSelecionada = "";

    @FXML
    private void handleSelecaoUnidade(ActionEvent event) {
        formaVendaSelecionada = "Unidade";
        menuFormaVenda.setText("Unidade");
    }

    @FXML
    private void handleSelecaoQuilo(ActionEvent event) {
        formaVendaSelecionada = "Quilo";
        menuFormaVenda.setText("Quilo");
    }

    @FXML
    private void handleSelecaoLitro(ActionEvent event) {
        formaVendaSelecionada = "Litro";
        menuFormaVenda.setText("Litro");
    }

    @FXML
    private void handleCadastrarTipo(ActionEvent event) {
        String nomeTipo = txtNomeTipo.getText();

        if (nomeTipo == null || nomeTipo.trim().isEmpty()) {
            exibirAlerta("Campo Vazio", "Por favor, insira o nome da categoria/tipo.", AlertType.WARNING);
            return;
        }

        if (formaVendaSelecionada.isEmpty()) {
            exibirAlerta("Campo Vazio", "Por favor, selecione uma forma de venda.", AlertType.WARNING);
            return;
        }

        try {
            // cria a entidade com os dados dinâmicos coletados da tela
            Tipo novoTipo = new Tipo(nomeTipo.trim(), formaVendaSelecionada);
            tipoService.cadastrar(novoTipo);

            exibirAlerta("Sucesso", "Categoria cadastrada com sucesso!", AlertType.INFORMATION);

            // retorna para a listagem de tipos atualizada
            Navegacao.trocarTela("/Telas_fxml/8. Gerenciamento de Tipos.fxml", event);
        } catch (IllegalArgumentException e) {
            exibirAlerta("Erro de Cadastro", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void handleFechar(ActionEvent event) {
        // retorna para o gerenciamento de tipos
        Navegacao.trocarTela("/Telas_fxml/8. Gerenciamento de Tipos.fxml", event);
    }

    // handlers e navegação
    @FXML private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event); }
    @FXML private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event); }
    @FXML private void handleIrParaVendas(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/TelaDeVendas.fxml", event); }
    @FXML private void handleIrParaCompras(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/15. Tela de Compras.fxml", event); }
    @FXML private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event); }

    private void exibirAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void carregarPerfilUsuario() {
        Funcionario usuarioLogado = Sessao.getUsuarioLogado();

        if (usuarioLogado != null) {
            lblUsuarioNome.setText(usuarioLogado.getNome());
            String cargo = usuarioLogado.getTipo();
            lblUsuarioCargo.setText(cargo);

            if (cargo != null && cargo.equalsIgnoreCase("Funcionário")) {
                lblUsuarioCargo.setStyle("-fx-background-color: #E0F2FE; -fx-background-radius: 15px; -fx-padding: 2px 10px; -fx-font-weight: bold;");
                lblUsuarioCargo.setTextFill(Color.web("#0369A1"));
            } else {
                lblUsuarioCargo.setStyle("-fx-background-color: #E2E0FA; -fx-background-radius: 15px; -fx-padding: 2px 10px; -fx-font-weight: bold;");
                lblUsuarioCargo.setTextFill(Color.web("#432dd7"));
            }
        }
    }

}