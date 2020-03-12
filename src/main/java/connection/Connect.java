package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static service.Properties.*;
import static service.Properties.DB_PASSWORD;

public class Connect {
    public Connect() {
    }

    public Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
