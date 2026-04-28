public class Produto {
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

    public void alterarPreco(double preco) {
        if (preco >= 0) this.preco = preco;
    }

    public void alterarQuantidade(int qtd) {
        if (this.quantidadeEstoque + qtd >= 0) {
            this.quantidadeEstoque += qtd;
        }
    }

    // getters
    public String getCodigoBarras() { return codigoBarras; }
    public double getPreco() { return preco; }
    public double getQuantidadeEstoque() { return quantidadeEstoque; }
    public String getMarca() { return marca; }
}