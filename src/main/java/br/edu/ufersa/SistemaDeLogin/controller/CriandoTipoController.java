package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.TipoDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import br.edu.ufersa.SistemaDeLogin.model.service.TipoService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class CriandoTipoController {

    @FXML private TextField txtNomeTipo;

    private final TipoService tipoService = new TipoService(new TipoDAO());

    @FXML
    private void handleCadastrarTipo(ActionEvent event) {
        String nomeTipo = txtNomeTipo.getText();

        if (nomeTipo == null || nomeTipo.trim().isEmpty()) {
            exibirAlerta("Campo Vazio", "Por favor, insira o nome da categoria/tipo.", AlertType.WARNING);
            return;
        }

        try {
            // Passa "Unidade" como forma de venda padrão para preencher a sua entidade Tipo
            Tipo novoTipo = new Tipo(nomeTipo.trim(), "Unidade");
            tipoService.cadastrar(novoTipo);

            exibirAlerta("Sucesso", "Categoria cadastrada com sucesso!", AlertType.INFORMATION);
            Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event);
        } catch (IllegalArgumentException e) {
            exibirAlerta("Erro de Cadastro", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void handleFechar(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event);
    }

    private void exibirAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}