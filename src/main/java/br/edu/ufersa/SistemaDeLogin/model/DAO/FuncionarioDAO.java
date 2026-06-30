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
        // Agora o comando envia 4 informações, incluindo o email!
        String sql = "INSERT INTO tb_funcionario (nome, email, tipo, senha) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getEmail());
            stmt.setString(3, f.getTipo());
            stmt.setString(4, f.getSenha());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário: " + e.getMessage());
        }
    }

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM tb_funcionario";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Adicionei o rs.getString("email") aqui para puxar o e-mail do banco
                Funcionario f = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
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

    public boolean autenticar(String email, String senha) {
        String sql = "SELECT * FROM tb_funcionario WHERE email = ? AND senha = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar: " + e.getMessage());
        }
    }
    public boolean atualizarSenha(String email, String novaSenha) {
        String sql = "UPDATE tb_funcionario SET senha = ? WHERE email = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, novaSenha);
            stmt.setString(2, email);

            // O executeUpdate devolve quantas linhas foram alteradas no banco
            int linhasAfetadas = stmt.executeUpdate();

            // Retorna true se encontrou o email e alterou a senha, ou false se o email não existir
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar senha: " + e.getMessage());
        }
    }
}
