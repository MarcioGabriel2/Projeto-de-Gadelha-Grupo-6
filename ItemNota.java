public class ItemNota {
    private Produto produto;
    private int quantidade;
    private double valorUnitario;

    public ItemNota(Produto produto, int quantidade, double valorUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public double calcularSubtotal() {
        return this.quantidade * this.valorUnitario;
    }

    public Produto getProduto() { return produto; }
    public double getQuantidade() { return quantidade; }
}
