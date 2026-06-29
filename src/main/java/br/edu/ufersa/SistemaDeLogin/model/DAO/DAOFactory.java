package br.edu.ufersa.SistemaDeLogin.model.DAO;

public interface DAOFactory {
    public FuncionarioDAO criarFuncionarioDAO();
    public ProdutoDAO criarProdutoDAO();
    public TipoDAO criarTipoDAO();
    public NotaDAO criarNotaDAO();
}