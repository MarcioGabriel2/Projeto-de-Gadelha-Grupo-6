package br.edu.ufersa.SistemaDeLogin.model.DAO;

public class SqlDAOFactory implements DAOFactory {

    @Override
    public FuncionarioDAO criarFuncionarioDAO() {
        return new FuncionarioDAO();
    }

    @Override
    public ProdutoDAO criarProdutoDAO() {
        return new SqlProdutoDAO();
    }

    @Override
    public TipoDAO criarTipoDAO() {
        return new TipoDAO();
    }

    @Override
    public NotaDAO criarNotaDAO() {
        return new NotaDAO();
    }
}