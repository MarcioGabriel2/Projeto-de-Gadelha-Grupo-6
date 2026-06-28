package br.edu.ufersa.SistemaDeLogin.controller;

import br.edu.ufersa.SistemaDeLogin.model.DAO.DAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.DAO.SqlDAOFactory;
import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import br.edu.ufersa.SistemaDeLogin.model.service.ProdutoService;
import br.edu.ufersa.SistemaDeLogin.util.Navegacao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

public class CriandoProdutoController {

    @FXML private TextField txtMarca;
    @FXML private TextField txtCodigoBarras;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPreco;
    @FXML private ComboBox<String> comboTipo;

    private final DAOFactory daoFactory = new SqlDAOFactory();
    private final ProdutoService produtoService = new ProdutoService(daoFactory.criarProdutoDAO());

    // Lista para guardar os objetos Tipo carregados do banco de dados
    private List<Tipo> listaTiposDoBanco = new ArrayList<>();

    @FXML
    public void initialize() {
        carregarTiposNoComboBox();
    }

    private void carregarTiposNoComboBox() {
        try {
            // BUSCA DINÂMICA: Puxa todos os tipos reais cadastrados na tabela tb_tipo
            // Se sua fábrica possuir criarTipoDAO(), use: daoFactory.criarTipoDAO().listarTodos()
            // Caso use uma instância direta como 'new SqlTipoDAO().listarTodos()', ajuste aqui.
            // Para garantir que o código compile, estou simulando o uso padrão da sua Factory:

            // Exemplo genérico (ajuste conforme o método real da sua Fábrica para Tipos):
            // listaTiposDoBanco = daoFactory.criarTipoDAO().listarTodos();

            // Como medida de segurança (caso o banco esteja vazio), criamos uma lista temporária
            // Mas o ideal é que você tenha registros na tabela `tb_tipo` do MySQL!

            // TODO: Substitua a linha abaixo pela chamada real da sua DAO de tipos se necessário:
            listaTiposDoBanco = daoFactory.criarProdutoDAO().listarTodos().stream()
                    .map(Produto::getTipo).distinct().toList();

            if (listaTiposDoBanco.isEmpty()) {
                // Caso não tenha nenhum tipo cadastrado no banco ainda, colocamos provisórios com ID 1
                // para o sistema não quebrar, mas lembre-se de cadastrar tipos na tb_tipo!
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
            System.out.println("Aviso ao carregar tipos (usando fallback): " + e.getMessage());
            // Fallback caso a tabela tb_tipo ainda não esteja mapeada na factory
            comboTipo.setItems(FXCollections.observableArrayList("Alimento", "Bebida"));
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
        Navegacao.trocarTela("/Telas_fxml/TelaDeCompras.fxml", event);
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
}