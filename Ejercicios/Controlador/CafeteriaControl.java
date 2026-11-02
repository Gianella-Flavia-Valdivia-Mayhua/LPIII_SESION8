package controlador;

import modelo.CafeteriaDAO;
import modelo.GestorProducto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CafeteriaControl {

    private final CafeteriaDAO dao = new CafeteriaDAO();
    private final Scanner scanner;

    public CafeteriaControl(Scanner scanner) {
        this.scanner = scanner;
    }

    // Gestionar una tabla existente
    public void gestionarTabla(
            String nombreTabla,
            String[] columnas,
            String pkNombre) {

        int opcion;

        do {

            System.out.println(
                "\n--- GESTION DE: "
                + nombreTabla.toUpperCase() + " ---"
            );

            System.out.println("1. Mostrar registros");
            System.out.println("2. Insertar registro");
            System.out.println("3. Actualizar registro");
            System.out.println("4. Eliminar registro");
            System.out.println("5. Volver al menu principal");

            opcion = leerEntero("Elija una opcion: ");

            try {

                switch (opcion) {

                    case 1:
                        dao.mostrarTabla(nombreTabla, columnas);
                        break;

                    case 2:
                        insertarDinamico(nombreTabla);
                        break;

                    case 3:
                        actualizarDinamico(nombreTabla, pkNombre);
                        break;

                    case 4:

                        System.out.print(
                            "Ingrese el " + pkNombre
                            + " del registro a eliminar: "
                        );

                        String idEliminar = scanner.nextLine();

                        String claveEliminar = solicitarClave();

                        dao.ejecutarOperacionConClave(
                            "DELETE FROM " + nombreTabla
                            + " WHERE " + pkNombre + " = ?",
                            claveEliminar,
                            idEliminar
                        );

                        break;

                    case 5:
                        System.out.println(
                            "Volviendo al menu principal..."
                        );
                        break;

                    default:
                        System.out.println("Opcion invalida.");
                }

            } catch (NumberFormatException e) {

                System.out.println(
                    "Debe ingresar un valor numerico valido."
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                    "Datos invalidos: " + e.getMessage()
                );
            }

        } while (opcion != 5);
    }

    // Insertar en la tabla seleccionada
    private void insertarDinamico(String tabla) {

        String sql;
        Object[] parametros;

        switch (tabla) {

            case "Cliente": {

                System.out.print("ID_CLIENTE: ");
                String id = scanner.nextLine();

                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();

                System.out.print("Documento de Identidad: ");
                String documento = scanner.nextLine();

                System.out.print("Numero celular: ");
                String celular = scanner.nextLine();

                sql = "INSERT INTO Cliente VALUES (?, ?, ?, ?)";

                parametros = new Object[]{
                    id, nombre, documento, celular
                };

                break;
            }

            case "Vendedor": {

                System.out.print("ID_VENDEDOR: ");
                String id = scanner.nextLine();

                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();

                System.out.print("Documento Identidad: ");
                String documento = scanner.nextLine();

                sql = "INSERT INTO Vendedor VALUES (?, ?, ?)";

                parametros = new Object[]{
                    id, nombre, documento
                };

                break;
            }

            case "Categoria": {

                System.out.print("ID_Categoria: ");
                String id = scanner.nextLine();

                System.out.print("Descripcion: ");
                String descripcion = scanner.nextLine();

                sql = "INSERT INTO Categoria VALUES (?, ?)";

                parametros = new Object[]{
                    id, descripcion
                };

                break;
            }

            case "Producto": {

                System.out.print("ID_PRODUCTO: ");
                String id = scanner.nextLine();

                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();

                System.out.print("Descripcion: ");
                String descripcion = scanner.nextLine();

                int stock = leerEntero("Stock: ");

                double precio = leerDouble("Precio: ");

                System.out.print("ID_Categoría: ");
                String categoria = scanner.nextLine();

                sql = "INSERT INTO Producto VALUES (?, ?, ?, ?, ?, ?)";

                parametros = new Object[]{
                    id, nombre, descripcion, stock, precio, categoria
                };

                break;
            }

            case "TIPO_PEDIDO": {

                System.out.print("ID_TIPO_PEDIDO: ");
                String id = scanner.nextLine();

                System.out.print("DESCRIPCION: ");
                String descripcion = scanner.nextLine();

                sql = "INSERT INTO TIPO_PEDIDO VALUES (?, ?)";

                parametros = new Object[]{
                    id, descripcion
                };

                break;
            }

            case "METODO_PAGO": {

                System.out.print("ID_metodo_Pago: ");
                String id = scanner.nextLine();

                System.out.print("Descripcion: ");
                String descripcion = scanner.nextLine();

                sql = "INSERT INTO METODO_PAGO VALUES (?, ?)";

                parametros = new Object[]{
                    id, descripcion
                };

                break;
            }

            case "Pedido": {

                System.out.print("ID_PEDIDO: ");
                String id = scanner.nextLine();

                System.out.print(
                    "Fecha y hora (YYYY-MM-DD HH:MM:SS): "
                );

                String fecha = scanner.nextLine();

                System.out.print("ID_TIPO_PEDIDO: ");
                String tipo = scanner.nextLine();

                System.out.print("ID_CLIENTE: ");
                String cliente = scanner.nextLine();

                System.out.print("ID_VENDEDOR: ");
                String vendedor = scanner.nextLine();

                double total = leerDouble("Total: ");

                sql = "INSERT INTO Pedido VALUES (?, ?, ?, ?, ?, ?)";

                parametros = new Object[]{
                    id, fecha, tipo, cliente, vendedor, total
                };

                break;
            }

            case "Detalles_del_Pedido": {

                System.out.print("ID_DELLATE_PEDIDO: ");
                String id = scanner.nextLine();

                System.out.print("ID_PEDIDO: ");
                String pedido = scanner.nextLine();

                System.out.print("ID_Producto: ");
                String producto = scanner.nextLine();

                int cantidad = leerEntero("Cantidad: ");

                double precio = leerDouble("Precio: ");

                sql = "INSERT INTO Detalles_del_Pedido VALUES (?, ?, ?, ?, ?)";

                parametros = new Object[]{
                    id, pedido, producto, cantidad, precio
                };

                break;
            }

            case "Venta": {

                System.out.print("ID_VENTA: ");
                String id = scanner.nextLine();

                double total = leerDouble("Total: ");

                System.out.print(
                    "Fecha pago (YYYY-MM-DD HH:MM:SS): "
                );

                String fecha = scanner.nextLine();

                System.out.print("ID_METODO_PAGO: ");
                String metodo = scanner.nextLine();

                System.out.print("ID_PEDIDO: ");
                String pedido = scanner.nextLine();

                sql = "INSERT INTO Venta VALUES (?, ?, ?, ?, ?)";

                parametros = new Object[]{
                    id, total, fecha, metodo, pedido
                };

                break;
            }

            default:

                System.out.println("Tabla no reconocida.");
                return;
        }

        // Solicitar confirmacion antes de ejecutar la operacion
        String clave = solicitarClave();

        dao.ejecutarOperacionConClave(
            sql, clave, parametros
        );
    }

    // Actualizar un registro
    private void actualizarDinamico(
            String tabla, String pkNombre) {

        System.out.print(
            "Ingrese el " + pkNombre
            + " del registro a actualizar: "
        );

        String id = scanner.nextLine();

        String campoModificar = "";

        if (tabla.equals("Cliente")
                || tabla.equals("Vendedor")) {

            campoModificar = "Nombre";

        } else if (tabla.equals("Categoria")) {

            campoModificar = "\"Descripción\"";

        } else if (tabla.equals("TIPO_PEDIDO")) {

            campoModificar = "DESCRIPCION";

        } else if (tabla.equals("METODO_PAGO")) {

            campoModificar = "\"Descripción\"";

        } else if (tabla.equals("Producto")) {

            campoModificar = "Nombre";

        } else if (tabla.equals("Pedido")
                || tabla.equals("Venta")) {

            campoModificar = "Total";

        } else if (tabla.equals("Detalles_del_Pedido")) {

            campoModificar = "Cantidad";
        }

        if (campoModificar.isEmpty()) {
            System.out.println(
                "No se ha definido un campo para actualizar."
            );
            return;
        }

        System.out.print(
            "Ingrese el nuevo valor para "
            + campoModificar + ": "
        );

        String nuevoValor = scanner.nextLine();

        String sql = "UPDATE " + tabla
                + " SET " + campoModificar
                + " = ? WHERE " + pkNombre + " = ?";

        String clave = solicitarClave();

        dao.ejecutarOperacionConClave(
            sql, clave, nuevoValor, id
        );
    }

    // Pedir la clave de confirmacion
    private String solicitarClave() {

        System.out.print("Ingrese la clave de confirmacion: ");

        return scanner.nextLine();
    }

    // Ejercicio 6: consultas configurables sobre productos en memoria
    public void gestionarConsultaProductos() {

        GestorProducto gestor = new GestorProducto();

        try {

            gestor.cargarProductos();

        } catch (SQLException e) {

            System.out.println(
                "Error al cargar productos: " + e.getMessage()
            );

            return;
        }

        System.out.println(
            "\nSe cargaron " + gestor.obtenerProductos().length
            + " productos en memoria."
        );

        int opcion;

        do {

            System.out.println(
                "\n===== CONSULTAS DE PRODUCTOS EN MEMORIA ====="
            );

            System.out.println("1. Realizar consulta configurable");
            System.out.println("2. Recargar productos desde SQLite");
            System.out.println("3. Volver al menu principal");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {

                case 1:
                    configurarConsultaProductos(gestor);
                    break;

                case 2:

                    try {

                        gestor.cargarProductos();

                        System.out.println(
                            "Productos recargados correctamente."
                        );

                    } catch (SQLException e) {

                        System.out.println(
                            "Error al recargar: " + e.getMessage()
                        );
                    }

                    break;

                case 3:
                    System.out.println(
                        "Volviendo al menu principal..."
                    );
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 3);
    }

    // Configurar campos, filtro, orden y limite
    private void configurarConsultaProductos(
            GestorProducto gestor) {

        String[] disponibles = {
            "ID_PRODUCTO",
            "Nombre",
            "Descripcion",
            "Stock",
            "Precio",
            "ID_Categoría"
        };

        System.out.println("\nCampos disponibles:");

        for (int i = 0; i < disponibles.length; i++) {
            System.out.println(
                (i + 1) + ". " + disponibles[i]
            );
        }

        System.out.println(
            "Escriba los numeros separados por comas."
        );

        System.out.print(
            "Campos a mostrar (ejemplo 1,2,5): "
        );

        String entradaCampos = scanner.nextLine();

        List<String> campos = new ArrayList<>();

        try {

            for (String parte : entradaCampos.split(",")) {

                int indice = Integer.parseInt(parte.trim());

                if (indice < 1 || indice > disponibles.length) {
                    System.out.println(
                        "Hay un numero de campo invalido."
                    );
                    return;
                }

                String campo = disponibles[indice - 1];

                if (!campos.contains(campo)) {
                    campos.add(campo);
                }
            }

        } catch (NumberFormatException e) {

            System.out.println(
                "Debe escribir numeros separados por comas."
            );
            return;
        }

        if (campos.isEmpty()) {
            System.out.println(
                "Debe seleccionar al menos un campo."
            );
            return;
        }

        // Definir filtro opcional
        String campoFiltro = "";
        String operador = "";
        String valorFiltro = "";

        System.out.print(
            "¿Desea aplicar un filtro? (s/n): "
        );

        String respuesta = scanner.nextLine().trim();

        if (respuesta.equalsIgnoreCase("s")) {

            int indiceFiltro = leerEnteroEnRango(
                "Seleccione el campo del filtro (1-6): ",
                1, 6
            );

            campoFiltro = disponibles[indiceFiltro - 1];

            if (ProductoCampoNumerico(campoFiltro)) {

                System.out.println(
                    "Operadores: =, >, <, >=, <="
                );

            } else {

                System.out.println(
                    "Operadores de texto: = o CONTIENE"
                );
            }

            System.out.print("Operador: ");
            operador = scanner.nextLine().trim().toUpperCase();

            System.out.print("Valor del filtro: ");
            valorFiltro = scanner.nextLine().trim();
        }

        // Definir orden
        String campoOrden = "";
        boolean ascendente = true;

        System.out.print(
            "Campo para ordenar (0 = ninguno, 1-6 = campo): "
        );

        int indiceOrden = leerEnteroEnRango(
            "Seleccione una opcion (0-6): ", 0, 6
        );

        if (indiceOrden != 0) {

            campoOrden = disponibles[indiceOrden - 1];

            int tipoOrden = leerEnteroEnRango(
                "1. Ascendente  2. Descendente: ", 1, 2
            );

            ascendente = tipoOrden == 1;
        }

        // Definir límite
        int limite = leerEnteroMinimo(
            "Cantidad maxima (0 = sin limite): ", 0
        );

        try {

            List<String> resultados =
                    gestor.consultarProductos(
                        campos,
                        campoFiltro,
                        operador,
                        valorFiltro,
                        campoOrden,
                        ascendente,
                        limite
                    );

            System.out.println(
                "\n===== RESULTADOS DE LA CONSULTA ====="
            );

            if (resultados.isEmpty()) {

                System.out.println(
                    "No se encontraron productos."
                );

            } else {

                for (String resultado : resultados) {
                    System.out.println(resultado);
                }

                System.out.println(
                    "Total de resultados: " + resultados.size()
                );
            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                "Consulta invalida: " + e.getMessage()
            );
        }
    }

    private boolean ProductoCampoNumerico(String campo) {

        return campo.equalsIgnoreCase("Stock")
                || campo.equalsIgnoreCase("Precio");
    }

    private int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {
                return Integer.parseInt(
                    scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                    "Ingrese un numero entero valido."
                );
            }
        }
    }

    private int leerEnteroEnRango(
            String mensaje, int minimo, int maximo) {

        while (true) {

            int valor = leerEntero(mensaje);

            if (valor >= minimo && valor <= maximo) {
                return valor;
            }

            System.out.println(
                "El valor debe estar entre "
                + minimo + " y " + maximo + "."
            );
        }
    }

    private int leerEnteroMinimo(String mensaje, int minimo) {

        while (true) {

            int valor = leerEntero(mensaje);

            if (valor >= minimo) {
                return valor;
            }

            System.out.println(
                "El valor minimo permitido es " + minimo + "."
            );
        }
    }

    private double leerDouble(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {
                return Double.parseDouble(
                    scanner.nextLine().trim().replace(',', '.')
                );

            } catch (NumberFormatException e) {

                System.out.println(
                    "Ingrese un numero decimal valido."
                );
            }
        }
    }
}