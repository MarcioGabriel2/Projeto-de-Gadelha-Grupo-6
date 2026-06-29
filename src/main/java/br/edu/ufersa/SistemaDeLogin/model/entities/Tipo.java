package br.edu.ufersa.SistemaDeLogin.model.entities;

public class Tipo {
    private int id;
    private String nome;
    private String formaVenda;

    public Tipo(String nome, String formaVenda) {
        this.nome = nome;
        this.formaVenda = formaVenda;
    }
    public Tipo(int id, String nome, String formaVenda) {
        this.id = id;
        this.nome = nome;
        this.formaVenda = formaVenda;
    }
    public void alterarFormaVenda(String f) {
        if (f != null && !f.isEmpty()) this.formaVenda = f;
    }

    // getters

    public int getId() { return id; }
    public String getFormaVenda() { return this.formaVenda; }
    public String getNome() { return nome; }

    // setters
    public void setId(int id) { this.id = id; }

    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty()) { this.nome = nome; }
    }

    public void setFormaVenda(String formaVenda) {
        this.formaVenda = formaVenda;
    }
}