package br.edu.ufersa.SistemaDeLogin.model.strategy;

public class PagamentoDinheiro implements EstrategiaPagamento {
    @Override
    public double calcularValorFinal(double valorTotal) {
        return valorTotal;
    }
}