package modelo;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorProducto {

    private Producto[] productos = new Producto[0];

    // Cargar productos desde SQLite al arreglo de objetos
    public void cargarProductos() throws SQLException {

        List<Producto> lista = new ArrayList<>();

        String sql =
            "SELECT ID_PRODUCTO, Nombre, Descripcion, Stock, "
            + "Precio, \"ID_Categoría\" FROM Producto";

        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                Producto producto = new Producto(
                    rs.getString("ID_PRODUCTO"),
                    rs.getString("Nombre"),
                    rs.getString("Descripcion"),
                    rs.getInt("Stock"),
                    rs.getDouble("Precio"),
                    rs.getString("ID_Categoría")
                );

                lista.add(producto);
            }
        }

        productos = lista.toArray(new Producto[0]);
    }

    // Devolver una copia del arreglo actual
    public Producto[] obtenerProductos() {
        return productos.clone();
    }

    // Consulta configurable realizada en Java
    public List<String> consultarProductos(
            List<String> campos,
            String campoFiltro,
            String operador,
            String valorFiltro,
            String campoOrden,
            boolean ascendente,
            int limite) {

        if (campos == null || campos.isEmpty()) {
            throw new IllegalArgumentException(
                "Debe seleccionar al menos un campo."
            );
        }

        if (limite < 0) {
            throw new IllegalArgumentException(
                "El limite no puede ser negativo."
            );
        }

        Double valorNumerico = null;

        if (campoFiltro != null
                && !campoFiltro.isEmpty()
                && Producto.esCampoNumerico(campoFiltro)) {

            try {
                valorNumerico = Double.parseDouble(
                    valorFiltro.trim().replace(',', '.')
                );

            } catch (NumberFormatException e) {

                throw new IllegalArgumentException(
                    "El valor del filtro debe ser numerico."
                );
            }
        }

        // Filtrar el arreglo en memoria
        List<Producto> resultado = new ArrayList<>();

        for (Producto p : productos) {

            if (cumpleFiltro(
                    p, campoFiltro, operador,
                    valorFiltro, valorNumerico)) {

                resultado.add(p);
            }
        }

        // Ordenar por el campo elegido
        if (campoOrden != null && !campoOrden.isEmpty()) {

            final String campo = campoOrden;
            final boolean ordenAscendente = ascendente;

            Collections.sort(resultado, (a, b) -> {

                Object va = a.obtenerValor(campo);
                Object vb = b.obtenerValor(campo);

                int comparacion;

                if (va instanceof Number && vb instanceof Number) {

                    comparacion = Double.compare(
                        ((Number) va).doubleValue(),
                        ((Number) vb).doubleValue()
                    );

                } else {

                    comparacion = String.valueOf(va)
                            .compareToIgnoreCase(String.valueOf(vb));
                }

                return ordenAscendente
                        ? comparacion
                        : -comparacion;
            });
        }

        // Limitar el número de resultados; cero significa sin límite
        if (limite > 0 && resultado.size() > limite) {

            resultado = new ArrayList<>(
                resultado.subList(0, limite)
            );
        }

        // Mostrar únicamente los campos seleccionados
        List<String> lineas = new ArrayList<>();

        for (Producto p : resultado) {

            StringBuilder fila = new StringBuilder();

            for (String campo : campos) {

                if (fila.length() > 0) {
                    fila.append(" | ");
                }

                fila.append(campo)
                    .append(": ")
                    .append(p.obtenerTexto(campo));
            }

            lineas.add(fila.toString());
        }

        return lineas;
    }

    // Comprobar si un producto cumple el filtro
    private boolean cumpleFiltro(
            Producto p,
            String campoFiltro,
            String operador,
            String valorFiltro,
            Double valorNumerico) {

        // Si no se eligió un filtro, pasan todos los productos
        if (campoFiltro == null || campoFiltro.isEmpty()) {
            return true;
        }

        Object actual = p.obtenerValor(campoFiltro);

        if (actual instanceof Number) {

            double numeroActual =
                    ((Number) actual).doubleValue();

            double numeroBuscado = valorNumerico;

            switch (operador) {

                case "=":
                    return numeroActual == numeroBuscado;

                case ">":
                    return numeroActual > numeroBuscado;

                case "<":
                    return numeroActual < numeroBuscado;

                case ">=":
                    return numeroActual >= numeroBuscado;

                case "<=":
                    return numeroActual <= numeroBuscado;

                default:
                    throw new IllegalArgumentException(
                        "Operador numerico no valido."
                    );
            }

        } else {

            String textoActual = String.valueOf(actual);

            if (operador.equalsIgnoreCase("CONTIENE")) {

                return textoActual.toLowerCase()
                        .contains(valorFiltro.toLowerCase());
            }

            if (operador.equals("=")) {

                return textoActual.equalsIgnoreCase(valorFiltro);
            }

            throw new IllegalArgumentException(
                "Para texto use = o CONTIENE."
            );
        }
    }
}