package br.edu.ufersa.SistemaDeLogin.model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private final static String URL = "jdbc:mysql://localhost:3306/supermercado";
    private final static String USER = "root";  // root pq é o user default do xampp
    private final static String PASS = "";      // Sem senha para os testes

    // variavel da conexao unica
    private static Connection con = null;

    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException e) {
                // se der erro de banco, o programa avisa o que foi
                throw new RuntimeException("Erro ao conectar com o banco: " + e.getMessage());
            }
        }
        return con;
    }
}
