package br.edu.ufersa.SistemaDeLogin.model.entities;

public class Funcionario {
    private int id;
    private String nome;
    private String tipo;
    private String senha;

    public Funcionario(String nome, String tipo, String senha) {
        this.nome = nome;
        this.tipo = tipo;
        this.senha = senha;
    }
    public Funcionario(int id, String nome, String tipo, String senha) {
        setId(id);
        this.nome = nome;
        this.tipo = tipo;
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }


    public boolean funcionario(String nome, String senha) {
    return this.nome.equals(nome) && this.senha.equals(senha);
}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("ID inválido: O ID não pode ser negativo.");
        }
        // ID não pode ser negativo
    }


}