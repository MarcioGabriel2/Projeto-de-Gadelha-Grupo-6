package br.edu.ufersa.sistemaDeLogin.model.service;

import br.edu.ufersa.sistemaDeLogin.model.DAO.NotaDAO;
import br.edu.ufersa.sistemaDeLogin.model.entities.Nota;
import br.edu.ufersa.sistemaDeLogin.model.entities.ItemNota;

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