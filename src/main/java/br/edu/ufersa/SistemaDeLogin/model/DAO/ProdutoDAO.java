package br.edu.ufersa.SistemaDeLogin.model.DAO;

import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {


    public void salvar(Produto p) {
        String sql = "INSERT INTO tb_produto (marca, codigo_barras, preco, quantidade_estoque, tipo_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, p.getMarca());
            stmt.setString(2, p.getCodigoBarras());
            stmt.setDouble(3, p.getPreco());
            stmt.setDouble(4, p.getQuantidadeEstoque());
            stmt.setInt(5, p.getTipo().getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage());
        }
    }

    // não deixa cadastrar código de barras repetido
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT p.*, t.nome as tipo_nome, t.forma_venda FROM tb_produto p INNER JOIN tipos t ON p.tipo_id = t.id";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tipo tipo = new Tipo(rs.getInt("tipo_id"), rs.getString("tipo_nome"), rs.getString("forma_venda"));
                Produto p = new Produto(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("codigo_barras"),
                        rs.getDouble("preco"),
                        tipo
                );
                p.alterarQuantidadeEstoque(rs.getDouble("quantidade_estoque"));
                produtos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    // atualiza a quantidade de estoque lá no banco após uma venda/compra
    public void alterar(Produto p) {
        String sql = "UPDATE tb_produto SET quantidade_estoque = ? WHERE id = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setDouble(1, p.getQuantidadeEstoque());
            stmt.setInt(2, p.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estoque do produto: " + e.getMessage());
        }
    }
}
