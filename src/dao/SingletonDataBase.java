package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonDataBase {
    // URL de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/etab_db";
    // Nom d'utilisateur
    private static final String USER = "Domi";
    private static final String PASSWORD = "1234";

    // notre instance unique de la connexion
    private static volatile Connection connection = null;

    // notre Constructeur
    private SingletonDataBase() {
        // Initialisation de la connexion
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            //  notre message d'erreur
            System.err.println("Erreur lors de la connexion à la base de données: " + e.getMessage());
        }
    }

    // la connexion
    public static Connection getInstance() {
        if (connection == null) {
            synchronized (SingletonDataBase.class) {
                //
                if (connection == null) {
                    new SingletonDataBase();
                }
            }
        }
        return connection;
    }
}