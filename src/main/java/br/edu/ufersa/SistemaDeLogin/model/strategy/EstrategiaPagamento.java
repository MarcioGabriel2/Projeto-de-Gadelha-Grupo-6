package br.edu.ufersa.SistemaDeLogin.model.strategy;

public interface EstrategiaPagamento {
    // recebe o valor original da nota e retorna o valor com desconto/juros
    double calcularValorFinal(double valorTotal);
}
