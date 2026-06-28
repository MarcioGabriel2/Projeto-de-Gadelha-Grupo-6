package br.edu.ufersa.SistemaDeLogin.model.DAO;

import br.edu.ufersa.SistemaDeLogin.model.entities.ItemNota;
import br.edu.ufersa.SistemaDeLogin.model.entities.Nota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class NotaDAO {

    public void salvar(Nota nota) {
        String sqlNota = "INSERT INTO tb_nota (valor_total) VALUES (?)";
        String sqlItem = "INSERT INTO tb_item_nota (nota_id, produto_id, quantidade, valor_unitario) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement stmtNota = con.prepareStatement(sqlNota, Statement.RETURN_GENERATED_KEYS)) {

                // 1. salva a nota principal
                stmtNota.setDouble(1, nota.getValorTotal());
                stmtNota.executeUpdate();

                ResultSet rs = stmtNota.getGeneratedKeys();
                int notaIdGerado = 0;
                if (rs.next()) {
                    notaIdGerado = rs.getInt(1);
                    nota.setId(notaIdGerado); // Atualiza o objeto no Java
                }

                // 2. salva todos os items vinculados a id
                try (PreparedStatement stmtItem = con.prepareStatement(sqlItem)) {
                    for (ItemNota item : nota.getItens()) {
                        stmtItem.setInt(1, notaIdGerado);
                        stmtItem.setInt(2, item.getProduto().getId());
                        stmtItem.setDouble(3, item.getQuantidade());
                        stmtItem.setDouble(4, item.getValorUnitario());
                        stmtItem.executeUpdate();
                    }
                }

                con.commit();

            } catch (SQLException e) {
                con.rollback(); // Se der qualquer erro no meio, desfaz tudo para não gerar nota vazia
                throw new RuntimeException("Erro ao processar a nota: " + e.getMessage());
            } finally {
                con.setAutoCommit(true); // Volta a conexão ao comportamento padrão
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro de conexão ao salvar nota: " + e.getMessage());
        }
    }

    public double buscarTotalVendas() {
        String sql = "SELECT SUM(valorTotal) FROM tb_nota";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar total de vendas no Dashboard: " + e.getMessage());
        }
        return 0.0;
    }

    public double buscarTotalCompras() {
        // Como o módulo de compras ainda vai ser ajustado, retornamos 0.0 temporariamente
        // para não quebrar a lógica do cálculo do Saldo no Dashboard.
        return 0.0;
    }
}
