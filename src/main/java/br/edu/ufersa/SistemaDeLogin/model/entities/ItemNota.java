package br.edu.ufersa.SistemaDeLogin.model.entities;

    public class ItemNota {
    private Produto produto;
    private double quantidade;
    private double valorUnitario;

    public ItemNota(Produto produto, double quantidade, double valorUnitario) {
        this.produto = produto;
        setQuantidade(quantidade);
        setValorUnitario(valorUnitario);
    }

        public double calcularSubtotal() {
        return this.quantidade * this.valorUnitario;

    }

    // getters
    public Produto getProduto() { return produto; }
    public double getQuantidade() { return quantidade; }
    public double getValorUnitario() { return valorUnitario; }

    // setters
        public void setQuantidade(double quantidade) {
            if (quantidade >= 0) {
                this.quantidade = quantidade;
            } else {
                throw new IllegalArgumentException("A quantidade não pode ser negativa.");
            }
        }

        public void setValorUnitario(double valorUnitario) {
            if (valorUnitario >= 0) {
                this.valorUnitario = valorUnitario;
            } else {
                throw new IllegalArgumentException("O valor unitário não pode ser negativo.");
            }
        }
}