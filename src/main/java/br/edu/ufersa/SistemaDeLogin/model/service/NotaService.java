package br.edu.ufersa.SistemaDeLogin.model.service;

import br.edu.ufersa.SistemaDeLogin.model.DAO.NotaDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Nota;
import br.edu.ufersa.SistemaDeLogin.model.strategy.EstrategiaPagamento;

public class NotaService {
    private NotaDAO notaDAO;

    public NotaService(NotaDAO notaDAO) {
        this.notaDAO = notaDAO;
    }

    // finaliza a nota calculando o total e salvando
    public void finalizarVenda(Nota nota) {
        // validação para não fechar nota sem nenhum produto
        if (nota.getItens().isEmpty()) {
            throw new IllegalArgumentException("Não é possível finalizar uma nota sem itens!");
        }

        nota.calcularTotal();


        notaDAO.registrarNota(nota, "VENDA");
    }

    public void finalizarVenda(Nota nota, EstrategiaPagamento formaPagamento) {
        if (nota.getItens().isEmpty()) {
            throw new IllegalArgumentException("Não é possível finalizar uma nota sem itens!");
        }

        // calcula o total normal dos itens
        double totalBruto = nota.calcularTotal();

        // aplica a estratégia do strategy
        double totalFinal = formaPagamento.calcularValorFinal(totalBruto);

        // atualiza o valor da nota com o cálculo da estratégia
        nota.setValorTotal(totalFinal);

        // passando o tipo "VENDA"
        notaDAO.registrarNota(nota, "VENDA");
    }
}