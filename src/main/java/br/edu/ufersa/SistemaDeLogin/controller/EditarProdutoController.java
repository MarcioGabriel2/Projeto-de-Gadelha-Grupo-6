package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.DAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.SqlDAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.TipoDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import br.edu.ufersa.SistemaDeLogin.util.Sessao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.List;

public class EditarProdutoController {

    @FXML private TextField txtMarca;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPreco;
    @FXML private MenuButton menuTipo;
    @FXML private Label lblUsuarioNome;
    @FXML private Label lblUsuarioCargo;

    private final DAOFactory daoFactory = new SqlDAOFactory();
    private Produto produtoEmEdicao;
    private Tipo tipoSelecionado;

    @FXML
    public void initialize() {
        carregarPerfilUsuario();
        carregarTiposNoMenu();
    }

    private void carregarTiposNoMenu() {
        try {
            // busca os tipos do banco
            List<Tipo> listaTipos = new TipoDAO().listarTodos();
            menuTipo.getItems().clear(); // remove itens inexistentes

            for (Tipo tipo : listaTipos) {
                MenuItem item = new MenuItem(tipo.getNome());

                item.setOnAction(event -> {
                    this.tipoSelecionado = tipo;
                    menuTipo.setText(tipo.getNome());
                });

                menuTipo.getItems().add(item);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar tipos no MenuButton: " + e.getMessage());
        }
    }


    public void preencherCampos(Produto produto) {
        this.produtoEmEdicao = produto;
        txtMarca.setText(produto.getMarca());
        txtCodigo.setText(produto.getCodigoBarras());
        txtQuantidade.setText(String.valueOf(produto.getQuantidadeEstoque()));
        txtPreco.setText(String.valueOf(produto.getPreco()));

        if (produto.getTipo() != null) {
            this.tipoSelecionado = produto.getTipo();
            menuTipo.setText(produto.getTipo().getNome());
        } else {
            menuTipo.setText("Selecione um tipo");
        }
    }

    @FXML
    private void handleAtualizar(ActionEvent event) {
        try {
            // validação de segurança
            if (txtMarca.getText().trim().isEmpty() || txtCodigo.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, preencha a Marca e o Código.");
                alert.showAndWait();
                return;
            }

            // atualiza os dados do objeto
            produtoEmEdicao.setMarca(txtMarca.getText().trim());
            produtoEmEdicao.setCodigoBarras(txtCodigo.getText().trim());
            produtoEmEdicao.setPreco(Double.parseDouble(txtPreco.getText().trim()));
            produtoEmEdicao.setQuantidadeEstoque(Double.parseDouble(txtQuantidade.getText().trim()));

            // vincula o tipo
            if (tipoSelecionado != null) {
                produtoEmEdicao.setTipo(tipoSelecionado);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, selecione uma categoria/tipo para o produto.");
                alert.showAndWait();
                return;
            }

            // salva alterações do banco de dados
            daoFactory.criarProdutoDAO().alterar(produtoEmEdicao);

            System.out.println("Produto ID " + produtoEmEdicao.getId() + " atualizado com sucesso com o Tipo: " + tipoSelecionado.getNome());

            // retorna a listagem principal
            Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantidade e Preço precisam ser números válidos.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao atualizar produto no banco: " + e.getMessage());
            e.printStackTrace();
            alert.showAndWait();
        }
    }

    @FXML private void handleCancelar(ActionEvent event) { handleIrParaProdutos(event); }
    @FXML private void handleIrParaDashboard(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event); }
    @FXML private void handleIrParaProdutos(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event); }
    @FXML private void handleIrParaVendas(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/11. Tela de Vendas.fxml", event); }
    @FXML private void handleIrParaCompras(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/15. Tela de Compras.fxml", event); }
    @FXML private void handleSair(ActionEvent event) { Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event); }

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