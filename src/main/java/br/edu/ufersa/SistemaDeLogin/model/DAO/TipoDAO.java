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
        String sql = "INSERT INTO tb_tipo (nome, formaVenda) VALUES (?, ?)";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, t.getNome());
            stmt.setString(2, t.getFormaVenda());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tipo: " + e.getMessage());
        }
    }

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
                        rs.getString("formaVenda")
                );
                tipos.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tipos: " + e.getMessage());
        }
        return tipos;
    }

    public void alterar(Tipo t) {
        String sql = "UPDATE tb_tipo SET nome = ?, formaVenda = ? WHERE id = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, t.getNome());
            stmt.setString(2, t.getFormaVenda());
            stmt.setInt(3, t.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Tipo ID " + t.getId() + " atualizado com sucesso no banco!");
            } else {
                System.out.println("Aviso: Nenhum tipo foi alterado (ID não encontrado).");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar tipo no banco: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tb_tipo WHERE id = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar tipo: " + e.getMessage());
        }
    }
}