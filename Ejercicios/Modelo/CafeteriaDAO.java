package modelo;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CafeteriaDAO {

    private static final String CLAVE_CONFIRMACION = "cafe123";

    // Mostrar registros de una tabla
    public void mostrarTabla(String nombreTabla, String[] columnas) {

        String sql = "SELECT * FROM " + nombreTabla;

        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println(
                "\n--- REGISTROS DE: "
                + nombreTabla.toUpperCase() + " ---"
            );

            boolean hayRegistros = false;

            while (rs.next()) {

                hayRegistros = true;
                StringBuilder fila = new StringBuilder();

                for (String col : columnas) {
                    fila.append(col)
                        .append(": ")
                        .append(rs.getString(col))
                        .append(" | ");
                }

                System.out.println(fila);
            }

            if (!hayRegistros) {
                System.out.println("No hay registros.");
            }

        } catch (SQLException e) {

            System.out.println(
                "Error al mostrar datos: " + e.getMessage()
            );
        }
    }

    // Ejercicio 5: ejecutar una operación con confirmación
    public boolean ejecutarOperacionConClave(
            String sqlQuery,
            String claveIngresada,
            Object... params) {

        try (Connection conn = Conexion.conectar()) {

            conn.setAutoCommit(false);

            try (PreparedStatement pstmt =
                    conn.prepareStatement(sqlQuery)) {

                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }

                // Ejecutar el cambio dentro de la transacción
                int filas = pstmt.executeUpdate();

                if (filas <= 0) {

                    conn.rollback();

                    System.out.println(
                        "No se encontro el registro o no hubo cambios."
                    );

                    return false;
                }

                // Confirmar o revertir según la clave
                if (CLAVE_CONFIRMACION.equals(claveIngresada)) {

                    conn.commit();

                    System.out.println(
                        "Operacion confirmada. Cambios guardados."
                    );

                    return true;

                } else {

                    conn.rollback();

                    System.out.println(
                        "Clave incorrecta. Cambios revertidos."
                    );

                    return false;
                }

            } catch (SQLException e) {

                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {

            System.out.println(
                "Error en la operacion: " + e.getMessage()
            );

            return false;
        }
    }
}