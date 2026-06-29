package br.edu.ufersa.SistemaDeLogin.model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private final static String URL = "jdbc:mysql://localhost:3306/supermercado";
    private final static String USER = "root";  // root pq é o user default do xampp
    private final static String PASS = "1234";      // Sem senha para os testes

    public static Connection getConnection() {
        try {
            // Força o carregamento do driver do MySQL para evitar conflitos com o módulo
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Retorna uma conexão NOVA a cada chamada, essencial para o try-with-resources
            return DriverManager.getConnection(URL, USER, PASS);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do MySQL não encontrado no sistema: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco. A senha está correta? Detalhe: " + e.getMessage());
        }
    }
}
