package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private ConnectionFactory(){};
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost/parque_de_diversoes",
                    "postgres",
                    "rod89uuu"
            );
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}