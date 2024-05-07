package ar.edu.unju.fi.ejercicio5.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import ar.edu.unju.fi.ejercicio5.model.*;
import ar.edu.unju.fi.ejercicio1.model.*;
import ar.edu.unju.fi.ejercicio1.model.Producto.Categoria;
import ar.edu.unju.fi.ejercicio1.model.Producto.OrigenFabricacion;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Producto> listaProductos = cargarProductos();

        int opcion;
        do {
            System.out.println("1 - Ver productos disponibles");
            System.out.println("2 - Comprar");
            System.out.println("3 - Salir");
            System.out.print("Elija una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarProductos(listaProductos);
                    break;
                case 2:
                    comprarProductos(listaProductos, scanner);
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                    break;
            }
        } while (opcion != 3);

        scanner.close();
    }

    private static ArrayList<Producto> cargarProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        
        productos.add(new Producto("001", "Producto 1", 100.0, OrigenFabricacion.ARGENTINA, Categoria.TELEFONIA, true));
        productos.add(new Producto("002", "Producto 2", 150.0, OrigenFabricacion.CHINA, Categoria.INFORMATICA, true));
        
        return productos;
    }

    private static void mostrarProductos(ArrayList<Producto> productos) {
        System.out.println("Productos disponibles:");
        for (Producto producto : productos) {
            System.out.println("Código: " + producto.getCodigo());
            System.out.println("Descripción: " + producto.getDescripcion());
            System.out.println("Precio: " + producto.getPrecioUnitario());
            System.out.println("Origen de fabricación: " + producto.getOrigenFabricacion());
            System.out.println("Categoría: " + producto.getCategoria());
            System.out.println("-----------------------------------");
        }
    }

    private static void comprarProductos(ArrayList<Producto> productos, Scanner scanner) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles para comprar.");
            return;
        }

        mostrarProductos(productos);

        ArrayList<Producto> productosComprados = new ArrayList<>();
        double totalCompra = 0.0;
        boolean continuarCompra = true;
        while (continuarCompra) {
            System.out.println("Ingrese el código del producto que desea comprar (o 0 para finalizar):");
            String codigoProducto = scanner.nextLine();
            if (codigoProducto.equals("0")) {
                continuarCompra = false;
            } else {
                Producto productoSeleccionado = null;
                for (Producto producto : productos) {
                    if (producto.getCodigo().equals(codigoProducto)) {
                        productoSeleccionado = producto;
                        break;
                    }
                }
                if (productoSeleccionado != null) {
                    if (productoSeleccionado.isDisponible()) {
                        productosComprados.add(productoSeleccionado);
                        totalCompra += productoSeleccionado.getPrecioUnitario();
                        productoSeleccionado.setDisponible(false);
                        System.out.println("Producto añadido a la compra.");
                    } else {
                        System.out.println("Este producto no está disponible.");
                    }
                } else {
                    System.out.println("No se encontró ningún producto con ese código.");
                }
            }
        }

        if (productosComprados.isEmpty()) {
            System.out.println("No se ha seleccionado ningún producto.");
            return;
        }

        System.out.println("Seleccione el método de pago:");
        System.out.println("1 - Pago en efectivo");
        System.out.println("2 - Pago con tarjeta");
        int metodoPago = scanner.nextInt();
        scanner.nextLine();

        switch (metodoPago) {
            case 1:
                realizarPagoEfectivo(totalCompra);
                break;
            case 2:
                realizarPagoTarjeta(totalCompra);
                break;
            default:
                System.out.println("Método de pago no válido. Cancelando compra.");
                for (Producto producto : productosComprados) {
                    producto.setDisponible(true);
                }
                break;
        }
    }

    private static void realizarPagoEfectivo(double totalCompra) {
        double montoPagado = totalCompra * 0.9;
        PagoEfectivo pagoEfectivo = new PagoEfectivo(montoPagado, LocalDate.now());
        System.out.println("Recibo de pago en efectivo:");
        System.out.println("Fecha de pago: " + pagoEfectivo.getFechaPago());
        System.out.println("Monto pagado: " + pagoEfectivo.getMontoPagado());
    }

    private static void realizarPagoTarjeta(double totalCompra) {
        double montoPagado = totalCompra * 1.15;
        PagoTarjeta pagoTarjeta = new PagoTarjeta("4021123454786010", LocalDate.now(), montoPagado);
        System.out.println("Recibo de pago con tarjeta:");
        System.out.println("Número de tarjeta: " + pagoTarjeta.getNumeroTarjeta());
        System.out.println("Fecha de pago: " + pagoTarjeta.getFechaPago());
        System.out.println("Monto pagado: " + pagoTarjeta.getMontoPagado());
    }
}