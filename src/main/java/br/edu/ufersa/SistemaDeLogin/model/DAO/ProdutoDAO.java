package br.edu.ufersa.SistemaDeLogin.model.DAO;

import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import java.util.List;

public interface ProdutoDAO {
    void salvar(Produto p);
    List<Produto> listarTodos();
    void alterar(Produto p);
    void deletar(int id); // apaga produtos
    int contarTiposDeProdutos();
}