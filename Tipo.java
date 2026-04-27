public class Tipo {
    private String nome;
    private String formaVenda;

    public Tipo(String nome, String formaVenda) {
        this.nome = nome;
        this.formaVenda = formaVenda;
    }

    public void alterarFormaVenda(String f) {
        if (f != null && !f.isEmpty()) this.formaVenda = f;
    }

    public String getNome() { return nome; }
}