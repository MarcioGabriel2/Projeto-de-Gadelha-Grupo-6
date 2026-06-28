package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.NotaDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Nota;
import br.edu.ufersa.SistemaDeLogin.model.service.NotaService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class VendasController {

    private final NotaService notaService = new NotaService(new NotaDAO());
    private Nota notaCorrente = new Nota();

    @FXML
    private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event); }

    @FXML
    private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event); }

    @FXML
    private void handleIrParaVendas(ActionEvent event) { /* Contexto Atual */ }

    @FXML
    private void handleIrParaCompras(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/14. Tela de Compras.fxml", event); }

    @FXML
    private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event); }

    @FXML
    private void handleAdicionarNota() {
        System.out.println("Item inserido na lista temporária da venda...");
    }

    @FXML
    private void handleFinalizarVenda() {
        try {
            // Envia para o NotaService salvar a venda e seus itens com controle de transação (commit/rollback)
            notaService.finalizarVenda(notaCorrente);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Venda registrada com sucesso no banco de dados!");
            alert.showAndWait();

            notaCorrente = new Nota(); // Reseta para a próxima venda
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancelarNota() {
        notaCorrente = new Nota();
        System.out.println("Nota descartada e campos redefinidos.");
    }

    @FXML
    private void handleEditarItem() { System.out.println("Atualizando quantidade do item selecionado..."); }

    @FXML
    private void handleExcluirItem() { System.out.println("Removendo item da nota fiscal..."); }
}