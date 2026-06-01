public class ItemNota {
    private Produto produto;
    private int quantidade;
    private double valorUnitario;

    public ItemNota(Produto produto, int quantidade, double valorUnitario) {
        this.produto = produto;
        setQuantidade(quantidade);
        setValorUnitario(valorUnitario);
    }

    /*
        public double calcularSubtotal() {
        return this.quantidade * this.valorUnitario;
    }
    */
    // getters
    public Produto getProduto() { return produto; }
    public double getQuantidade() { return quantidade; }

    // setters

    public void setQuantidade(int quantidade) throws RuntimeException {
        if (quantidade >= 0) this.quantidade = quantidade;
        else throw new RuntimeException;
        // Não pode ser negativo
    }
    public void setValorUnitario(double valorUnitario) throws RuntimeException {
        if (valorUnitario >= 0) this.valorUnitario = valorUnitario;
        else throw new RuntimeException;
        // Não pode ser negativo
    }
}
