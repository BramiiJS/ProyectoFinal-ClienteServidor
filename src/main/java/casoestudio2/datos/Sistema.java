package casoestudio2.datos;

import casoestudio2.modelo.*;
import java.util.ArrayList;

public class Sistema {

    private ArrayList<Usuario> usuarios;
    private ArrayList<Cliente> clientes;
    private ArrayList<Producto> productos;
    private ArrayList<Factura> facturas;

    public Sistema() {

        usuarios = new ArrayList<>();
        clientes = new ArrayList<>();
        productos = new ArrayList<>();
        facturas = new ArrayList<>();

        cargarDatosPrueba();

    }

    private void cargarDatosPrueba() {

        usuarios.add(new Administrador("admin", "1234"));
        usuarios.add(new Cajero("cajero", "5678"));

        clientes.add(new Cliente(
                "123456789",
                "Juan Pérez",
                "juan@gmail.com",
                "88888888"));

        clientes.add(new Cliente(
                "987654321",
                "María López",
                "maria@gmail.com",
                "77777777"));

        productos.add(new ProductoPerecedero(
                "P001",
                "Leche",
                950,
                20,
                "20/08/2026",
                "4°C"));

        productos.add(new ProductoNoPerecedero(
                "NP001",
                "Martillo",
                8500,
                15,
                "Acero",
                "Pasillo A"));

    }
    
    public Usuario validarUsuario(String usuario, String password) {

        for (Usuario u : usuarios) {

            if (u.login(usuario, password)) {
                return u;
            }

        }

        return null;

    }
    
    public Cliente buscarCliente(String cedula) {

        for (Cliente c : clientes) {

            if (c.getCedula().equals(cedula)) {
                return c;
            }

        }

        return null;

    }
    
    public Producto buscarProducto(String idProducto) {

        for (Producto p : productos) {

            if (p.getIdProducto().equalsIgnoreCase(idProducto)) {
                return p;
            }

        }

        return null;

    }
    
    public void agregarFactura(Factura factura) {

        facturas.add(factura);

    }

}
