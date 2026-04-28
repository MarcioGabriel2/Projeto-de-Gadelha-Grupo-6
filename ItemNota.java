public class ItemNota {
    private Produto produto;
    private double quantidade;
    private double valorUnitario;

    public ItemNota(Produto produto, double quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = produto.getPreco();
    }

    public double calcularSubtotal() {
        return this.quantidade * this.valorUnitario;
    }
}
