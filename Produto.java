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
        alterarPreco(preco);    // Aproveitando validação do método
        this.tipo = tipo;
    }
    public Produto(int id, String marca, String codigoBarras, double preco, Tipo tipo) {
        setID(id);
        this.marca = marca;
        this.codigoBarras = codigoBarras;
        alterarPreco(preco);    // Aproveitando validação do método
        this.tipo = tipo;
    }

    public void alterarPreco(double preco) throws RuntimeException {
        if (preco >= 0) this.preco = preco;
        else throw new RuntimeException ("Preço inválido!");
    }   // Preço não pode ser negativo

    public void alterarQuantidadeEstoque (double qtd) throws RuntimeException{
        if (this.quantidadeEstoque + qtd >= 0) {
            this.quantidadeEstoque += qtd;  // Aumenta o tamanho máximo da quantidade do estoque
        else throw new RuntimeException ("Estoque inválido!");
        }
    }

    // getters
    public String getCodigoBarras() { return codigoBarras; }
    public double getPreco() { return preco; }
    public double getQuantidadeEstoque() { return quantidadeEstoque; }
    public String getMarca() { return marca; }
    public int getId() { return id; }

    // setters
    public void setId(int id) throws RuntimeException {
        if (id >= 0) this.id = id;   // ID não pode ser negativo
        else throw new RuntimeException("ID inválido!");
}

}
