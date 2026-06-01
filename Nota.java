import java.util.ArrayList;
import java.util.List;

public class Nota {
    private int id;
    private List<ItemNota> itens = new ArrayList<>();
    private double valorTotal;

    public Nota() {}

    public Nota(int id) {
        setId(id);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) throws RuntimeException {
        if (id >= 0) this.id = id;   // ID não pode ser negativo
        else throw new RuntimeException("ID inválido!");
    }

    /*

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
        this.valorTotal = 0;
        for (ItemNota item : itens) {
            this.valorTotal += item.calcularSubtotal();
        }
        return this.valorTotal;
    }
    */
}
