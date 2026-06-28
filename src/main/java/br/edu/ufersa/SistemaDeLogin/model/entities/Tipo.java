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
// Getters e Setters necessários
    public int getId() { return id; }
    public String getFormaVenda() { return this.formaVenda; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
}