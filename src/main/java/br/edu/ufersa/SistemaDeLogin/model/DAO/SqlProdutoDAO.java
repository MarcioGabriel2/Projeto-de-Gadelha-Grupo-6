package br.edu.ufersa.SistemaDeLogin.model.DAO;

import br.edu.ufersa.SistemaDeLogin.model.entities.Produto;
import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlProdutoDAO implements ProdutoDAO {

    public void salvar(Produto p) {
        String sql = "INSERT INTO tb_produto (marca, codigoBarras, preco, quantidadeEstoque, tipo_id) VALUES (?, ?, ?, ?, ?)";
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

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        // CORRIGIDO: codigoBarras, quantidadeEstoque
        String sql = "SELECT p.id, p.marca, p.codigoBarras, p.preco, p.quantidadeEstoque, p.tipo_id, t.nome as tipo_nome, t.formaVenda " +
                "FROM tb_produto p INNER JOIN tb_tipo t ON p.tipo_id = t.id";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Tipo tipo = new Tipo(rs.getInt("tipo_id"), rs.getString("tipo_nome"), rs.getString("formaVenda"));
                // CORRIGIDO: Puxando as colunas certas do ResultSet
                Produto p = new Produto(rs.getInt("id"), rs.getString("marca"), rs.getString("codigoBarras"), rs.getDouble("preco"), tipo);
                p.alterarQuantidadeEstoque(rs.getDouble("quantidadeEstoque"));
                produtos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    @Override
    public void alterar(Produto produto) {
        // CORRIGIDO: id_tipo alterado para tipo_id para bater com o banco de dados!
        String sql = "UPDATE tb_produto SET marca = ?, codigoBarras = ?, preco = ?, quantidadeEstoque = ?, tipo_id = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getMarca());
            ps.setString(2, produto.getCodigoBarras());
            ps.setDouble(3, produto.getPreco());
            ps.setDouble(4, produto.getQuantidadeEstoque());

            if (produto.getTipo() != null) {
                ps.setInt(5, produto.getTipo().getId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            ps.setInt(6, produto.getId());

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Produto ID " + produto.getId() + " atualizado com sucesso no banco de dados!");
            } else {
                System.out.println("Aviso: Nenhum produto foi alterado no banco (ID não encontrado).");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar produto no banco: " + e.getMessage(), e);
        }
    }

    public int contarTiposDeProdutos() {
        String sql = "SELECT COUNT(*) FROM tb_produto";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar tipos de produtos no Dashboard: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM tb_produto WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage());
        }
    }
}