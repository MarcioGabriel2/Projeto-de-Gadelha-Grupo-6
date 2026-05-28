public class Funcionario {
    private int id;
    private String nome;
    private String tipo;
    private String senha;

    public Funcionario(String nome, String tipo, String senha) {
        this.nome = nome;
        this.tipo = tipo;
        this.senha = senha;
    }
    public Funcionario(int id, String nome, String tipo, String senha) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.senha = senha;
    }

    public String getTipo() {
    return tipo;
}

public boolean funcionario(String nome, String senha) {
    return this.nome.equals(nome) && this.senha.equals(senha);
}
}