import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Produto> produtos = new ArrayList<>();
    private List<Tipo> tipos = new ArrayList<>();
    private List<Funcionario> usuarios = new ArrayList<>(); // Tipo Funcionario
    private List<Nota> notas = new ArrayList<>();
    private double totalVendido;
    private double totalComprado;

    public void cadastrarProduto(Produto p) {
        produtos.add(p);
    }

    public Produto pesquisarProdutoCodigo(String codigo) {
        for (Produto p : produtos) {
            if (p.getCodigoBarras().equals(codigo)) return p;
        }
        return null;
    }

    public void venderProduto(Produto p, double qtd) {
        if (p.getQuantidadeEstoque() >= qtd) {
            p.alterarQuantidade((int)-qtd);
            this.totalVendido += (p.getPreco() * qtd);
        } else {
            System.out.println("Estoque insuficiente para a venda!");
        }
    }

    public void comprarProduto(Produto p, double qtd) {
        p.alterarQuantidade((int)qtd);
        this.totalComprado += (p.getPreco() * qtd);
    }

    // getter
    public double getTotalVendido() { return totalVendido; }
}