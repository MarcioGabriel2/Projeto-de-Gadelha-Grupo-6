package br.edu.ufersa.SistemaDeLogin.util; // Ajuste o pacote se necessário

import br.edu.ufersa.SistemaDeLogin.model.entities.Funcionario;

public class Sessao {
    private static Funcionario usuarioLogado;

    public static void setUsuarioLogado(Funcionario usuario) {
        usuarioLogado = usuario;
    }

    public static Funcionario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void encerrarSessao() {
        usuarioLogado = null;
    }
}