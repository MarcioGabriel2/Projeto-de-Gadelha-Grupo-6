package br.edu.ufersa.SistemaDeLogin.model.strategy;

public class PagamentoPix implements EstrategiaPagamento {
    @Override
    public double calcularValorFinal(double valorTotal) {
        return valorTotal - (valorTotal * 0.05);
    }
}