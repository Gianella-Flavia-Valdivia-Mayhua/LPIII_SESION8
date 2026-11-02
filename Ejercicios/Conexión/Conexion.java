package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    private static final String URL =
            "jdbc:sqlite:Cafeteria.db";

    public static Connection conectar() throws SQLException {

        Connection conn = DriverManager.getConnection(URL);

        try (Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON");

            return conn;

        } catch (SQLException e) {

            try {
                conn.close();
            } catch (SQLException errorCierre) {
                e.addSuppressed(errorCierre);
            }

            throw e;
        }
    }
}