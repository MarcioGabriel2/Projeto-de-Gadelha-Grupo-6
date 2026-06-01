public class Tipo {
    private int id;
    private String nome;
    private String formaVenda;

    public Tipo(String nome, String formaVenda) {
        this.nome = nome;
        this.formaVenda = formaVenda;
    }
    public Tipo(int id, String nome, String formaVenda) {
        setId(id);
        this.nome = nome;
        this.formaVenda = formaVenda;
    }


    public void alterarFormaVenda(String f) {
        if (f != null && !f.isEmpty()) this.formaVenda = f;
    }


// Getters e Setters necessários
    public int getId() { return id; }
    public void setId(int id) throws RuntimeException {
        if (id > 0) this.id = id;   // ID não pode ser negativo
        else throw new RuntimeException("ID inválido!");
    }

    public String getNome() { return nome; }
}
