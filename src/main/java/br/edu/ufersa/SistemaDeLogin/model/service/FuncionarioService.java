package br.edu.ufersa.SistemaDeLogin.model.service;

import br.edu.ufersa.SistemaDeLogin.model.DAO.FuncionarioDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;

public class FuncionarioService {

    private FuncionarioDAO funcionarioDAO;

    public FuncionarioService(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    public Funcionario login(String nome, String senha) {
        Funcionario f = funcionarioDAO.buscarPorNomeESenha(nome, senha);

        if (f != null) {
            return f;
        }

        throw new IllegalArgumentException("Usuário ou senha incorretos!");
    }

    public void cadastrar(Funcionario novoFuncionario) {
        // confere se o nome já existe
        if (funcionarioDAO.existeNome(novoFuncionario.getNome())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com este nome!");
        }

        funcionarioDAO.salvar(novoFuncionario);
    }

    // valida e chama a atualização de senha
    public void alterarSenha(String nome, String novaSenha) {
        if (nome == null || nome.trim().isEmpty() || novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new IllegalArgumentException("Os campos não podem ser vazios!");
        }

        funcionarioDAO.atualizarSenha(nome.trim(), novaSenha);
    }
}