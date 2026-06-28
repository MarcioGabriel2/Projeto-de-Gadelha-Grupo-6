package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class GerenciamentoTiposController {

    @FXML
    private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event); }

    @FXML
    private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event); }

    @FXML
    private void handleIrParaVendas(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/TelaDeVendas.fxml", event); }

    @FXML
    private void handleIrParaCompras(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/14. Tela de Compras.fxml", event); }

    @FXML
    private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event); }

    @FXML
    private void handleNovoTipo(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/9. Criando novo Tipo.fxml", event);
    }

    @FXML
    private void handleEditarTipo() { System.out.println("Abrindo painel de edição de categoria..."); }

    @FXML
    private void handleApagarTipo() { System.out.println("Efetuando DELETE da categoria se não houver vínculos ativos."); }
}