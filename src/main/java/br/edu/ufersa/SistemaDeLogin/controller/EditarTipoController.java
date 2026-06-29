package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarTipoController {
    @FXML
    private TextField txtNomeTipo;

    @FXML
    private ComboBox<String> cbFormaVenda;

    @FXML
    public void initialize() { }

    @FXML
    public void handleFechar(ActionEvent event) {
        Stage stage = (Stage) txtNomeTipo.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleAtualizar(ActionEvent event) {
        System.out.println("Tipo atualizado com sucesso!");
        Stage stage = (Stage) txtNomeTipo.getScene().getWindow();
        stage.close();
    }
}