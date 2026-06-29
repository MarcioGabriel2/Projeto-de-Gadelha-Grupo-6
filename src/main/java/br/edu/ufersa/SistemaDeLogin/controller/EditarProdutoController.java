package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarProdutoController {
    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtCodigo;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TextField txtPreco;

    @FXML
    public void initialize() {
        // No futuro, quando o usuário clicar no botão "Editar" da tabela,
        // o código vai enviar os dados do produto para preencher esses campos automaticamente!
    }

    @FXML
    public void handleFechar(ActionEvent event) {
        // Fecha o modal e revela a tabela
        Stage stage = (Stage) txtMarca.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleAtualizar(ActionEvent event) {
        System.out.println("Produto atualizado no banco de dados!");

        // Fecha a janela após atualizar
        Stage stage = (Stage) txtMarca.getScene().getWindow();
        stage.close();
    }
}