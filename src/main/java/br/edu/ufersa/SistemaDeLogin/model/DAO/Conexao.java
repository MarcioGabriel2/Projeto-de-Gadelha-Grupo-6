package br.edu.ufersa.SistemaDeLogin.model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private final static String URL = "jdbc:mysql://localhost:3306/supermercado";
    private final static String USER = "root";
    private final static String PASS = "";

    // guarda a instancia
    private static Connection conexaoUnica;
    // impede outras conexões
    private Conexao() {}

    public static Connection getConnection() {
        try {
            if (conexaoUnica == null || conexaoUnica.isClosed()) {
                conexaoUnica = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Uma nova e ÚNICA conexão foi aberta com o banco!");
            }

            return conexaoUnica;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco: " + e.getMessage());
        }
    }
}