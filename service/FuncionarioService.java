package br.edu.ufersa.sistemaDeLogin.model.service;

import br.edu.ufersa.sistemaDeLogin.model.DAO.FuncionarioDAO;
import br.edu.ufersa.sistemaDeLogin.model.entities.Funcionario;
import java.util.List;

public class FuncionarioService {
    private FuncionarioDAO funcionarioDAO;

    
    public FuncionarioService(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }
  
    public Funcionario login(String nome, String senha) {
        // Busca todos os funcionários do banco usando o DAO
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();

        // procura nome e senha iguais
        for (Funcionario f : funcionarios) {
            if (f.getNome().equals(nome) && f.getSenha().equals(senha)) {
                return f; 
            }
        }
        
        // não achou ninguem
        throw new IllegalArgumentException("Usuário ou senha incorretos!");
    }

    // cadastro sem repetir nome
    public void cadastrar(Funcionario novoFuncionario) {
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();

        // validação para não permitir dois nomes
        for (Funcionario f : funcionarios) {
            if (f.getNome().equalsIgnoreCase(novoFuncionario.getNome())) {
                throw new IllegalArgumentException("Já existe um funcionário cadastrado com este nome!");
            }
        }

        funcionarioDAO.salvar(novoFuncionario);
    }
}
