package br.edu.ufersa.SistemaDeLogin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class VendasHistoricoController {
    @FXML
    private TableView<?> tabelaHistorico;

    @FXML
    public void initialize() {
        // Futuramente: O botão "Gerar PDF" vai ser criado aqui pelo Java,
        // sendo renderizado dinamicamente dentro da coluna de Ações!
    }

    @FXML
    public void handleIrParaDashboard(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Dashboard Final.fxml", event);
    }

    @FXML
    public void handleIrParaProdutos(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Gerenciando Produtos 1 Final.fxml", event);
    }

    @FXML
    public void handleIrParaNovaVenda(ActionEvent event) {
        // Volta para a tela de bipar os produtos (A primeira versão fixa)
        Navegacao.trocarTela("/Telas_fxml/Tela de Vendas.fxml", event);
    }

    @FXML
    public void handleSair(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/Tela de Login 1 Final.fxml", event);
    }
}