package br.edu.ufersa.SistemaDeLogin.model.DAO;

import br.edu.ufersa.SistemaDeLogin.model.entities.ItemNota;
import br.edu.ufersa.SistemaDeLogin.model.entities.Nota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    // Adicionamos o parâmetro "tipo" para diferenciar Venda de Compra
    public void registrarNota(Nota nota, String tipo) {
        String sqlNota = "INSERT INTO tb_nota (valorTotal, tipo, data_venda) VALUES (?, ?, NOW())";
        String sqlItem = "INSERT INTO tb_item_nota (nota_id, produto_id, quantidade, valor_unitario) VALUES (?, ?, ?, ?)";

        // Se for COMPRA, soma (+). Se for VENDA, subtrai (-)
        String operador = tipo.equalsIgnoreCase("COMPRA") ? "+" : "-";
        String sqlAtualizarEstoque = "UPDATE tb_produto SET quantidadeEstoque = quantidadeEstoque " + operador + " ? WHERE id = ?";

        try (Connection con = Conexao.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement stmtNota = con.prepareStatement(sqlNota, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmtItem = con.prepareStatement(sqlItem);
                 PreparedStatement stmtEstoque = con.prepareStatement(sqlAtualizarEstoque)) {

                // 1. Salva a nota principal
                stmtNota.setDouble(1, nota.getValorTotal());
                stmtNota.setString(2, tipo); // "COMPRA" ou "VENDA"
                stmtNota.executeUpdate();

                ResultSet rs = stmtNota.getGeneratedKeys();
                int notaIdGerado = 0;
                if (rs.next()) {
                    notaIdGerado = rs.getInt(1);
                    nota.setId(notaIdGerado);
                }

                // 2. Salva os itens e atualiza o estoque de cada um
                for (ItemNota item : nota.getItens()) {
                    stmtItem.setInt(1, notaIdGerado);
                    stmtItem.setInt(2, item.getProduto().getId());
                    stmtItem.setInt(3, item.getQuantidade());
                    stmtItem.setDouble(4, item.getValorUnitario());
                    stmtItem.executeUpdate();

                    // O Banco atualiza o estoque automaticamente (pra mais ou pra menos)
                    stmtEstoque.setInt(1, item.getQuantidade());
                    stmtEstoque.setInt(2, item.getProduto().getId());
                    stmtEstoque.executeUpdate();
                }

                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw new RuntimeException("Erro ao processar a nota fiscal: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro de conexão ao salvar nota: " + e.getMessage());
        }
    }

    public double buscarTotalVendas() {
        String sql = "SELECT SUM(valorTotal) FROM tb_nota WHERE tipo = 'VENDA'";
        return executarSoma(sql);
    }

    public double buscarTotalCompras() {
        String sql = "SELECT SUM(valorTotal) FROM tb_nota WHERE tipo = 'COMPRA'";
        return executarSoma(sql);
    }

    private double executarSoma(String sql) {
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar somatório no banco: " + e.getMessage());
        }
        return 0.0;
    }

    // --- ESSE É O MÉTODO QUE VAI ALIMENTAR AS TABELAS DE HISTÓRICO ---
    public List<Nota> listarHistorico(String tipo) {
        List<Nota> historico = new ArrayList<>();
        // Adapte a query caso sua tabela tenha o campo data
        String sql = "SELECT id, valorTotal FROM tb_nota WHERE tipo = ? ORDER BY id DESC";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Nota n = new Nota();
                    n.setId(rs.getInt("id"));
                    // O setTotal depende da sua entidade Nota, mas geralmente se usa algo assim:
                    // n.setValorTotal(rs.getDouble("valorTotal"));
                    historico.add(n);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar histórico: " + e.getMessage());
        }
        return historico;
    }
}