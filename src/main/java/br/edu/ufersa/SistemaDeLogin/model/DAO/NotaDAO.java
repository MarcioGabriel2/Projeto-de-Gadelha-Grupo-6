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
}
