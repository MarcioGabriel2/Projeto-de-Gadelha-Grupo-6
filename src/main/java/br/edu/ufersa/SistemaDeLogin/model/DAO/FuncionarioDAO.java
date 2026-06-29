package br.edu.ufersa.SistemaDeLogin.model.DAO;

import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void salvar(Funcionario f) {
        String sql = "INSERT INTO tb_funcionario (nome, tipo, senha) VALUES (?, ?, ?)";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getTipo());
            stmt.setString(3, f.getSenha());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário: " + e.getMessage());
        }
    }

    public Funcionario buscarPorNomeESenha(String nome, String senha) {
        String sql = "SELECT * FROM tb_funcionario WHERE nome = ? AND senha = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("tipo"),
                            rs.getString("senha")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário para login: " + e.getMessage());
        }
        return null; // se n encontrar ninguem
    }

    // verificação de nome ja existente
    public boolean existeNome(String nome) {
        String sql = "SELECT id FROM tb_funcionario WHERE nome = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true se achou alguém com esse nome
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar nome existente: " + e.getMessage());
        }
    }

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM tb_funcionario";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getString("senha")
                );
                funcionarios.add(f);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage());
        }
        return funcionarios;
    }

    public void atualizarSenha(String nome, String novaSenha) {
        String sql = "UPDATE tb_funcionario SET senha = ? WHERE nome = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, novaSenha);
            stmt.setString(2, nome);

            int linhasAfetadas = stmt.executeUpdate(); // Retorna quantas linhas o banco alterou

            if (linhasAfetadas == 0) {
                throw new IllegalArgumentException("Nenhuma conta encontrada com este email/nome.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar senha no banco: " + e.getMessage());
        }
    }
}