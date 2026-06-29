package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.DAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.SqlDAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import br.edu.ufersa.SistemaDeLogin.model.service.ProdutoService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import br.edu.ufersa.SistemaDeLogin.util.Sessao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;
import br.edu.ufersa.SistemaDeLogin.model.DAO.TipoDAO;
import javafx.scene.paint.Color;

public class CriandoProdutoController {

    @FXML private TextField txtMarca;
    @FXML private TextField txtCodigoBarras;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPreco;
    @FXML private Label lblUsuarioNome;
    @FXML private Label lblUsuarioCargo;
    @FXML private ComboBox<String> comboTipo;

    private final DAOFactory daoFactory = new SqlDAOFactory();
    private final ProdutoService produtoService = new ProdutoService(daoFactory.criarProdutoDAO());

    // Lista para guardar os objetos Tipo carregados do banco de dados
    private List<Tipo> listaTiposDoBanco = new ArrayList<>();

    @FXML
    public void initialize() {
        carregarPerfilUsuario();
        carregarTiposNoComboBox();
    }

    private void carregarTiposNoComboBox() {
        try {
            // BUSCA CORRETA: Puxa direto da fonte (Tabela de Tipos) usando o TipoDAO
            listaTiposDoBanco = new TipoDAO().listarTodos();

            if (listaTiposDoBanco.isEmpty()) {
                System.out.println("Nenhum tipo encontrado no banco. Adicionando provisórios...");
                listaTiposDoBanco = new ArrayList<>();
                listaTiposDoBanco.add(new Tipo(1, "Alimento", "Unidade"));
                listaTiposDoBanco.add(new Tipo(2, "Bebida", "Unidade"));
            }

            // Extrai apenas os nomes dos tipos para exibir no ComboBox
            List<String> nomesTipos = new ArrayList<>();
            for (Tipo t : listaTiposDoBanco) {
                nomesTipos.add(t.getNome());
            }

            comboTipo.setItems(FXCollections.observableArrayList(nomesTipos));
        } catch (Exception e) {
            System.out.println("Erro ao carregar tipos do banco: " + e.getMessage());
        }
    }

    @FXML
    private void handleSalvarProduto(ActionEvent event) {
        String marca = txtMarca.getText();
        String codigoBarras = txtCodigoBarras.getText();
        String qtdStr = txtQuantidade.getText();
        String precoStr = txtPreco.getText();
        String tipoSelecionadoStr = comboTipo.getValue();

        if (marca == null || codigoBarras == null || qtdStr == null || precoStr == null || tipoSelecionadoStr == null ||
                marca.trim().isEmpty() || codigoBarras.trim().isEmpty() || qtdStr.trim().isEmpty() || precoStr.trim().isEmpty()) {
            exibirAlerta("Campos Vazios", "Por favor, preencha todos os campos do produto e selecione um tipo.", AlertType.WARNING);
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr.trim().replace(",", "."));
            double quantidade = Double.parseDouble(qtdStr.trim());

            // PROCURA O OBJETO TIPO CORRETO COM O ID REAL DO BANCO
            Tipo tipoSelecionado = null;
            for (Tipo t : listaTiposDoBanco) {
                if (t.getNome().equals(tipoSelecionadoStr)) {
                    tipoSelecionado = t;
                    break;
                }
            }

            // Caso não tenha achado na lista dinâmica, cria um provisório estável
            if (tipoSelecionado == null) {
                int idProvisorio = tipoSelecionadoStr.equals("Alimento") ? 1 : 2;
                tipoSelecionado = new Tipo(idProvisorio, tipoSelecionadoStr, "Unidade");
            }

            // Instancia o produto com o Tipo contendo o ID correto
            Produto novoProduto = new Produto(marca.trim(), codigoBarras.trim(), preco, tipoSelecionado);
            novoProduto.alterarQuantidadeEstoque(quantidade);

            // Cadastra no Banco usando o Service
            produtoService.cadastrar(novoProduto);

            exibirAlerta("Sucesso", "Produto cadastrado com sucesso!", AlertType.INFORMATION);
            Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event);

        } catch (NumberFormatException e) {
            exibirAlerta("Erro de Formato", "Quantidade e Preço devem conter valores numéricos válidos.", AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            exibirAlerta("Erro de Regra", e.getMessage(), AlertType.ERROR);
        } catch (RuntimeException e) {
            exibirAlerta("Erro de Banco", "Verifique se o Tipo selecionado existe na tabela tb_tipo do banco.\nErro original: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void handleFechar(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/5. Gerenciando Produtos.fxml", event);
    }

    @FXML
    private void handleIrParaDashboard(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/4. Dashboard.fxml", event);
    }

    @FXML
    private void handleIrParaVendas(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/TelaDeVendas.fxml", event);
    }

    @FXML
    private void handleIrParaCompras(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/15. Tela de Compras.fxml", event);
    }

    @FXML
    private void handleSair(ActionEvent event) {
        Navegacao.trocarTela("/Telas_fxml/1. Tela de Login 1.fxml", event);
    }

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

            // Ajusta as cores do "badge" dependendo se é Gerente ou Funcionário
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