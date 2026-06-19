package br.edu.ufersa.SistemaDeLogin.model.service;

import br.edu.ufersa.SistemaDeLogin.model.DAO.NotaDAO;
import br.edu.ufersa.SistemaDeLogin.model.entities.Nota;

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
}