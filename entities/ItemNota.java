package br.edu.ufersa.sistemaDeLogin.model.entities;

    public class ItemNota {
    private Produto produto;
    private int quantidade;
    private double valorUnitario;

    public ItemNota(Produto produto, int quantidade, double valorUnitario) {
        this.produto = produto;
        setQuantidade(quantidade);
        setValorUnitario(valorUnitario);
    }

        public double calcularSubtotal() {
        return this.quantidade * this.valorUnitario;

    }

    // getters
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getValorUnitario() { return valorUnitario; }

    // setters

        public void setQuantidade(int quantidade) {
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