package br.edu.ufersa.SistemaDeLogin.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Nota {
    private int id;
    private List<ItemNota> itens = new ArrayList<>();
    private double valorTotal;

    private String tipo;
    private LocalDateTime dataVenda;

    public Nota() {}

    public Nota(int id) {
        setId(id);
    }

    // getters e setters
    public int getId() {return id;}

    public void setId(int id) throws RuntimeException {
        if (id >= 0) this.id = id;
        else throw new RuntimeException("ID inválido!");
    }

    public List<ItemNota> getItens() {return itens;}

    public double getValorTotal() {return valorTotal;}

    public void setValorTotal(double totalFinal) {this.valorTotal = totalFinal;}

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}

    public LocalDateTime getDataVenda() {return dataVenda;}

    public void setDataVenda(LocalDateTime dataVenda) {this.dataVenda = dataVenda;}

    public void adicionarItem(ItemNota item) {
        itens.add(item);
        calcularTotal();
    }

    public void removerItem(ItemNota item) {
        itens.remove(item);
        calcularTotal();
    }

    public void trocarProduto(ItemNota itemAntigo, ItemNota itemNovo) {
        int index = itens.indexOf(itemAntigo);
        if (index != -1) {
            itens.set(index, itemNovo);
            calcularTotal();
        }
    }

    public double calcularTotal() {
        this.valorTotal = 0.0;
        for (ItemNota item : this.itens) {
            this.valorTotal += item.calcularSubtotal();
        }
        return this.valorTotal;
    }
}