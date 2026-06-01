package br.edu.ufersa.sistemaDeLogin.model.service;

import br.edu.ufersa.sistemaDeLogin.model.DAO.ProdutoDAO;
import br.edu.ufersa.sistemaDeLogin.model.entities.Produto;
import java.util.List;

public class ProdutoService {
    private ProdutoDAO produtoDAO;

    public ProdutoService(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    // venda de produto tirando do estoque
    public void venderProduto(Produto p, double qtd) {
        // validação para ver se tem estoque suficiente
        if (p.getQuantidadeEstoque() >= qtd) {
            p.alterarQuantidadeEstoque(-qtd);
        } else {
            throw new IllegalArgumentException("Estoque insuficiente para a venda!");
        }
    }

    // compra de produto aumentando o estoque
    public void comprarProduto(Produto p, double qtd) {
        p.alterarQuantidadeEstoque(qtd);
    }

    // cadastro de produto sem repetir codigo de barras
    public void cadastrar(Produto novoProduto) {
        List<Produto> produtos = produtoDAO.listarTodos();

        // validação para não permitir código de barras igual
        for (Produto p : produtos) {
            if (p.getCodigoBarras().equals(novoProduto.getCodigoBarras())) {
                throw new IllegalArgumentException("Já existe um produto com este código de barras!");
            }
        }

        produtoDAO.salvar(novoProduto);
    }
}