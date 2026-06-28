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
        notaDAO.salvar(nota);
    }

    // Mas também dá pra escolher como o cliente vai pagar
    public void finalizarVenda(Nota nota, EstrategiaPagamento formaPagamento) {
        if (nota.getItens().isEmpty()) {
            throw new IllegalArgumentException("Não é possível finalizar uma nota sem itens!");
        }

        // 1. Calcula o total normal dos itens
        double totalBruto = nota.calcularTotal();

        // 2. Aplica a estratégia do Strategy
        double totalFinal = formaPagamento.calcularValorFinal(totalBruto);

        // 3. Atualiza o valor da nota com o cálculo da estratégia
        // (Você pode precisar criar um setValorTotal(double) na classe Nota)
        nota.setValorTotal(totalFinal);

        // 4. Salva no banco de dados usando a Factory que fizemos!
        notaDAO.salvar(nota);
    }
}