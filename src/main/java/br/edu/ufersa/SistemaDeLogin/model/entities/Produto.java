package br.edu.ufersa.SistemaDeLogin.model.entities;
public class Produto {
    private int id;
    private String marca;
    private String codigoBarras;
    private double quantidadeEstoque;
    private double preco;
    private Tipo tipo;

    public Produto(String marca, String codigoBarras, double preco, Tipo tipo) {
        this.marca = marca;
        this.codigoBarras = codigoBarras;
        this.preco = (preco >= 0) ? preco : 0;
        this.tipo = tipo;
    }
    public Produto(int id, String marca, String codigoBarras, double preco, Tipo tipo) {
        this.id = id;
        this.marca = marca;
        this.codigoBarras = codigoBarras;
        this.preco = (preco >= 0) ? preco : 0;
        this.tipo = tipo;
    }
    public void alterarPreco(double preco) {
        if (preco >= 0) this.preco = preco;
    }

    public void alterarQuantidadeEstoque(double qtd) {
        if (this.quantidadeEstoque + qtd >= 0) {
            this.quantidadeEstoque += qtd;
        }
    }

    // getters

    public String getCodigoBarras() { return codigoBarras; }
    public double getPreco() { return preco; }
    public double getQuantidadeEstoque() { return quantidadeEstoque; }
    public String getMarca() { return marca; }
    public int getId() { return id; }
    public Tipo getTipo() { return tipo; }

    // setters

    public void setId(int id) { this.id = id; }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setPreco(double preco) {
        this.preco = (preco >= 0) ? preco : 0;     // Checa se o preço é positivo
    }

    public void setQuantidadeEstoque(double quantidadeEstoque) {
        if (quantidadeEstoque >= 0) {
            this.quantidadeEstoque = quantidadeEstoque;
        }
    }
}