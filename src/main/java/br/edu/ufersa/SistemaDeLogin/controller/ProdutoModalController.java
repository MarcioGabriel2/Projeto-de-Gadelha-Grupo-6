package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProdutoModalController {
    @FXML private TextField txtMarca;
    @FXML private TextField txtCodigo;
    @FXML private ComboBox<String> cbTipo;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPreco;

    @FXML public void initialize() {
    }

    @FXML
    public void handleFechar(ActionEvent event) {
        // Pega a própria janela do modal e fecha
        Stage stage = (Stage) txtMarca.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCadastrar(ActionEvent event) {
        System.out.println("Produto cadastrado com sucesso!");

        // Depois de salvar no banco, fecha a janela
        Stage stage = (Stage) txtMarca.getScene().getWindow();
        stage.close();
    }
}