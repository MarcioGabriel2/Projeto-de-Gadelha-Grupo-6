package br.edu.ufersa.SistemaDeLogin.model.DAO;

import br.edu.ufersa.SistemaDeLogin.model.entities.Tipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoDAO {

    public void salvar(Tipo t) {
        String sql = "INSERT INTO tb_tipo (nome, forma_venda) VALUES (?, ?)";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, t.getNome());
            stmt.setString(2, t.getFormaVenda());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tipo: " + e.getMessage());
        }
    }

    //
    public List<Tipo> listarTodos() {
        List<Tipo> tipos = new ArrayList<>();
        String sql = "SELECT * FROM tb_tipo";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tipo t = new Tipo(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("forma_venda")
                );
                tipos.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tipos: " + e.getMessage());
        }
        return tipos;
    }
    public boolean atualizar(Tipo tipo) {
        String sql = "UPDATE tb_tipo SET nome = ?, forma_venda = ? WHERE id = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, tipo.getNome());
            stmt.setString(2, tipo.getFormaVenda());
            stmt.setInt(3, tipo.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tipo: " + e.getMessage());
        }
    }
}