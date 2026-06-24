package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class TipoModalController {

    @FXML private TextField txtNomeTipo;

    @FXML
    private void handleCadastrarTipo(ActionEvent event) {
        System.out.println("Novo Tipo cadastrado!");
        Navegacao.trocarTela("/Telas_fxml/Gerenciando Produtos (Tipos).fxml", event);
    }

    @FXML
    private void handleFechar(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Gerenciando Produtos (Tipos).fxml", event);
    }
}