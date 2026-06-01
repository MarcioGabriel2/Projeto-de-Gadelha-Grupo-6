package br.edu.ufersa.sistemaDeLogin.model.service;

import br.edu.ufersa.sistemaDeLogin.model.DAO.TipoDAO;
import br.edu.ufersa.sistemaDeLogin.model.entities.Tipo;
import java.util.List;

public class TipoService {
    private TipoDAO tipoDAO;

    public TipoService(TipoDAO tipoDAO) {
        this.tipoDAO = tipoDAO;
    }

    // cadastro de tipo sem repetir o nome da categoria
    public void cadastrar(Tipo novoTipo) {
        List<Tipo> tipos = tipoDAO.listarTodos();

        // validação para não duplicar nome de tipo
        for (Tipo t : tipos) {
            if (t.getNome().equalsIgnoreCase(novoTipo.getNome())) {
                throw new IllegalArgumentException("Já existe este tipo de produto cadastrado!");
            }
        }

        tipoDAO.salvar(novoTipo);
    }
}