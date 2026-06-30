package br.edu.ufersa.SistemaDeLogin.model.entities;

public class Funcionario {
    private int id;
    private String nome;    // Nome ou Email do Usuário
    private String tipo;    // Tipo ou Cargo do Funcionário
    private String senha;
    private String email;

    public Funcionario(String nome, String email, String tipo, String senha) {
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        this.senha = senha;
    }
    public Funcionario(int id, String nome, String email, String tipo, String senha) {
        setId(id);
        this.nome = nome;
        this.tipo = email;
        this.senha = tipo;
        this.email = senha;
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
    public String getEmail() { return email; }
    // Atualizado para checar o email em vez do nome, acompanhando o banco
    public boolean funcionario(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }


}
