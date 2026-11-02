package Vista;

import controlador.CafeteriaControl;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            CafeteriaControl controller =
                    new CafeteriaControl(scanner);

            int opcion;

            do {

                System.out.println(
                    "\n===== SISTEMA DE CAFETERIA ====="
                );

                System.out.println("1. Gestionar Cliente");
                System.out.println("2. Gestionar Vendedor");
                System.out.println("3. Gestionar Categoria");
                System.out.println("4. Gestionar Producto");
                System.out.println("5. Gestionar Tipo de Pedido");
                System.out.println("6. Gestionar Metodo de Pago");
                System.out.println("7. Gestionar Pedido");
                System.out.println("8. Gestionar Detalles del Pedido");
                System.out.println("9. Gestionar Venta");
                System.out.println(
                    "10. Consultas avanzadas de productos (Ejercicio 6)"
                );
                System.out.println("11. Salir");

                System.out.print("Seleccione una opcion: ");

                try {
                    opcion = Integer.parseInt(
                        scanner.nextLine().trim()
                    );

                } catch (NumberFormatException e) {

                    System.out.println(
                        "Debe ingresar un numero valido."
                    );

                    opcion = 0;
                    continue;
                }

                switch (opcion) {

                    case 1:

                        controller.gestionarTabla(
                            "Cliente",
                            new String[]{
                                "ID_CLIENTE",
                                "Nombre",
                                "Documento_de_Identidad",
                                "Numero_celular"
                            },
                            "ID_CLIENTE"
                        );

                        break;

                    case 2:

                        controller.gestionarTabla(
                            "Vendedor",
                            new String[]{
                                "ID_VENDEDOR",
                                "Nombre",
                                "Documento_Identidad"
                            },
                            "ID_VENDEDOR"
                        );

                        break;

                    case 3:

                        controller.gestionarTabla(
                            "Categoria",
                            new String[]{
                                "ID_Categoria",
                                "Descripción"
                            },
                            "ID_Categoria"
                        );

                        break;

                    case 4:

                        controller.gestionarTabla(
                            "Producto",
                            new String[]{
                                "ID_PRODUCTO",
                                "Nombre",
                                "Stock",
                                "Precio",
                                "ID_Categoría"
                            },
                            "ID_PRODUCTO"
                        );

                        break;

                    case 5:

                        controller.gestionarTabla(
                            "TIPO_PEDIDO",
                            new String[]{
                                "ID_TIPO_PEDIDO",
                                "DESCRIPCION"
                            },
                            "ID_TIPO_PEDIDO"
                        );

                        break;

                    case 6:

                        controller.gestionarTabla(
                            "METODO_PAGO",
                            new String[]{
                                "ID_metodo_Pago",
                                "Descripción"
                            },
                            "ID_metodo_Pago"
                        );

                        break;

                    case 7:

                        controller.gestionarTabla(
                            "Pedido",
                            new String[]{
                                "ID_PEDIDO",
                                "Fecha_y_hora",
                                "ID_CLIENTE",
                                "Total"
                            },
                            "ID_PEDIDO"
                        );

                        break;

                    case 8:

                        controller.gestionarTabla(
                            "Detalles_del_Pedido",
                            new String[]{
                                "ID_DELLATE_PEDIDO",
                                "ID_PEDIDO",
                                "ID_Producto",
                                "Cantidad",
                                "Precio"
                            },
                            "ID_DELLATE_PEDIDO"
                        );

                        break;

                    case 9:

                        controller.gestionarTabla(
                            "Venta",
                            new String[]{
                                "ID_VENTA",
                                "Total",
                                "Fecha_pago",
                                "ID_METODO_PAGO"
                            },
                            "ID_VENTA"
                        );

                        break;

                    case 10:

                        controller.gestionarConsultaProductos();

                        break;

                    case 11:

                        System.out.println(
                            "Sistema cerrado con exito."
                        );

                        break;

                    default:

                        System.out.println("Opcion no valida.");
                }

            } while (opcion != 11);
        }
    }
}