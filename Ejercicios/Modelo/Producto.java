package modelo;

import java.text.Normalizer;
import java.util.Locale;

public class Producto {

    private String idProducto;
    private String nombre;
    private String descripcion;
    private int stock;
    private double precio;
    private String idCategoria;

    public Producto(
            String idProducto,
            String nombre,
            String descripcion,
            int stock,
            double precio,
            String idCategoria) {

        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
        this.idCategoria = idCategoria;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }

    public double getPrecio() {
        return precio;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    // Obtener cualquier campo mediante su nombre
    public Object obtenerValor(String campo) {

        switch (normalizar(campo)) {

            case "IDPRODUCTO":
                return idProducto;

            case "NOMBRE":
                return nombre;

            case "DESCRIPCION":
                return descripcion;

            case "STOCK":
                return stock;

            case "PRECIO":
                return precio;

            case "IDCATEGORIA":
                return idCategoria;

            default:
                throw new IllegalArgumentException(
                    "Campo desconocido: " + campo
                );
        }
    }

    // Convertir el valor a texto para mostrarlo
    public String obtenerTexto(String campo) {

        Object valor = obtenerValor(campo);

        if (valor instanceof Double) {
            return String.format(
                Locale.US, "%.2f", (Double) valor
            );
        }

        return String.valueOf(valor);
    }

    public static boolean esCampoNumerico(String campo) {

        String c = normalizar(campo);

        return c.equals("STOCK") || c.equals("PRECIO");
    }

    // Ignorar tildes, espacios y guiones bajos al comparar nombres
    private static String normalizar(String texto) {

        return Normalizer.normalize(
                    texto.trim(), Normalizer.Form.NFD
                )
                .replaceAll("\\p{M}", "")
                .replace("_", "")
                .replace(" ", "")
                .toUpperCase(Locale.ROOT);
    }
}